package org.usfirst.frc.team2635.robot.subsystems;

import org.usfirst.frc.team2635.robot.commands.SorterSolenoidCommand;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Sorter extends Subsystem {

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
//	DoubleSolenoid sorter0Good;
//	DoubleSolenoid sorter0Bad;
//	DoubleSolenoid sorter1Good;
//	DoubleSolenoid sorter1Bad;
//	DoubleSolenoid sorter2Good;
//	DoubleSolenoid sorter2Bad;
//	DoubleSolenoid sorter3Good;
//	DoubleSolenoid sorter3Bad;
//	
//	DoubleSolenoid[] outputs;
//	public Sorter() {
//		sorter0Good = new DoubleSolenoid(1,0,1);
//		sorter0Bad = new DoubleSolenoid(1,2,3);
//		sorter1Good = new DoubleSolenoid(1,4,5);
//		sorter1Bad = new DoubleSolenoid(1,6,7);
//		sorter2Good = new DoubleSolenoid(2,0,1);
//		sorter2Bad = new DoubleSolenoid(3,2,3);
//		sorter3Good = new DoubleSolenoid(3,4,5);
//		sorter3Bad = new DoubleSolenoid(3,6,7);
//		outputs = new DoubleSolenoid[] {sorter0Good, sorter1Good, sorter2Good, sorter3Good, sorter0Bad, sorter1Bad, sorter2Bad, sorter3Bad};
//	}
//
//    // Put methods for controlling this subsystem
//    // here. Call these from Commands.
//	
//	public void sort(boolean[] inputs) {
//		for(int i = 0; i <= 8; i++) {
//			if(inputs[i]) {
//				new SorterSolenoidCommand(1,i).start();
//			} else {
//				new SorterSolenoidCommand(1,i+4).start();
//			}
//			
//			if(i == 3) {
//				i = 0;
//			}
//			
//		}
//	}
//	
//	public void out(int i) {
//		outputs[i].set(Value.kForward);
//	}
//	
//	public void in(int i) {
//		outputs[i].set(Value.kReverse);
//	}
//    public void initDefaultCommand() {
//        // Set the default command for a subsystem here.
//        //setDefaultCommand(new MySpecialCommand());
//    	
//    }
}

