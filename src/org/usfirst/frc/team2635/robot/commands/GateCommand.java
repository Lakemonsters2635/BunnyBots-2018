package org.usfirst.frc.team2635.robot.commands;

import org.usfirst.frc.team2635.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class GateCommand extends TimedCommand {

	
    public GateCommand(double timeout) {
    	super(timeout);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.gate);
    }
    public GateCommand() {
    	super(.5);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.gate);
    }
    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.gate.moveGateOut();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return false;
    }

    // Called once after isFinished returns true
    public void end() {
    	//System.out.println("end");
    	Robot.gate.moveGateIn();
    	System.out.println("GateCommand end");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.gate.moveGateIn();
    	System.out.println("Gate interupt");
    }
}
