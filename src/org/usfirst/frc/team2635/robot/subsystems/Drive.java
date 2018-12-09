package org.usfirst.frc.team2635.robot.subsystems;

import org.usfirst.frc.team2635.robot.Robot;
import org.usfirst.frc.team2635.robot.RobotMap;
import org.usfirst.frc.team2635.robot.model.MotionParameters;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

/**
 *
 */
public class Drive extends Subsystem {
	WPI_TalonSRX FRmotor;
	WPI_TalonSRX FLmotor;
	WPI_TalonSRX BRmotor;
	WPI_TalonSRX BLmotor;
	PowerDistributionPanel pdp;
	public double leftErrorReport = 0;
	public double rightErrorReport = 0;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public Drive(){
    	FRmotor = new WPI_TalonSRX(4);
    	FLmotor = new WPI_TalonSRX(2);
    	System.out.println("FLmotorInit: " + FLmotor);
    	BRmotor = new WPI_TalonSRX(3);
    	BLmotor = new WPI_TalonSRX(1);
    	
    	FLmotor.setSensorPhase(false);
    	FRmotor.setSensorPhase(false);
		
    	FLmotor.configPeakOutputForward(1,0);
		FRmotor.configPeakOutputForward(1,0);
		BLmotor.configPeakOutputForward(1,0);
		BRmotor.configPeakOutputForward(1,0);
		
		pdp = new PowerDistributionPanel();
    }
    
    public void tankDrive(double left, double right) {
		double absleft = Math.abs(left);
		double absright = Math.abs(right);
		if(absleft<0.05) left = 0;
		if(absright<0.05) right = 0;
		if(RobotMap.VELOCITYDRIVEMODE){
			FLmotor.setSelectedSensorPosition(0, 0, 0);
			FLmotor.config_kP(1, RobotMap.MOTION_MAGIC_P, 0);
			FLmotor.config_kI(1, 0, 0);
			FLmotor.config_kD(1, RobotMap.MOTION_MAGIC_D, 0);
			FLmotor.config_kF(1, RobotMap.MOTION_MAGIC_F, 0);
    	
			FRmotor.setSelectedSensorPosition(0, 0, 0);
			FRmotor.config_kP(1, RobotMap.MOTION_MAGIC_P, 0);
			FRmotor.config_kI(1, 0, 0);
			FRmotor.config_kD(1, RobotMap.MOTION_MAGIC_D, 0);
			FRmotor.config_kF(1, RobotMap.MOTION_MAGIC_F, 0);
			
	    	FRmotor.selectProfileSlot(1, 0);
	    	FLmotor.selectProfileSlot(1, 0);
	    	
			motorControl(ControlMode.Velocity, -left*RobotMap.ENCODER_COUNTS_PER_REVOLUTION, right*RobotMap.ENCODER_COUNTS_PER_REVOLUTION, true);
		}else {
			motorControl(ControlMode.PercentOutput, -left, right, false);
		}
	}
    public void setPIDValues(double p) {
    	FLmotor.config_kP(1, p, 0);
    	FRmotor.config_kP(1, p, 0);
	}

    public void motorControl(ControlMode controlMode, Double left, Double right, Boolean slave){
    	FLmotor.set(controlMode, left);
    	FRmotor.set(controlMode, right);
		
		if(!slave){
			BLmotor.set(controlMode, left);
			BRmotor.set(controlMode, right);
		}else {
			//back eg. slaves
			BLmotor.follow(FLmotor);
			BRmotor.follow(FRmotor);
		}	
	}
    public void teleInit() {
    	System.out.println("Flmotor:" + FLmotor);
		FLmotor.config_kP(1, RobotMap.MOTION_MAGIC_P, 0);
		FLmotor.config_kI(1, 0, 0);
		FLmotor.config_kD(1, RobotMap.MOTION_MAGIC_D, 0);
		FLmotor.config_kF(1, RobotMap.MOTION_MAGIC_F, 0);
    	
		FRmotor.config_kP(1, RobotMap.MOTION_MAGIC_P, 0);
		FRmotor.config_kI(1, 0, 0);
		FRmotor.config_kD(1, RobotMap.MOTION_MAGIC_D, 0);
		FRmotor.config_kF(1, RobotMap.MOTION_MAGIC_F, 0);
		
		FRmotor.configMotionAcceleration(400, 0);
	    FLmotor.configMotionAcceleration(400, 0);
	    	
	    FRmotor.configMotionCruiseVelocity(400, 0);
	    FLmotor.configMotionCruiseVelocity(400, 0);
	}
    
public void autoInit() {
    	
    	FLmotor.setSelectedSensorPosition(0, 0, 0);
    	
    	FLmotor.config_kP(0, RobotMap.MOTION_MAGIC_P, 0);
    	FLmotor.config_kI(0, RobotMap.MOTION_MAGIC_I, 0);
    	FLmotor.config_kD(0, RobotMap.MOTION_MAGIC_D, 0);
    	FLmotor.config_kF(0, RobotMap.MOTION_MAGIC_F, 0);
    	
    	FRmotor.setSelectedSensorPosition(0, 0, 0);

    	FRmotor.config_kP(0, RobotMap.MOTION_MAGIC_P, 0);
    	FRmotor.config_kI(0, RobotMap.MOTION_MAGIC_I, 0);
    	FRmotor.config_kD(0, RobotMap.MOTION_MAGIC_D, 0);
    	FRmotor.config_kF(0, RobotMap.MOTION_MAGIC_F, 0);
    	FRmotor.selectProfileSlot(0, 0);
    	FLmotor.selectProfileSlot(0, 0);
    	
    	FRmotor.configMotionAcceleration(RobotMap.AUTO_DRIVE_ACCELERATION, 0);
    	FLmotor.configMotionAcceleration(RobotMap.AUTO_DRIVE_ACCELERATION, 0);
    	
    	FRmotor.configMotionCruiseVelocity(RobotMap.MOTION_MAGIC_CRUISE_VELOCITY, 0);
    	FLmotor.configMotionCruiseVelocity(RobotMap.MOTION_MAGIC_CRUISE_VELOCITY, 0);
    }
    
