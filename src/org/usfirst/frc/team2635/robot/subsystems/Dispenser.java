package org.usfirst.frc.team2635.robot.subsystems;

import org.usfirst.frc.team2635.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Dispenser extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	DoubleSolenoid dispenser;
	
	public Dispenser() {
		dispenser = new DoubleSolenoid(1, RobotMap.DISPENSER_OUT_CHANNEL, RobotMap.DISPENSER_IN_CHANNEL);
	}
	
	public void dispenserOut() {
		dispenser.set(Value.kForward);
	}
	public void dispenserIn() {
		dispenser.set(Value.kReverse);
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

