/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2635.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	public static final int RIGHT_JOYSTICK = 0;
	public static final int LEFT_JOYSTICK = 1;
	
	public static final double WHEEL_RADIUS_INCHES = 2.93389;
	public static final double ROBOT_LENGTH = 39; //includes bumpers
	public static final double ROBOT_WIDTH = 34.75; //includes bumpers
	public static final double WHEEL_SEPARATION_INCHES = 25.75;
	
	public static double MOTION_MAGIC_P = 5;
	public static double MOTION_MAGIC_I = 0.0;
	public static double MOTION_MAGIC_D = 0.0;
	public static double MOTION_MAGIC_F = 0.7;
	
	public static double MOTION_MAGIC_DISTANCE = 3000;
	public static double GATE_MOTOR_COUNTS = 100;
	public static double WHEEL_DIAMETER = 6;
	
	public static int ERRORTOLERANCE = 20;
	public static int ROTATE_ERRORTOLERANCE = 30;

	public static boolean VELOCITYDRIVEMODE = false;
	
	public static final int EXTENDER_IN_CHANNEL = 5;
	public static final int EXTENDER_OUT_CHANNEL = 6;
	public static final int KICKER_MOTOR_CHANNEL = 0;
	public static final int DISPENSER_OUT_CHANNEL = 7;
	public static final int DISPENSER_IN_CHANNEL = 4;
	public static final int INTAKE_MOTOR_CHANNEL = 0;
	public static final int FR_MOTOR_CHANNEL = 0;
	public static final int FL_MOTOR_CHANNEL = 2;
	public static final int BR_MOTOR_CHANNEL = 3;
	public static final int BL_MOTOR_CHANNEL = 1;
	
	public static final int INTAKE_BUTTON = 1;
	public static final int DISPENSER_BUTTON = 2;
	public static final int KICKER_BUTTON = 7;
	public static final int EXTENDER_BUTTON = 8;
	public static final int DRIVE_BUTTON = 9;
	
	public static final int ENCODER_COUNTS_PER_REVOLUTION = 4096/3;
	public static final int MOTION_MAGIC_CRUISE_VELOCITY = 500;
	
	//AUTO
	public static final double AUTO_CRATE_DISTANCE = 10;
	
	//TURNING
	public static final double AUTO_TURN_VELOCITY = 400;
	public static final int AUTO_TURN_ACCELERATION = 400;
	
	//NORMAL DRIVING
 	public static final double AUTO_DRIVE_VELOCITY = 600;
 	public static final int AUTO_DRIVE_ACCELERATION = 700;
 	
 	//GATE
 	public static final int GATE_VELOCITY = 1;
 	public static final int GATE_ACCELERATION = 1;
	
}
