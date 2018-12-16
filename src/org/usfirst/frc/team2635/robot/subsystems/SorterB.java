package org.usfirst.frc.team2635.robot.subsystems;

import org.usfirst.frc.team2635.robot.commands.ProcessBall;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class SorterB extends Subsystem {
	public DoubleSolenoid sorterChamber1;
	public DoubleSolenoid sorterPanel1;
	public DoubleSolenoid sorterChamber2;
	public DoubleSolenoid sorterPanel2;
	public DoubleSolenoid sorterChamber3;
	public DoubleSolenoid sorterPanel3;
	public DoubleSolenoid sorterChamber4;
	public DoubleSolenoid sorterPanel4;
	
	int sorter1Status;
	int sorter2Status;
	int sorter3Status;
	int sorter4Status;
	
	ProcessBall[] sorterArray;
	
	ProcessBall sorter1Command;
	ProcessBall sorter2Command;
	ProcessBall sorter3Command;
	ProcessBall sorter4Command;
	
	public SorterB(){
		sorterChamber1 = new DoubleSolenoid(0, 2, 3);
		sorterPanel1 = new DoubleSolenoid(0, 0,1);
		sorterChamber2 = new DoubleSolenoid(0, 2, 3);
		sorterPanel2 = new DoubleSolenoid(0, 0,1);
		sorterChamber3 = new DoubleSolenoid(0, 2, 3);
		sorterPanel3 = new DoubleSolenoid(0, 0,1);
		sorterChamber4 = new DoubleSolenoid(0, 2, 3);
		sorterPanel4 = new DoubleSolenoid(0, 0,1);
		
		sorter1Command = new ProcessBall(1);
		sorter2Command = new ProcessBall(2);
		sorter3Command = new ProcessBall(3);
		sorter4Command = new ProcessBall(4);
		
		sorterArray[0] = sorter1Command;
    	sorterArray[1] = sorter2Command;
    	sorterArray[2] = sorter3Command;
    	sorterArray[3] = sorter4Command;
	}

    public void sortLoop(int[] valueArray){
    	sorter1Status = valueArray[0];
    	sorter2Status = valueArray[1];
    	sorter3Status = valueArray[2];
    	sorter4Status = valueArray[3];
    	
    	for(int i=0;i<4;i++){
    		if(valueArray[i] == 1 && !sorterArray[i].isRunning()) {
    			sorterArray[i].ballPresent(1);
    		} else if(valueArray[i] == 2 && !sorterArray[i].isRunning()) {
    			sorterArray[i].ballPresent(2);
    		}
    	
    	
    	
//    		if(valueArray[0] == 1){
//    			sorterChamber1.set(Value.kForward);
//    			sorterPanel1.set(Value.kReverse);
//    		} else if(valueArray[0] == 2){
//    			sorterChamber1.set(Value.kReverse);	
//    			sorterPanel1.set(Value.kForward);
//        	} else if(valueArray[0] == 0){
//        		sorterChamber1.set(Value.kReverse);
//        		sorterPanel1.set(Value.kReverse);
//    		} else{
//    			sorterChamber1.set(Value.kReverse);
//        		sorterPanel1.set(Value.kForward);
//    		}
    	}
    }
    public void autoSortLoop(int[] valueArray){
    	sorter1Status = valueArray[0];
    	sorter2Status = valueArray[1];
    	sorter3Status = valueArray[2];
    	sorter4Status = valueArray[3];
    	
    	for(int i=0;i<4;i++){
    		if(valueArray[i] != 0 && !sorterArray[i].isRunning()) {
    			sorterArray[i].ballPresent(2);
    		}     	
    	}
    }

	public void sorterReceive(int sorterid) {
		if(sorterid == 1) {
			sorterChamber1.set(Value.kReverse);
			sorterPanel1.set(Value.kReverse);
		} else if(sorterid == 2) {
			sorterChamber2.set(Value.kReverse);
			sorterPanel2.set(Value.kReverse);
		}else if(sorterid == 3) {
			sorterChamber3.set(Value.kReverse);
			sorterPanel3.set(Value.kReverse);
		}else if(sorterid == 4) {
			sorterChamber4.set(Value.kReverse);
			sorterPanel4.set(Value.kReverse);
		}
	}
	public void sorterGood(int sorterid) {
		if(sorterid == 1) {
			sorterChamber1.set(Value.kForward);
			sorterPanel1.set(Value.kReverse);
		} else if(sorterid == 2) {
			sorterChamber2.set(Value.kForward);
			sorterPanel2.set(Value.kReverse);
		}else if(sorterid == 3) {
			sorterChamber3.set(Value.kForward);
			sorterPanel3.set(Value.kReverse);
		}else if(sorterid == 4) {
			sorterChamber4.set(Value.kForward);
			sorterPanel4.set(Value.kReverse);
		}
	}
	public void sorterBad(int sorterid) {
		if(sorterid == 1) {
			sorterChamber1.set(Value.kReverse);
			sorterPanel1.set(Value.kForward);
		} else if(sorterid == 2) {
			sorterChamber2.set(Value.kReverse);
			sorterPanel2.set(Value.kForward);
		}else if(sorterid == 3) {
			sorterChamber3.set(Value.kReverse);
			sorterPanel3.set(Value.kForward);
		}else if(sorterid == 4) {
			sorterChamber4.set(Value.kReverse);
			sorterPanel4.set(Value.kForward);
		}
	}
	
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

