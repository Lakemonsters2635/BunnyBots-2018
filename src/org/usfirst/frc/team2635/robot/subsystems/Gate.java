package org.usfirst.frc.team2635.robot.subsystems;

import org.usfirst.frc.team2635.robot.Robot;
import org.usfirst.frc.team2635.robot.RobotMap;
import org.usfirst.frc.team2635.robot.model.MotionParameters;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
 
/**
 *
 */
public class Gate extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	WPI_TalonSRX gateMotor;
	
	public Gate() {
		gateMotor = new WPI_TalonSRX(5);
		encoderStart();
		gateMotor.setSensorPhase(true);
		
	}
	
	public void openGate() {
		gateMotor.set(ControlMode.MotionMagic, RobotMap.GATE_MOTOR_COUNTS);
	}
	public void closeGate() {
		gateMotor.set(ControlMode.MotionMagic, 0);
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
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    }
}

