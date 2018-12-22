package org.usfirst.frc.team2635.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
 
/**
 *
 */
public class Gate extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	DoubleSolenoid gate;
	public Gate() {
		gate = new DoubleSolenoid(1, 7, 6);
	}
	public void moveGateOut() {	
		gate.set(Value.kForward);
	}
	public void moveGateIn(){
		gate.set(Value.kReverse);
	}
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}