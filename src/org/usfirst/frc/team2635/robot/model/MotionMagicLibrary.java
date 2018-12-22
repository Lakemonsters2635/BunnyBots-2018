package org.usfirst.frc.team2635.robot.model;

import org.usfirst.frc.team2635.robot.RobotMap;
import org.usfirst.frc.team2635.robot.commands.AutonomousStraightCommand;
import org.usfirst.frc.team2635.robot.commands.AutonomousTurnCommand;
import org.usfirst.frc.team2635.robot.commands.GateCommand;
import org.usfirst.frc.team2635.robot.commands.PauseCommand;
import org.usfirst.frc.team2635.robot.commands.ProcessBall;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class MotionMagicLibrary {
	public static MotionParameters getRotationParameters(double targetAngle,    double wheelRadiusInches, 
			double wheelSeparationInches,  double velocity, double acceleration)	
	{
			double inchesPerRotation = wheelRadiusInches * 2 * Math.PI;
			System.out.println("Rotation Parameters Called");
			double arcLengthRight;
			double archLengthLeft;
			double rightWheelRotations;
			double leftWheelRotations;
			
			
			//To rotate around center.
			double radius = wheelSeparationInches/2.0;
			//radius is 1/2 of wheelSeparationInches
			//ArcLengh = radius * angle in radians
			
			arcLengthRight = radius *  (2*Math.PI)/360.0 * targetAngle;
			archLengthLeft = arcLengthRight;
			rightWheelRotations = -arcLengthRight/inchesPerRotation;
			leftWheelRotations = -archLengthLeft/inchesPerRotation;
			
			double velocityRatio = Math.abs(leftWheelRotations/rightWheelRotations);
			
			double rightVelocity = velocity;
			double leftVelocity = velocity * velocityRatio;
			
			double rightAcceleration =  acceleration;
			double leftAcceleration =  acceleration;
	
			MotionParameters rotationParams = new MotionParameters();
			rotationParams.rightAcceleration = (int) rightAcceleration;
			rotationParams.leftAcceleration = (int) leftAcceleration;
			rotationParams.rightVelocity     = (int) rightVelocity;
			rotationParams.leftVelocity     = (int) leftVelocity;
			rotationParams.rightWheelRotations = rightWheelRotations*RobotMap.ENCODER_COUNTS_PER_REVOLUTION;
			rotationParams.leftWheelRotations = leftWheelRotations*RobotMap.ENCODER_COUNTS_PER_REVOLUTION;
			
			return rotationParams;
			}

	
	public static MotionParameters getDriveParameters(double wheelRadiusInches, double distanceInches, double velocity, boolean reverse, double acceleration)
	{
		

		double inchesPerRotation = wheelRadiusInches * 2 * Math.PI;
		
		//double arcLengthInner;
		//double archLengthOuter;
		double velocit = velocity;
		
		
		//FOR COMPETITION BOT DO THE FOLLOWING
		double leftWheelRotations = distanceInches/inchesPerRotation;
		//END COMPETITION BOT
		
		double rightWheelRotations = -distanceInches/inchesPerRotation;
		
		if (reverse)
		{			
			leftWheelRotations = -leftWheelRotations;
			rightWheelRotations = -rightWheelRotations;
		}
		
		MotionParameters driveParams = new MotionParameters();
		driveParams.leftAcceleration = (int) acceleration;
		driveParams.rightAcceleration = (int) acceleration;
		driveParams.leftWheelRotations = leftWheelRotations * RobotMap.ENCODER_COUNTS_PER_REVOLUTION;
		driveParams.rightWheelRotations = rightWheelRotations * RobotMap.ENCODER_COUNTS_PER_REVOLUTION;
		driveParams.leftVelocity = (int) velocit;
		driveParams.rightVelocity = (int) velocit;
		return driveParams;

	}
	public static GateParams getGateParams(double wheelRadiusInches, double distanceInches, double velocity, double acceleration)
	{
		

		double inchesPerRotation = wheelRadiusInches * 2 * Math.PI;
		
		double velocit = velocity;
		double leftWheelRotations = distanceInches/inchesPerRotation;
		
		double rightWheelRotations = -distanceInches/inchesPerRotation;
		
		GateParams params = new GateParams();
		params.acceleration = (int) acceleration;
		params.wheelRotations = leftWheelRotations * RobotMap.ENCODER_COUNTS_PER_REVOLUTION;
		params.velocity = (int) velocit;
		return params;

	}
	public static CommandGroup AutonomousStandard() {
		CommandGroup output;
		output = new CommandGroup();
		for (int i=0; i<4; i++) {
			output.addSequential(new AutonomousStraightCommand(RobotMap.AUTO_CRATE_DISTANCE, RobotMap.AUTO_DRIVE_VELOCITY, RobotMap.AUTO_DRIVE_ACCELERATION));	
		}
		return output;
	}
	public static CommandGroup DoNothingCommand() {
		CommandGroup output;
		output = new CommandGroup();
		
		return output;
	}
}
