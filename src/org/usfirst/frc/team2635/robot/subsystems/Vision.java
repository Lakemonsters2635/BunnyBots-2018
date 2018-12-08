package org.usfirst.frc.team2635.robot.subsystems;

//import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;


import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.cscore.CvSource;
import Model.CameraServerFPS;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.MjpegServer;


//import edu.wpi.first.wpilibj.vision.CameraServer;
//import edu.wpi.first.wpilibj.vision.USBCamera;

/**
 *
 */
public class Vision extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	UsbCamera camera;
	
	public Vision() {

		// Creates UsbCamera and MjpegServer [1] and connects them
		CameraServerFPS.getInstance().startAutomaticCapture();
		
		//Creates the CvSink and connects it to the UsbCamera 
		CvSink cvSink = CameraServerFPS.getInstance().getVideo();

		// Creates the CvSource and MjpegServer [2] and connects them
		CameraServerFPS.getInstance().putVideo("Blur", 640, 480);
		
//		UsbCamera usbCamera = new UsbCamera("USB Camera 0", 0);
//		MjpegServer mjpegServer1 = new MjpegServer("serve_USB Camera 0", 1181);
//		mjpegServer1.setSource(usbCamera); CvSink cvSink = new CvSink("opencv_USB Camera 0");
//		cvSink.setSource(usbCamera);
//		CvSource outputStream = new CvSource("Blur", PixelFormat.kMJPEG, 640, 480, 30);
//		MjpegServer mjpegServer2 = new MjpegServer("serve_Blur", 1182);
//		mjpegServer2.setSource(outputStream);
		
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

