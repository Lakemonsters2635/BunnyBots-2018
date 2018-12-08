package org.usfirst.frc.team2635.robot.model;

import org.usfirst.frc.team2635.robot.Robot;

public class SorterControl {
	public SorterControl(){
		System.out.println("inst");
	}
	
	public int[] control(){
		
		int[] passArray = new int[4];
		for(int i = 0; i<4; i++) passArray[i] = 0;
		
		if(Robot.oi.rightStick.getRawButton(11)){
			passArray[0] = 1;
			passArray[1] = 1;
		}else if(Robot.oi.rightStick.getRawButton(10)){
			passArray[0] = 2;
			passArray[1] = 2;
		}else if(Robot.oi.rightStick.getRawButton(9)){
			passArray[0] = 0;
			passArray[1] = 0;
		}else{
			passArray[0] = 3;
			passArray[1] = 3;
		}
		return passArray;
		
	}
}
