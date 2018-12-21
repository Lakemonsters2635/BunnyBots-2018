package org.usfirst.frc.team2635.robot.commands;

import org.usfirst.frc.team2635.robot.Robot;
import org.usfirst.frc.team2635.robot.subsystems.SorterC;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SortCommand extends Command {

	private enum SorterState {
		SENSING_PANEL,
		SENSING_CHAMBER,
		SORTING
	};
	private enum SensorState {
		NO_BALL,
		KEEP,
		TOSS
	};
	
	private SensorState sensorState;
	private SorterState sorterState;
	private SorterC sorter;
	
    public SortCommand(SorterC sorter, int sorterNumber) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.sorter = sorter;
    	requires(Robot.colorSensor);
    	requires(this.sorter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	sensorState = SensorState.NO_BALL;
    	sorterState = SorterState.SENSING_PANEL;    	
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(sensorState.NO_BALL != null) {
    		
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
