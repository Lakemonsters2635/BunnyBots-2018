package org.usfirst.frc.team2635.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class SorterC extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public DoubleSolenoid sorterChamber;
	public DoubleSolenoid sorterPannel;
	public boolean sorterState = false; //false = pannel start open
	public boolean isSorting = false;
	
	public SorterC(DoubleSolenoid sorterChamber, DoubleSolenoid sorterPannel) {
		this.sorterChamber = sorterChamber;
		this.sorterPannel = sorterPannel;
	}
	public void extendChamber() {
		sorterChamber.set(Value.kForward);
	}
	public void extendPanel() {
		sorterPannel.set(Value.kForward);
	}
	public void retractChamber() {
		sorterChamber.set(Value.kReverse);
	}
	public void retractPanel() {
		sorterPannel.set(Value.kForward);
	}
	
	public void keep() {
		if (sorterState==false ) {
			retractPanel();
			extendChamber();
			sorterState=true;
		}
		else {
			retractChamber();
			extendChamber();
		}
	}
	public void toss() {
		if (sorterState==false) {
			retractPanel();
			extendPanel();
		}
		else {
			retractChamber();
			extendPanel();
			sorterState=false;
		}
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

