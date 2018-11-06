package org.usfirst.frc.team2635.robot.subsystems;

import org.usfirst.frc.team2635.robot.Robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Drive extends Subsystem {
	WPI_TalonSRX FRmotor;
	WPI_TalonSRX FLmotor;
	WPI_TalonSRX BRmotor;
	WPI_TalonSRX BLmotor;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void driveInit(){
    	FRmotor = new WPI_TalonSRX(1);
    	FLmotor = new WPI_TalonSRX(2);
    	BRmotor = new WPI_TalonSRX(3);
    	BLmotor = new WPI_TalonSRX(4);
    }

    public void driveLoop() {
    	double leftvalue =Robot.oi.leftStick.getRawAxis(1);
    	double rightvalue =Robot.oi.rightStick.getRawAxis(1);
    	FRmotor.set(rightvalue);
    	FLmotor.set(leftvalue);
    	FRmotor.set(rightvalue);
    	BLmotor.set(leftvalue);
    }
}

