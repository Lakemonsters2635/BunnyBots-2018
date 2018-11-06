package org.usfirst.frc.team2635.robot.subsystems;

import org.usfirst.frc.team2635.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Kicker extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	WPI_TalonSRX kickerMotor;
	
	public Kicker(){
		kickerMotor = new WPI_TalonSRX(RobotMap.KICKER_MOTOR_CHANNEL);
	}

	public void setKicker(double speed){
		kickerMotor.set(speed);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    }
}