	public int getFrontRightPos(){
		return FRmotor.getSelectedSensorPosition(0);
	}
	public int getFrontLeftPos(){
		return FLmotor.getSelectedSensorPosition(0);
	}
	public double inchesToCounts(double inches){
		double circumference = RobotMap.WHEEL_DIAMETER * Math.PI;
		
		double counts = inches/circumference*RobotMap.ENCODER_COUNTS_PER_REVOLUTION;
		return counts;
	}
	public void motionMagicRotate(MotionParameters motionParams) {
		int frontRight = getFrontRightPos();
		int frontLeft = getFrontLeftPos();
		double delta = (frontLeft + frontRight) * 0.5;
		if (motionParams.leftWheelRotations < 0) {
			delta = -delta;
		}
	
		FRmotor.configMotionCruiseVelocity(motionParams.rightVelocity, 0);
		FLmotor.configMotionCruiseVelocity(motionParams.leftVelocity, 0);
	
		FRmotor.configMotionAcceleration(motionParams.rightAcceleration, 0);
		FLmotor.configMotionAcceleration(motionParams.leftAcceleration, 0);
	
		motorControl(ControlMode.MotionMagic, motionParams.leftWheelRotations, motionParams.rightWheelRotations, true);
	}
	
	public void motionDriveInit(MotionParameters motionParams)
	{
		FRmotor.configMotionCruiseVelocity(motionParams.rightVelocity, 0);
		FLmotor.configMotionCruiseVelocity(motionParams.leftVelocity, 0);
	
		FRmotor.configMotionAcceleration(motionParams.rightAcceleration, 0);
		FLmotor.configMotionAcceleration(motionParams.leftAcceleration, 0);
	}
	
public void motionMagicDriveStraight(MotionParameters motionParams) {
    	
     	int frontRight = getFrontRightPos();
    	int frontLeft = getFrontLeftPos();
    			
		FRmotor.configMotionCruiseVelocity(motionParams.rightVelocity, 0);
		FLmotor.configMotionCruiseVelocity(motionParams.leftVelocity, 0);

		FRmotor.configMotionAcceleration(motionParams.rightAcceleration, 0);
		FLmotor.configMotionAcceleration(motionParams.leftAcceleration, 0);

		FRmotor.configMotionAcceleration(motionParams.rightAcceleration, 0);
		FLmotor.configMotionAcceleration(motionParams.leftAcceleration, 0);
		
		motorControl(ControlMode.MotionMagic, motionParams.leftWheelRotations, motionParams.rightWheelRotations, true);
	}
   
    public double getErrorDelta() {
      	int frontRight = getFrontRightPos();
    	int frontLeft = getFrontLeftPos();
    	double delta = (Math.abs(frontRight) - Math.abs(frontLeft));
    	
    	return delta;
  
    }
    
    public boolean motionMagicDone(MotionParameters motionParams, double errorTolerance, boolean useStallDetection) {
    	
    	if(useStallDetection && isStalled() ){
    		return true;
    	}
    	double leftIntended = motionParams.leftWheelRotations;
    	double rightIntended = motionParams.rightWheelRotations;
    	
    	double leftPos = Robot.drive.FLmotor.getSelectedSensorPosition(0);
    	double rightPos = Robot.drive.FRmotor.getSelectedSensorPosition(0);
    	
    	double leftError = Math.abs(leftIntended - leftPos);
    	double rightError = Math.abs(rightIntended - rightPos);
    	
    	this.leftErrorReport = leftError;
    	this.rightErrorReport = rightError;
   
    	if(leftError < errorTolerance && rightError < errorTolerance) {
    		return true;
    	}
    	
    	return false;
    }
    
    public void reset(){
    	FLmotor.setSelectedSensorPosition(0, 0, 0);
    	FRmotor.setSelectedSensorPosition(0, 0, 0);
    	
    	FLmotor.set(ControlMode.PercentOutput, 0);
    	FRmotor.set(ControlMode.PercentOutput, 0);
 
    }
    
    public Boolean isStalled(){
    	Double flCurr = FLmotor.getOutputCurrent();
    	Double frCurr = FRmotor.getOutputCurrent();
    	Double blCurr = BLmotor.getOutputCurrent();
    	Double brCurr = BRmotor.getOutputCurrent();
    	
    	Double flVolt = FLmotor.getBusVoltage();
    	Double frVolt = FRmotor.getBusVoltage();
    	Double blVolt = BLmotor.getBusVoltage();
    	Double brVolt = BRmotor.getBusVoltage();
    	
    	
    	Double totalCurr = (flCurr + frCurr + blCurr + brCurr);
    	Double avgVolt = (flVolt + frVolt + blVolt + brVolt)/4;
    	Double battVolt = pdp.getVoltage();
    	if(battVolt < 6.5){
    		System.out.println("Stalled Ended Auto Sequence");
    		return true;
    	}
    	else{
    		return false;
    	}
    }
	
}

