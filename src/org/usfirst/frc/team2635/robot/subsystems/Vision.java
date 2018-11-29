package org.usfirst.frc.team2635.robot.subsystems;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

import edu.wpi.cscore.UsbCamera;
//import edu.wpi.first.wpilibj.vision.CameraServer;
//import edu.wpi.first.wpilibj.vision.USBCamera;
//import edu.wpi.first.wpilibj.vision.;

/**
 *
 */
public class Vision extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	UsbCamera camera;
	
	public Vision() {
		camera = new UsbCamera("camera_1", 0);
		camera.setResolution(160, 90);
		//camera.setSize(160, 90); //resolution
		camera.setFPS(10);
		CameraServer.getInstance().startAutomaticCapture(camera);
		
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

