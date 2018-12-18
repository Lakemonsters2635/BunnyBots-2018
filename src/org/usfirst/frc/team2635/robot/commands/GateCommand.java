package org.usfirst.frc.team2635.robot.commands;

import org.usfirst.frc.team2635.robot.Robot;
import org.usfirst.frc.team2635.robot.RobotMap;
import org.usfirst.frc.team2635.robot.model.GateParams;
import org.usfirst.frc.team2635.robot.model.MotionMagicLibrary;
import org.usfirst.frc.team2635.robot.model.MotionParameters;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class GateCommand extends TimedCommand {

	GateParams params;
	double distance;
	double velocity;
	double acceleration;
	boolean useStallDetection;
	
    public GateCommand(double distance, double velocity, double acceleration, double timeOut) {
    	super(timeOut);
    	 requires(Robot.gate);
         this.distance = distance;
         this.velocity = velocity;
         this.acceleration = acceleration;
         this.useStallDetection = false;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.gate.reset();
    	params = MotionMagicLibrary.getGateParams(RobotMap.WHEEL_RADIUS_INCHES, distance, velocity, acceleration);
    	Robot.gate.gateInit(params);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.gate.moveGate(params);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	boolean isFinished = isTimedOut();
    	if (!isFinished) {
    		isFinished = Robot.gate.motionMagicDone(params, RobotMap.ERRORTOLERANCE, useStallDetection);
    	}

    	if(isFinished) {
    		
    		System.out.println("Gate move Finished.");
    		System.out.println("-----------");
    	}
    	
    	return isFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.gate.setPIDValues(RobotMap.MOTION_MAGIC_P);
    	Robot.gate.motorControl(ControlMode.PercentOutput, 0.0);
    	Robot.gate.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    	
    }
}
