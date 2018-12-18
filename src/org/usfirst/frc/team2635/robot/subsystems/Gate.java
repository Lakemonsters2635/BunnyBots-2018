package org.usfirst.frc.team2635.robot.subsystems;

import org.usfirst.frc.team2635.robot.Robot;
import org.usfirst.frc.team2635.robot.RobotMap;
import org.usfirst.frc.team2635.robot.model.GateParams;
import org.usfirst.frc.team2635.robot.model.MotionParameters;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Subsystem;
 
/**
 *
 */
public class Gate extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	WPI_TalonSRX gateMotor;
	public double errorReport = 0;
	PowerDistributionPanel pdp;
	public Gate() {
		gateMotor = new WPI_TalonSRX(5);
		encoderStart();
		gateMotor.setSensorPhase(true);
		
	}
	public void moveGate(GateParams params) {	
	    int gatePos = getGatePos();
	   			
		gateMotor.configMotionCruiseVelocity(params.velocity, 0);

		gateMotor.configMotionAcceleration(params.acceleration, 0);
			
		motorControl(ControlMode.MotionMagic, params.wheelRotations);
		
	}
	public void motorControl(ControlMode controlMode, Double wheelRotations){
		gateMotor.set(controlMode, wheelRotations);

	}
	public int getGatePos() {
		return gateMotor.getSelectedSensorPosition(0);
	}
	
	public void encoderStart() {
		
		System.out.println("encoderStart()");
		gateMotor.setSelectedSensorPosition(0, 0, 0);
    	gateMotor.config_kP(0, 5, 0);
    	gateMotor.config_kI(0, 0, 0);
    	gateMotor.config_kD(0, 0, 0);
    	gateMotor.config_kF(0, 0, 0);
    
    	gateMotor.selectProfileSlot(0, 0);

    	gateMotor.configMotionAcceleration(RobotMap.GATE_ACCELERATION, 0);
    	
    	gateMotor.configMotionCruiseVelocity(RobotMap.GATE_VELOCITY, 0);
	}
	public boolean isWithinTolerance(int distance) {
		double currentDistance = currentDistance();
		
		int lowerError = (distance - RobotMap.ERRORTOLERANCE);
		int upperError = (distance + RobotMap.ERRORTOLERANCE);
		
		boolean isOver = (currentDistance > lowerError); 
		boolean isUnder = (currentDistance < upperError); 
		
		return isOver && isUnder;
	}
	public double currentDistance() {
		
		double currentDistance = Math.abs(gateMotor.getSelectedSensorPosition(0));
	
		return currentDistance;
	}
	public void gateInit(GateParams params) {
		gateMotor.configMotionCruiseVelocity(params.velocity, 0);
		gateMotor.configMotionAcceleration(params.velocity, 0);
	}
	public void reset(){
    	gateMotor.setSelectedSensorPosition(0, 0, 0);
    	
    	gateMotor.set(ControlMode.PercentOutput, 0);
    }
	public void setPIDValues(double p) {
    	gateMotor.config_kP(1, p, 0);
	}
	public boolean motionMagicDone(GateParams params, double errorTolerance, boolean useStallDetection) {
    	
    	if(useStallDetection && isStalled() ){
    		return true;
    	}
    	double intended = params.wheelRotations;
    	
    	double leftPos = Robot.drive.FLmotor.getSelectedSensorPosition(0);
    	
    	double error = Math.abs(intended - leftPos);
    	
    	this.errorReport = error;
   
    	if(error < errorTolerance) {
    		return true;
    	}
    	
    	return false;
    }
	 public Boolean isStalled(){
	    	Double flCurr = gateMotor.getOutputCurrent();
	    	
	    	Double flVolt = gateMotor.getBusVoltage();
	    
	    	Double battVolt = pdp.getVoltage();
	    	if(battVolt < 6.5){
	    		System.out.println("Stalled Ended Auto Sequence");
	    		return true;
	    	}
	    	else{
	    		return false;
	    	}
	    }
    
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    }
}

