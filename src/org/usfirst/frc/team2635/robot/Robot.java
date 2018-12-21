/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2635.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.Calendar;

import org.usfirst.frc.team2635.robot.commands.AutonomousCommand;
import org.usfirst.frc.team2635.robot.commands.AutonomousStraightCommand;
import org.usfirst.frc.team2635.robot.commands.AutonomousTurnCommand;
import org.usfirst.frc.team2635.robot.commands.DispenserCommand;
import org.usfirst.frc.team2635.robot.commands.DriveCommand;
import org.usfirst.frc.team2635.robot.commands.DriveForwardCommand;
import org.usfirst.frc.team2635.robot.commands.ExampleCommand;
import org.usfirst.frc.team2635.robot.commands.ExtenderCommand;
import org.usfirst.frc.team2635.robot.commands.GateCommand;
import org.usfirst.frc.team2635.robot.commands.IntakeCommand;
import org.usfirst.frc.team2635.robot.subsystems.ColorSensorTCS34725;
import org.usfirst.frc.team2635.robot.subsystems.Dispenser;
import org.usfirst.frc.team2635.robot.subsystems.Drive;
import org.usfirst.frc.team2635.robot.commands.KickerCommand;
import org.usfirst.frc.team2635.robot.commands.SortCommand;
import org.usfirst.frc.team2635.robot.model.MotionMagicLibrary;
import org.usfirst.frc.team2635.robot.model.SorterControl;
import org.usfirst.frc.team2635.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team2635.robot.subsystems.Extender;
import org.usfirst.frc.team2635.robot.subsystems.Gate;
import org.usfirst.frc.team2635.robot.subsystems.Intake;
import org.usfirst.frc.team2635.robot.subsystems.Kicker;
import org.usfirst.frc.team2635.robot.subsystems.SorterB;
import org.usfirst.frc.team2635.robot.subsystems.SorterC;
import org.usfirst.frc.team2635.robot.subsystems.Vision;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	public static final ExampleSubsystem kExampleSubsystem
			= new ExampleSubsystem();
	public static OI oi;
	public static Drive drive;
	public static Kicker kicker;
	public static ColorSensorTCS34725 colorSensor;
	public static Vision vision;
	//public static Extender extender;
	public static Dispenser dispenser;
	public static Intake intake;
	public static SortCommand sorterCommand;
	public static Gate gate;
//	public static SorterC sorter1;
//	public static SorterC sorter2;
//	public static SorterC sorter3;
//	public static SorterC sorter4;
	public static SorterB sorter;
	
	
	
	public static IntakeCommand intakeCommand;
	public static DispenserCommand dispenserCommand;
	public static SorterControl sortcontrol;
	public static AutonomousCommand autoCommand;
	public static DriveCommand driveCommand;
	public static GateCommand gateCommand;
	public static ExtenderCommand extenderCommand;
	
	KickerCommand kickerCommand;
	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		oi = new OI();
		kicker = new Kicker();
		colorSensor = new ColorSensorTCS34725();
		kickerCommand = new KickerCommand();
		vision = new Vision();
		//extender = new Extender();
		gate = new Gate();
		dispenser = new Dispenser();
		intake = new Intake();
		drive = new Drive();
//		DoubleSolenoid sorterChamber1 = new DoubleSolenoid(0, 2, 3);
//		DoubleSolenoid sorterPanel1 = new DoubleSolenoid(0, 1,0);
//		DoubleSolenoid sorterChamber2 = new DoubleSolenoid(0, 6, 7);
//		DoubleSolenoid sorterPanel2 = new DoubleSolenoid(0, 5,4);
//		DoubleSolenoid sorterChamber3 = new DoubleSolenoid(2, 2, 3);
//		DoubleSolenoid sorterPanel3 = new DoubleSolenoid(2, 1,0);
//		DoubleSolenoid sorterChamber4 = new DoubleSolenoid(2, 6, 7);
//		DoubleSolenoid sorterPanel4 = new DoubleSolenoid(2, 5,4); 
//		
//		sorter1 = new SorterC(sorterChamber1, sorterPanel1);
//		sorter2 = new SorterC(sorterChamber2, sorterPanel2);
//		sorter3 = new SorterC(sorterChamber3, sorterPanel3);
//		sorter4 = new SorterC(sorterChamber4, sorterPanel4);
		
		sorter = new SorterB();
		sortcontrol = new SorterControl();
		//sorterCommand = new SortCommand();
		autoCommand = new AutonomousCommand();
		dispenserCommand = new DispenserCommand();
		intakeCommand = new IntakeCommand();
		gateCommand = new GateCommand(1.0);
		//extenderCommand = new ExtenderCommand(1.0);
		
		
		driveCommand = new DriveCommand();
		m_chooser = new SendableChooser<Command>();

		oi.intakeButton.toggleWhenPressed(intakeCommand);
		oi.dispenserButton.toggleWhenPressed(dispenserCommand);
		oi.driveButton.toggleWhenPressed(driveCommand);
		
		oi.gateButton.whenPressed(gateCommand);
		oi.kickerButton.whenPressed(kickerCommand); 
		oi.extenderButton.whenPressed(extenderCommand);
		
		
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);
		
		m_chooser.addDefault("Default Auto", new ExampleCommand());
		
		//TODO: Replace constants in AutonomousStraightCommand
		m_chooser.addObject("Go Forward", new AutonomousStraightCommand(1.0, 1.0, 1.0));

		//TODO: Replace constants in AutonomousTurnCommand
		m_chooser.addObject("Turn", new AutonomousTurnCommand(100.0, 1.0));

	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		
		
		if (autoCommand != null && autoCommand.isRunning())
		{
			autoCommand.cancel();
		}
		if (driveCommand != null && driveCommand.isRunning())
		{
			driveCommand.cancel();
		}
	}

	@Override
	public void disabledPeriodic() {
		
		//Code to test that chooser works properly.
//		m_autonomousCommand = m_chooser.getSelected();		
//		String selectedCommandName = m_chooser.getSelected().getName();
//		System.out.println("Selected Command:" + selectedCommandName);

		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
	
		m_autonomousCommand = m_chooser.getSelected();

		drive.autoInit();
		
		String selectedCommandName = m_chooser.getSelected().getName();
		System.out.println("Running:" + selectedCommandName);
		//m_autonomousCommand = MotionMagicLibrary.DoThing();
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		colorSensor.senseLoop();
		int[] valueArray = sortcontrol.control();
		sorter.autoSortLoop(valueArray);
		
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		
		if (autoCommand != null) {
			autoCommand.cancel();
		}
		
		if (drive != null) {
			drive.teleInit();
		}
		
		if (driveCommand != null) {
			driveCommand.start();
		}
		sorter.teleInit();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		if(oi.leftStick.getRawButton(11)){sorter.interuptAll();}
		//driveCommand.start();
		colorSensor.senseLoop();
		int[] cant = sortcontrol.control();
		//new SortCommand(sorter0, cant[0]).start();
		sorter.sortLoop(cant);
		
		//Andrew's Code
		////sorter.sort(cant);
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		//colorSensor.senseLoop();
		//int[] cant = sortcontrol.control();
		//sorter.sortLoop(cant);
	}
}
