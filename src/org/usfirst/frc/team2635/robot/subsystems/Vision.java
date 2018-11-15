package org.usfirst.frc.team2635.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.vision.CameraServer;
import edu.wpi.first.wpilibj.vision.USBCamera;

/**
 *
 */
public class Vision extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	USBCamera camera;
	
	public Vision() {
		camera = new USBCamera();	
		CameraServer.getInstance().startAutomaticCapture(camera);
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

