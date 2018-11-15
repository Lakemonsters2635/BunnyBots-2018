package org.usfirst.frc.team2635.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.TimerTask;

import org.opencv.core.Rect;
import org.usfirst.frc.team2635.robot.Robot;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class ColorSensorTCS34725 extends Subsystem{

	public static final double kDefaultPeriod = .05;
	private static int instances = 0;
	private static final byte kAddress = 0x29;
	private static final byte kCommandBit = (byte)0x80;
	private static final byte kClearInterrupt = (byte) 0x66;
	private static final byte kID = (byte) 0x44;
	
	private static final TCS34725Gain kDefaultGain = TCS34725Gain.GAIN_16X;
	private static final TCS34725Integration kDefaultIntegrationTime = TCS34725Integration.TIME_24_MS;

	// *** Status Register Bit Definitions ***
	/* bit #4, RGBC clear channel interrupt	 */
	private static final byte kStatusAINT = (byte)0x10; 
	/* bit #0, RGBC Valid. Indicates that the RGBC channels have completed an integration cycle */
	private static final byte kStatusAVALID = (byte) 0x01;
	
	
	// *** Enable Register Bit Definitions ***
	/* bit #4, RGBC interrupt enable. 
	 * When asserted, permits RGBC interrupts to be generated. */
	private static final byte kEnableAIEN = (byte)0x10;
	
	/*	bit #3, Wait enable. This bit activates the wait feature. 
	Writing a 1 activates the wait time. 
	Writing a 0 disables the wait timer.*/
	private static final byte kEnableWEN = (byte) 0x08; 
	

	/* bit #1, RGBC enable, this bit activates the two channel ADC. 
	 * Writing a 1 activates the RGBC. 
	 * Writing a 0 disables the RGBC */
	private static final byte kEnableAEN = (byte) 0x02; 
	
	 
	/*// Bit #0, Power on, this bit activates the internal oscillator to permit the 
	 * timers and ADC channels to operate. 
	 * Writing a 1 activates the oscillator. 
	 * Writing a 0 disables the oscillator. */	
	private static final byte kEnablePON = (byte) 0x01;
	
	protected I2C m_i2c;
	private boolean isInit = false;
	private double m_period;
	private java.util.Timer m_pollLoop;
	private int m_deviceAddress;
	private int tca_addr;
	private I2C muxchat;
	private Boolean[] confirmedSensors;
	

	private TCS34725Gain m_gain = kDefaultGain;
	private TCS34725Integration m_integrationTime = kDefaultIntegrationTime;
	
	public class TCS34725Measurement {
		final private byte m_status;
		final private int m_clearData;
		final private int m_redData;
		final private int m_blueData;
		final private int m_greenData;

		TCS34725Measurement (byte status, int clearData, int redData, int blueData, int greenData) {
			m_status = status;
			m_clearData = clearData;
			m_redData = redData;
			m_blueData = blueData;
			m_greenData = greenData;
		}

		TCS34725Measurement(TCS34725Measurement m_CurrentMeasurement) {
			this(	m_CurrentMeasurement.m_status,
					m_CurrentMeasurement.m_clearData,
					m_CurrentMeasurement.m_redData,
					m_CurrentMeasurement.m_blueData,
					m_CurrentMeasurement.m_greenData);
		}
		
		public byte getStatus() {
			return m_status;
		}
		
		public int getClearData() {
			return m_clearData;
		}
		
		public int getRedData() {
			return m_redData;
		}
		
		public int getBlueData() {
			return m_blueData;
		}
		
		public int getGreenData() {
			return m_greenData;
		}
	}


	private ArrayList<TCS34725Measurement> m_CurrentMeasurement;

	public ColorSensorTCS34725() {
		this(I2C.Port.kOnboard, kDefaultPeriod);
	}
	public ColorSensorTCS34725(I2C.Port port) {
		this(port, kDefaultPeriod);
	}

	public ColorSensorTCS34725(I2C.Port port, double period) {
		muxchat = new I2C(I2C.Port.kOnboard, 0x70);
		tcaselect(0);
		confirmedSensors = new Boolean[8];
			m_i2c = new I2C(port, kAddress);
			m_CurrentMeasurement = new ArrayList<TCS34725Measurement>();
			for(int i = 0; i<8;i++){
				m_CurrentMeasurement.add(null);
			}
			// verify sensor is there
			for(int i = 0; i<8;i++){
				tcaselect(i);
				byte id = getRegister(TCS34725Register.ID);
				if (id == kID) { 
					instances ++;
					TCS34725Init();	
					confirmedSensors[i] = true;
					System.out.println("Found a TCS34725 at mux port "+ i);
				} else {
					confirmedSensors[i] = false;
					System.out.println("Can't Find a TCS34725 at mux port "+ i);
				}
				
			}
			if(instances > 0){
				m_period = period;
				m_pollLoop = new java.util.Timer();
				m_pollLoop.schedule(new PollTCS34725Task(this), 0L, (long) (m_period * 1000));
			}
	}

	/**
	 * Free the TCS34725 object.
	 */
	public void free() {
		m_pollLoop.cancel();
		synchronized (this) {
			m_pollLoop = null;
			m_i2c = null;
		}
	}	

	public void tcaselect(int a){
		if(a<8 && a>=0){
			muxchat.write(0, 1 << a);
		}
	}
	
	public void senseLoop(){
		
		for(int i = 0; i<8;i++){
			if(confirmedSensors[i]){
				//System.out.println("Sensor at mux port "+ i);
				final ColorSensorTCS34725.TCS34725Measurement meas = Robot.colorSensor.getMeasurement(i);
				double redData = meas.getRedData();
				double blueData = meas.getBlueData();
				double greenData = meas.getGreenData();
				double statData = meas.getStatus();
				double clearData = meas.getClearData();
//				System.out.println("Sensor status: " + meas.getStatus());
//				System.out.println("Clear data: " + meas.getClearData());
//				System.out.println("TCS34725 Red: " + meas.getRedData());
//				System.out.println("TCS34725 Blue: " + meas.getBlueData());
//				System.out.println("TCS34725 Green: " + meas.getGreenData());
//				System.out.println("");
				SmartDashboard.putNumber(i + " Sensor status:", meas.getStatus());
				SmartDashboard.putNumber(i + " Clear data:", meas.getClearData());
				SmartDashboard.putNumber(i + " Red:", meas.getRedData());
				SmartDashboard.putNumber(i + " Blue:", meas.getBlueData());
				SmartDashboard.putNumber(i + " Green:", meas.getGreenData());
				
				//Test Color Parameters
				if(redData < 600 && blueData < 500 && greenData < 500){
					SmartDashboard.putString(i + " Ball Status:", "No Ball");
				} else if(redData > (blueData + greenData)){
					SmartDashboard.putString(i + " Ball Status:", "Red Ball");
				} else if(blueData > redData && greenData > redData){
					SmartDashboard.putString(i + " Ball Status:", "Blue Ball");
				} else{
					SmartDashboard.putString(i + " Ball Status:", "Unknown Object");
				}
				
				
			}
		}
	}
	
	
	public boolean isInitialized(){
		return isInit;
	}

	private void TCS34725Init() {
		isInit = true;


		setIntegrationTime(m_integrationTime);
		setGain(m_gain);
		enable();	
		setInterrupt(true); // connect INT line to LED to force it to be off
	}
	
	private boolean isFinishedMeasure(){
		byte status = getRegister(TCS34725Register.STATUS);

		if((status & 0x01) != 0){
			return true;
		}
		return false;
	}

	private void readColors(){
		byte status;
		int clear;
		int red;
		int blue;
		int green;
		for(int i = 0; i<8;i++){
			if(confirmedSensors[i]){
				tcaselect(i);
				status = getRegister(TCS34725Register.STATUS);
				clear = getRegister16bit(TCS34725Register.CDATAL);
				red = getRegister16bit(TCS34725Register.RDATAL);
				blue = getRegister16bit(TCS34725Register.BDATAL);		
				green = getRegister16bit(TCS34725Register.GDATAL);

				// TCS34725Measurement class is immutable so we can skip synchronization
				m_CurrentMeasurement.set(i,new TCS34725Measurement(status, clear, red, blue, green));
			}
		}
		
	}

	
	public TCS34725Measurement getMeasurement(int i) {
		return m_CurrentMeasurement.get(i);
	}

	private class PollTCS34725Task extends TimerTask {
		private ColorSensorTCS34725 m_sensor;

		public PollTCS34725Task(ColorSensorTCS34725 sensor) {
			m_sensor = sensor;
		}

		@Override
		public void run() {
			if(m_sensor.isInitialized()){
				if(m_sensor.isFinishedMeasure()){
					m_sensor.readColors();

				}
			}
		}
	}
	
	public void enable() {
		setRegister(TCS34725Register.ENABLE, kEnablePON);
		Timer.delay(0.003);
		setRegister(TCS34725Register.ENABLE, (byte) (kEnablePON | kEnableAEN));
	}
	
	private void disable() {
		byte reg = getRegister(TCS34725Register.ENABLE);
		reg = (byte)(reg & (~(kEnablePON | kEnableAEN)));
		setRegister(TCS34725Register.ENABLE, reg);
	}
	
	public void setIntegrationTime(TCS34725Integration time) {
		if (!isInitialized()) {
			TCS34725Init();
		}
		setRegister(TCS34725Register.ATIME, time.value);
		m_integrationTime = time;
	}
	
	public void setGain(TCS34725Gain gain) {
		if (!isInitialized()) {
			TCS34725Init();
		}
		setRegister(TCS34725Register.CONTROL, gain.value);
		m_gain = gain;
	}
	
	public void setInterrupt(boolean b) {
		byte reg = getRegister(TCS34725Register.ENABLE);
		if (b) {
			reg = (byte) (reg | kEnableAIEN); // enable interrupt
		} else {
			reg = (byte) (reg & ~kEnableAIEN); // disable interrupt
		}
		setRegister(TCS34725Register.ENABLE, reg);
	}
	
	public void clearInterrupt() {
		// Uses special function of the Command Register (single byte transmission)
		ByteBuffer temp = ByteBuffer.allocateDirect(1);
		temp.put((byte) (kClearInterrupt | kCommandBit));
		m_i2c.writeBulk(temp, 1);	
	}
	
	public void setIntLimits(int low, int hi) {
		setRegister(TCS34725Register.AILTL, (byte)(low & 0xFF));
		setRegister(TCS34725Register.AILTH, (byte)(low >> 8));
		setRegister(TCS34725Register.AIHTL, (byte)(hi & 0xFF));
		setRegister(TCS34725Register.AIHTH, (byte)(hi >> 8));
	}

	private enum TCS34725Register{
		COMMAND	((byte) 0x00),	// Specifies register address
		ENABLE	((byte) 0x00),	// Enables states and interrupts
		ATIME	((byte) 0x01),	// RGBC time
		WTIME	((byte) 0x03),	// Wait time
		AILTL	((byte) 0x04), //Clear interrupt low threshold low byte
		AILTH	((byte) 0x05), //Clear interrupt low threshold high byte
		AIHTL	((byte) 0x06), // Clear interrupt high threshold low byte
		AIHTH	((byte) 0x07), // Clear interrupt high threshold high byte
		PERS	((byte) 0x0C), // Interrupt persistance filter
		CONFIG	((byte) 0x0D), // Configuration
		CONTROL	((byte) 0x0F), // Control
		ID		((byte) 0x12), // Device ID
		STATUS	((byte) 0x13), // Device Status
		CDATAL	((byte) 0x14), // Clear Data low byte
		CDATAH	((byte) 0x15), // Clear Data high byte
		RDATAL	((byte) 0x16), // Red Data low byte
		RDATAH	((byte) 0x17), // Red Data high byte
		GDATAL	((byte) 0x18), // Green Data low byte
		GDATAH	((byte) 0x19), // Green Data high byte
		BDATAL	((byte) 0x1A), // Blue Data low byte
		BDATAH	((byte) 0x1B); // Blue Data high byte

		public final byte value;

		private TCS34725Register(byte value) {
			this.value = value;
		}
	}
	
	public enum TCS34725Gain {
		GAIN_1X	((byte) 0x00),
		GAIN_4X ((byte) 0x01),
		GAIN_16X ((byte) 0x02),
		GAIN_60X ((byte) 0x03);
		
		public final byte value;

		private TCS34725Gain(byte value) {
			this.value = value;
		}
	}
	
	public enum TCS34725Integration {
		TIME_2_4_MS	((byte) 0xFF),
		TIME_24_MS ((byte) 0xF6),
		TIME_101_MS ((byte) 0xD5),
		TIME_154_MS ((byte) 0xC0),
		TIME_700_MS ((byte) 0x00);
		
		public final byte value;

		private TCS34725Integration(byte value) {
			this.value = value;
		}
	}
	
	public enum TCS34625Wait {
		TIME_2_4_MS	((byte) 0xFF),
		TIME_204_MS ((byte) 0xAB),
		TIME_614_MS ((byte) 0x00);
		
		public final byte value;

		private TCS34625Wait(byte value) {
			this.value = value;
		}
	}
	
	public enum TCS34625Persistance {
		EVERY_CYCLE	((byte) 0x00),	// Every RGBC cycle generates an interrupt
		CYCLE_1	((byte) 0x01),	// 1 clear channel value outside of threshold range generates an interrupt
		CYCLE_2	((byte) 0x02),	// 2 clear channel values outside of threshold range generates an interrupt
		CYCLE_3	((byte) 0x03), //3 clear channel values outside of threshold range generates an interrupt
		CYCLE_5	((byte) 0x04), //5 clear channel values outside of threshold range generates an interrupt
		CYCLE_10	((byte) 0x05), // 10 clear channel values outside of threshold range generates an interrupt
		CYCLE_15	((byte) 0x06), // 15 clear channel values outside of threshold range generates an interrupt
		CYCLE_20	((byte) 0x07), // 20 clear channel values outside of threshold range generates an interrupt
		CYCLE_25	((byte) 0x08), // 25 clear channel values outside of threshold range generates an interrupt
		CYCLE_30	((byte) 0x09), // 30 clear channel values outside of threshold range generates an interrupt
		CYCLE_35	((byte) 0x0A), // 35 clear channel values outside of threshold range generates an interrupt
		CYCLE_40	((byte) 0x0B), // 40 clear channel values outside of threshold range generates an interrupt
		CYCLE_45	((byte) 0x0C), // 45 clear channel values outside of threshold range generates an interrupt
		CYCLE_50	((byte) 0x0D), // 50 clear channel values outside of threshold range generates an interrupt
		CYCLE_55	((byte) 0x0E), // 55 clear channel values outside of threshold range generates an interrupt
		CYCLE_60	((byte) 0x0F); // 60 clear channel values outside of threshold range generates an interrupt

		
		public final byte value;

		private TCS34625Persistance(byte value) {
			this.value = value;
		}
	}

	
	//I2C manipulations utilities
	private void setRegister(TCS34725Register reg, byte data){
		ByteBuffer temp = ByteBuffer.allocateDirect(2);
		temp.put((byte) ((reg.value & 0xFF) | kCommandBit));
		temp.put((byte) (data & 0xFF));
		m_i2c.writeBulk(temp, 2);
	}

	private byte getRegister(TCS34725Register reg) {
		ByteBuffer index = ByteBuffer.allocateDirect(1);
		ByteBuffer rawData = ByteBuffer.allocateDirect(1);
		index.put((byte) ((reg.value & 0xFF) | kCommandBit));
		m_i2c.transaction(index, 1, rawData, 1);
		byte data = rawData.get();
		return data;
	}

	private int getRegister16bit(TCS34725Register registerAddr){
		ByteBuffer rawData = ByteBuffer.allocateDirect(2);
		ByteBuffer index = ByteBuffer.allocateDirect(1);
		index.put((byte) ((registerAddr.value & 0xFF) | kCommandBit));
		m_i2c.transaction(index, 1, rawData, 2);
		int lo = (int) rawData.get() & 0xFF;
		int hi = (int) rawData.get() & 0xFF;
		int temp = (hi << 8) + lo;
		return temp;

	}
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}


}