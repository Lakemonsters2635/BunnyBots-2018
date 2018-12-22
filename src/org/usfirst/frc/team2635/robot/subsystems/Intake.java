package org.usfirst.frc.team2635.robot.subsystems;

import org.usfirst.frc.team2635.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Intake extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	WPI_TalonSRX intakeMotor;
	
	public Intake(){
		intakeMotor = new WPI_TalonSRX(RobotMap.INTAKE_MOTOR_CHANNEL);
	}
	public void setIntake(double speed){
		intakeMotor.set(speed);
	}
	public void intakeIn(){
		intakeMotor.set(1.0);
	}
	public void intakeOut(){
		intakeMotor.set(-0.5);
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

