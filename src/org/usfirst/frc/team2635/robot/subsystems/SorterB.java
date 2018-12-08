package org.usfirst.frc.team2635.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class SorterB extends Subsystem {
	public DoubleSolenoid sorterChamber1;
	public DoubleSolenoid sorterPanel1;
	
	public SorterB(){
		sorterChamber1 = new DoubleSolenoid(0, 2, 3);
		sorterPanel1 = new DoubleSolenoid(0, 0,1);
	}

    public void sortLoop(int[] valueArray){
    	//for(int i=0;i<4;i++){
    		if(valueArray[0] == 1){
    			sorterChamber1.set(Value.kForward);
    			sorterPanel1.set(Value.kReverse);
    		} else if(valueArray[0] == 2){
    			sorterChamber1.set(Value.kReverse);	
    			sorterPanel1.set(Value.kForward);
        	} else if(valueArray[0] == 0){
        		sorterChamber1.set(Value.kReverse);
        		sorterPanel1.set(Value.kReverse);
    		} else{
    			sorterChamber1.set(Value.kReverse);
        		sorterPanel1.set(Value.kForward);
    		}
    	//}
    }
	
	
	
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

