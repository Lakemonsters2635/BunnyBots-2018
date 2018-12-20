package org.usfirst.frc.team2635.robot.commands;

import java.sql.Time;

import org.usfirst.frc.team2635.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ProcessBall extends Command {
	
	int sorterid;
	int ballStatus;
	double startTimer;
	boolean commandFinished;
	
    public ProcessBall(int sorterid) {
    	this.sorterid = sorterid;
    	commandFinished = false;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	commandFinished = false;
    	startTimer = Timer.getFPGATimestamp();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double currentTimer = Timer.getFPGATimestamp();
    	double elapsedTimer = currentTimer - startTimer;
    	if(elapsedTimer < 1.0 ) {
    		Robot.sorter.sorterReceive(sorterid);
    	} else if(elapsedTimer >= 1.0 && elapsedTimer <2.0) {
    		if(ballStatus == 2) {
    			Robot.sorter.sorterBad(sorterid);
    		} else {
    			Robot.sorter.sorterGood(sorterid);
    		}
    	} else {
    		commandFinished = true;
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return commandFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.sorter.sorterBad(sorterid);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
    
    public void ballPresent(int ballStatus) {
    	this.ballStatus = ballStatus;
    	start();
    }
}
