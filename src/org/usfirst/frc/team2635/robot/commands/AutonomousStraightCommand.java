package org.usfirst.frc.team2635.robot.commands;

import java.util.ArrayList;

import org.usfirst.frc.team2635.robot.Robot;
import org.usfirst.frc.team2635.robot.RobotMap;
import org.usfirst.frc.team2635.robot.model.MotionMagicLibrary;
import org.usfirst.frc.team2635.robot.model.MotionParameters;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class AutonomousStraightCommand extends TimedCommand {

	MotionParameters motionParams;
	double distance;
	double velocity;
	double acceleration;
	double initialHeading;
	boolean useStallDetection;

    public AutonomousStraightCommand(double distance, double velocity, double acceleration) {
        super(7.0);
        requires(Robot.drive);
        this.distance = distance;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.useStallDetection = false;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }
	
    public AutonomousStraightCommand(double distance, double velocity, double acceleration, double timeout) {
        super(timeout);
        requires(Robot.drive);
        this.distance = distance;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.useStallDetection = true;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    	Robot.drive.reset();
    	System.out.println("-----Autonomous Straight Started----");
    	System.out.println("Distance: "+ distance + "  Velocity: " + velocity + "  Acceleration: " + acceleration);
    	
      	motionParams = MotionMagicLibrary.getDriveParameters(RobotMap.WHEEL_RADIUS_INCHES, distance, velocity, false, acceleration);
    	Robot.drive.motionDriveInit(motionParams);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drive.motionMagicDriveStraight(motionParams);
    }

    // Called once after timeout
    protected void end() {
    	Robot.drive.setPIDValues(RobotMap.MOTION_MAGIC_P);
    	Robot.drive.motorControl(ControlMode.PercentOutput, 0.0, 0.0, false);
    	Robot.drive.reset();
    }

    protected void interrupted() {
    	end();
    }
   
    @Override protected boolean isFinished() {
    	
    	boolean isFinished = isTimedOut();
    	if (!isFinished) {
    		isFinished = Robot.drive.motionMagicDone(motionParams, RobotMap.ERRORTOLERANCE, useStallDetection);
    	}

    	if(isFinished) {
    		
    		System.out.println("Drive Straight Finished.");
    		System.out.println("-----------");
    	}
    	
    	return isFinished;
    }
}
