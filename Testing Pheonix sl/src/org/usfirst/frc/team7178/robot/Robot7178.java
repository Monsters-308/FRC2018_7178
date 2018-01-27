/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team7178.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.Joystick;
import org.usfirst.frc.team7178.robot.commands.ExampleCommand;
import org.usfirst.frc.team7178.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj.AnalogGyro;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot7178 extends TimedRobot {
	public static final ExampleSubsystem kExampleSubsystem
			= new ExampleSubsystem();
	public static OI m_oi;
	
	MecanumDrive m_drive;
	Joystick jstick;
	AnalogGyro gyro;
	
	private static final int leftFrontCANChannel1 = 4;
	private static final int leftBackCANChannel1 = 2;
	private static final int rightFrontCANChannel1 = 7;
	private static final int rightBackCANChannel1 = 3;
	
	private static final int leftFrontCANChannel2 = 5;
	private static final int leftBackCANChannel2 = 1;
	private static final int rightFrontCANChannel2 = 6;
	private static final int rightBackCANChannel2 = 0;
	
	private static final int joystickChannel = 0;
	private static final int gyroChannel = 0;
	private static final double gyroSense = 0.0011;
	      
	      
	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_oi = new OI();
		m_chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);
		
		WPI_TalonSRX leftFront1 = new WPI_TalonSRX(leftFrontCANChannel1);
		WPI_TalonSRX leftBack1 = new WPI_TalonSRX(leftBackCANChannel1);
		WPI_TalonSRX rightBack1 = new WPI_TalonSRX(rightBackCANChannel1);
		WPI_TalonSRX rightFront1 = new WPI_TalonSRX(rightFrontCANChannel1);
	
		WPI_TalonSRX leftFront2 = new WPI_TalonSRX(leftFrontCANChannel2);
		WPI_TalonSRX leftBack2 = new WPI_TalonSRX(leftBackCANChannel2);
		WPI_TalonSRX rightBack2 = new WPI_TalonSRX(rightBackCANChannel2);
		WPI_TalonSRX rightFront2 = new WPI_TalonSRX(rightFrontCANChannel2);
		
		leftFront1.setInverted(true);
		leftFront2.setInverted(true);
		leftBack1.setInverted(true);
		leftBack2.setInverted(true);
//		rightFront1.setInverted(true);
//		rightFront2.setInverted(true);
//		rightBack1.setInverted(true);
//		rightBack2.setInverted(true);
		
		leftFront2.follow(leftFront1);
		leftBack2.follow(leftBack1);
		rightFront2.follow(rightFront1);
		rightBack2.follow(rightBack1);
		
				
		m_drive = new MecanumDrive(leftFront1, leftBack1, rightFront1, rightBack1);
		jstick = new Joystick(joystickChannel);
		gyro = new AnalogGyro(gyroChannel);
		gyro.reset();
		gyro.setSensitivity(gyroSense);
		
		
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
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
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		
		m_drive.driveCartesian(jstick.getX(), jstick.getY(), jstick.getZ(), gyro.getAngle());
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
