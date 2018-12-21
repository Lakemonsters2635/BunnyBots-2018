package org.usfirst.frc.team2635.robot.subsystems;

import org.usfirst.frc.team2635.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Extender extends Subsystem {
	DoubleSolenoid extender;
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public Extender(){
		//extender = new DoubleSolenoid(1, RobotMap.EXTENDER_OUT_CHANNEL, RobotMap.EXTENDER_IN_CHANNEL);
	}
	
	public void extenderOut(){
		extender.set(Value.kForward);
	}
	
	public void extenderIn(){
		extender.set(Value.kReverse);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

