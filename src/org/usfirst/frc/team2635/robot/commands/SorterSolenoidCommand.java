package org.usfirst.frc.team2635.robot.commands;

import org.usfirst.frc.team2635.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class SorterSolenoidCommand extends TimedCommand {
	int i;
	
    public SorterSolenoidCommand(double timeout, int i) {
        super(timeout);
        this.i = i;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.sorter.out(i);
    }

    // Called once after timeout
    protected void end() {
    	Robot.sorter.in(i);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.sorter.in(i);
    }
}
