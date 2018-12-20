package org.usfirst.frc.team2635.robot.subsystems;

import org.usfirst.frc.team2635.robot.Robot;
import org.usfirst.frc.team2635.robot.RobotMap;
import org.usfirst.frc.team2635.robot.model.GateParams;
import org.usfirst.frc.team2635.robot.model.MotionParameters;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Subsystem;
 
/**
 *
 */
public class Gate extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	DoubleSolenoid gate;
	public double errorReport = 0;
	PowerDistributionPanel pdp;
	public Gate() {
		gate = new DoubleSolenoid(1, 4, 5);
	}
	public void moveGate(GateParams params) {	
		//gate.set(Value.kForward)
	}
	public void motorControl(ControlMode controlMode, Double wheelRotations){

	}
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}