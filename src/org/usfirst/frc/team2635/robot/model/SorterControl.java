package org.usfirst.frc.team2635.robot.model;

import org.usfirst.frc.team2635.robot.Robot;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class SorterControl {
	public int[] colorArray;
	DriverStation dstation;
	int ourColor;
	int theirColor;
	
	public SorterControl(){
		colorArray = new int[4];
		dstation = DriverStation.getInstance();
		DriverStation.Alliance color;
		color = DriverStation.getInstance().getAlliance();
		if(color == DriverStation.Alliance.Blue){
			ourColor = 2;
			theirColor = 1;
		}else {
			ourColor = 1;
			theirColor = 2;
		}
	}
	
	public int[] control(){
		int[] passArray = new int[4];
		for(int i = 0; i<4; i++) passArray[i] = 0;
		if(colorArray != null) { 
			for(int i = 0; i<4; i++) {
				if(colorArray[i] == ourColor) {
					passArray[i] = 1;
				} else if(colorArray[i] == theirColor) {
					passArray[i] = 2;
				} else {
					passArray[i] = 0;
				}
			}
		}
		
		
		//Old Version controlled by Joystick buttons
//		if(Robot.oi.rightStick.getRawButton(11)){
//			passArray[0] = 1;
//			passArray[1] = 1;
//		}else if(Robot.oi.rightStick.getRawButton(10)){
//			passArray[0] = 2;
//			passArray[1] = 2;
//		}else if(Robot.oi.rightStick.getRawButton(9)){
//			passArray[0] = 0;
//			passArray[1] = 0;
//		}else{
//			passArray[0] = 3;
//			passArray[1] = 3;
//		}
		return passArray;
	}
}
