package org.usfirst.frc.team2635.robot.commands;

import org.usfirst.frc.team2635.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class KickerCommand extends Command {

    public KickerCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }
    double startTimer;
    // Called just before this Command runs the first time
    protected void initialize() {
    	startTimer = Timer.getFPGATimestamp();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.kicker.setKicker(-0.75);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	double currentTimer = Timer.getFPGATimestamp();
    	double timePassed = currentTimer - startTimer;
        return timePassed > 0.2;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.kicker.setKicker(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.kicker.setKicker(0.0);
    }
}
