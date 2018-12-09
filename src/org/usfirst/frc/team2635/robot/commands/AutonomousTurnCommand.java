package org.usfirst.frc.team2635.robot.commands;

import org.usfirst.frc.team2635.robot.Robot;
import org.usfirst.frc.team2635.robot.RobotMap;
import org.usfirst.frc.team2635.robot.model.MotionMagicLibrary;
import org.usfirst.frc.team2635.robot.model.MotionParameters;

import java.security.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousTurnCommand extends Command {

	MotionParameters rotationParams;
	
	double rpm;
	double targetAngle;
	double acceleration;
	boolean encodersDone;
	double initialAngle;
	int retryCount = 0;
	
    public AutonomousTurnCommand(double rpm, double targetAngle, double acceleration) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    	//FHE: WARNING: HARD CODED TIME OUT
    	
    	this.setTimeout(3);
    	this.rpm = rpm;
    	this.targetAngle = targetAngle;
    	this.acceleration = acceleration;
    }
    
    public AutonomousTurnCommand(double rpm, double acceleration) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    	//FHE: WARNING: HARD CODED TIME OUT
    	
    	this.setTimeout(3);
    	this.rpm = rpm;
    	this.acceleration = acceleration;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drive.reset();
	   	encodersDone = false;
     	
    	System.out.println("-----Autonomous Turn Started----");
    
	   	rotationParams = MotionMagicLibrary.getRotationParameters(targetAngle,
				RobotMap.WHEEL_RADIUS_INCHES, RobotMap.WHEEL_SEPARATION_INCHES, rpm, acceleration);
	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
   
    	if(!encodersDone) {
    		Robot.drive.motionMagicRotate(rotationParams);  
    		encodersDone = Robot.drive.motionMagicDone(rotationParams, RobotMap.ROTATE_ERRORTOLERANCE, false);
    	} 
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
    	boolean isTurnFinished = isTimedOut();
    	
    	if (!isTurnFinished) {
    		isTurnFinished = Robot.drive.motionMagicDone(rotationParams,RobotMap.ROTATE_ERRORTOLERANCE, false);
    	} else {
    		System.out.println("Turn timed out");
    	}
    	
    	if (isTurnFinished) {
    		System.out.println("Drive Turn Finished");
    		System.out.println("-----------");
    		Robot.drive.setPIDValues(RobotMap.MOTION_MAGIC_P);

    	}
    	return isTurnFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drive.setPIDValues(RobotMap.MOTION_MAGIC_P);
    	Robot.drive.motorControl(ControlMode.PercentOutput, 0.0, 0.0, false);
    	Robot.drive.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
