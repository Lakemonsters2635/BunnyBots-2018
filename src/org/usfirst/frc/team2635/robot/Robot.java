/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2635.robot;

import edu.wpi.first.wpilibj.TimedRobot;
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
import org.usfirst.frc.team2635.robot.commands.IntakeCommand;
import org.usfirst.frc.team2635.robot.subsystems.ColorSensorTCS34725;
import org.usfirst.frc.team2635.robot.subsystems.Dispenser;
import org.usfirst.frc.team2635.robot.subsystems.Drive;
import org.usfirst.frc.team2635.robot.commands.KickerCommand;
import org.usfirst.frc.team2635.robot.model.MotionMagicLibrary;
import org.usfirst.frc.team2635.robot.model.SorterControl;
import org.usfirst.frc.team2635.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team2635.robot.subsystems.Extender;
import org.usfirst.frc.team2635.robot.subsystems.Intake;
import org.usfirst.frc.team2635.robot.subsystems.Kicker;
import org.usfirst.frc.team2635.robot.subsystems.SorterB;
import org.usfirst.frc.team2635.robot.subsystems.Vision;
import edu.wpi.first.wpilibj.CameraServer;

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
	public static Extender extender;
	public static Dispenser dispenser;
	public static Intake intake;
	public static SorterB sorter;
	
	public static IntakeCommand intakeCommand;
	public static DispenserCommand dispenserCommand;
	public static SorterControl sortcontrol;
	public static AutonomousCommand autoCommand;
	public static DriveCommand driveCommand;
	
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
		extender = new Extender();
		dispenser = new Dispenser();
		intake = new Intake();
		sorter = new SorterB();
		sortcontrol = new SorterControl();
		autoCommand = new AutonomousCommand();
		dispenserCommand = new DispenserCommand();
		driveCommand = new DriveCommand();
		
		m_chooser = new SendableChooser<Command>();

		oi.intakeButton.toggleWhenPressed(intakeCommand);
		oi.dispenserButton.toggleWhenPressed(dispenserCommand);
		oi.driveButton.toggleWhenPressed(driveCommand);

		
		oi.kickerButton.toggleWhenPressed(kickerCommand); //TODO See if we need to do kickerCommand.set() elsewhere in order to get it to start without button press
		oi.extenderButton.whenPressed(new ExtenderCommand(1.0));
		
		
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);
		drive = new Drive();
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
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		driveCommand.start();
		colorSensor.senseLoop();
		int[] cant = sortcontrol.control();
		sorter.sortLoop(cant);

		//sorter.sort(cant);;
		
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
