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
	
	public enum SorterState {
		measurePannel,
		measureChannel
	};
	public SorterState[] sorterStates;
	
	ProcessBall[] sorterArray;
	DoubleSolenoid[] solenoidArray;
	
	ProcessBall sorter1Command;
	ProcessBall sorter2Command;
	ProcessBall sorter3Command;
	ProcessBall sorter4Command;
	
	public SorterB(){
		sorterStates = new SorterState[] {
				SorterState.measurePannel, 
				SorterState.measurePannel, 
				SorterState.measurePannel, 
				SorterState.measurePannel};
		sorterChamber1 = new DoubleSolenoid(1, 4, 5);
		sorterPanel1 = new DoubleSolenoid(2, 0, 1);
		sorterChamber2 = new DoubleSolenoid(0, 2, 3);
		sorterPanel2 = new DoubleSolenoid(0, 0,1);
		sorterChamber3 = new DoubleSolenoid(2, 6, 7);
		sorterPanel3 = new DoubleSolenoid(2, 3,2);
		sorterChamber4 = new DoubleSolenoid(0, 5, 4);
		sorterPanel4 = new DoubleSolenoid(0, 7,6);
		
		solenoidArray = new DoubleSolenoid[8];
		solenoidArray[0] = sorterChamber1;
		solenoidArray[1] = sorterChamber2;
		solenoidArray[2] = sorterChamber3;
		solenoidArray[3] = sorterChamber4;
		
		solenoidArray[4] = sorterPanel1;
		solenoidArray[5] = sorterPanel2;
		solenoidArray[6] = sorterPanel3;
		solenoidArray[7] = sorterPanel4;
		
		
		sorter1Command = new ProcessBall(1);
		sorter2Command = new ProcessBall(2);
		sorter3Command = new ProcessBall(3);
		sorter4Command = new ProcessBall(4);
		
		sorterArray = new ProcessBall[4];
		sorterArray[0] = sorter1Command;
    	sorterArray[1] = sorter2Command;
    	sorterArray[2] = sorter3Command;
    	sorterArray[3] = sorter4Command;
	}
	public void teleInit(){
		for(int i = 0; i<4; i++){
			solenoidArray[i].set(Value.kReverse);
			solenoidArray[i+4].set(Value.kForward);
		}
	}
    public void sortLoop(int[] valueArray){
    	for(int i=0;i<4;i++){
    		if(valueArray[i] == 1 && !sorterArray[i].isRunning()) {
    			sorterArray[i].ballPresent(1);
    		} else if(valueArray[i] == 2 && !sorterArray[i].isRunning()) {
    			sorterArray[i].ballPresent(2);
    		}
    	}
//    	Old Version Left For Reference to Working Values
//    	if(valueArray[0] == 1){
//    		sorterChamber1.set(Value.kForward);
//   		sorterPanel1.set(Value.kReverse);
//    	} else if(valueArray[0] == 2){
//    		sorterChamber1.set(Value.kReverse);	
//    		sorterPanel1.set(Value.kForward);
//      } else if(valueArray[0] == 0){
//        	sorterChamber1.set(Value.kReverse);
//        	sorterPanel1.set(Value.kReverse);
//    	} else{
//    		sorterChamber1.set(Value.kReverse);
//        	sorterPanel1.set(Value.kForward);
//    	}
    }
    public void autoSortLoop(int[] valueArray){
    	for(int i=0;i<4;i++){
    		if(valueArray[i] != 0 && !sorterArray[i].isRunning()) {
    			sorterArray[i].ballPresent(2);
    		}     	
    	}
    }
	public void sorterReceive(int sorterid) {
			solenoidArray[sorterid-1].set(Value.kReverse);
			solenoidArray[sorterid+3].set(Value.kReverse);
		
	}
	public void sorterGood(int sorterid) {
			solenoidArray[sorterid-1].set(Value.kForward);
			solenoidArray[sorterid+3].set(Value.kReverse);
	}
	public void sorterBad(int sorterid) {
			solenoidArray[sorterid-1].set(Value.kReverse);
			solenoidArray[sorterid+3].set(Value.kForward);
	}
	public void interuptAll(){
		for(int i = 0; i<4; i++) sorterArray[i].toInterupt();
	}
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

