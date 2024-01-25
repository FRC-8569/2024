//Libraries url
//https://software-metadata.revrobotics.com/REVLib-2023.json
//https://maven.ctr-electronics.com/release/com/ctre/phoenix/Phoenix5-frc2023-latest.json

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
  private CANSparkMax motor1;
  private CANSparkMax motor2;
  private CANSparkMax motor3;
  private CANSparkMax motor4;
  private CANSparkMax motor5;
  private CANSparkMax motor6;
  private RelativeEncoder encoder5;
  private RelativeEncoder encoder6;
  private MotorControllerGroup right;
  private MotorControllerGroup left;
  private DifferentialDrive drive;
  private Joystick joystick;

  @Override
  public void robotInit() {
    motor1 = new CANSparkMax(1, MotorType.kBrushless);
    motor2 = new CANSparkMax(2, MotorType.kBrushless);
    motor3 = new CANSparkMax(3, MotorType.kBrushless);
    motor4 = new CANSparkMax(4, MotorType.kBrushless);
    motor5 = new CANSparkMax(5, MotorType.kBrushless);
    motor6 = new CANSparkMax(6, MotorType.kBrushless);

    encoder5 = motor5.getEncoder();
    encoder6 = motor6.getEncoder();

    motor5.restoreFactoryDefaults();
    encoder5.setPosition(0);

    motor6.restoreFactoryDefaults();
    encoder6.setPosition(0);

    left = new MotorControllerGroup(motor1, motor2);
    right = new MotorControllerGroup(motor3, motor4);

    drive = new DifferentialDrive(left, right);
    joystick = new Joystick(0);
  }

  @Override
  public void teleopPeriodic() {
    double turnSpeed = 0.3 * joystick.getRawAxis(0);
    double driveSpeed = 0.5 * joystick.getRawAxis(5);

    double motorPos5 = encoder5.getPosition();
    double motorSpd5 = encoder5.getVelocity();
    double motorPos6 = encoder6.getPosition();
    double motorSpd6 = encoder6.getVelocity();

    if (joystick.getRawButton(0)) {
      motor5.set(0.3);
    } else if (joystick.getRawButton(1)) {
      motor5.set(-0.3);
    } else {
      motor5.set(0);
    }

    if (joystick.getRawButton(2)) {
      motor6.set(0.3);
    } else if (joystick.getRawButton(3)) {
      motor6.set(-0.3);
    } else {
      motor6.set(0);
    }

    if (joystick.getRawButton(5)) {
      motor5.set(0.3);
      motor6.set(-0.3);
    } else if (joystick.getRawButton(6)) {
      motor5.set(-0.3);
      motor6.set(0.3);
    } else {
      motor5.set(0);
      motor6.set(0);
    }

    drive.arcadeDrive(turnSpeed, driveSpeed);

    System.out.println(motorPos5);

    SmartDashboard.putNumber("turnSpeed", turnSpeed);
    SmartDashboard.putNumber("driveSpeed", driveSpeed);
    SmartDashboard.putNumber("motor5Pos", motorPos5);
    SmartDashboard.putNumber("motor5Spd", motorSpd5);
    SmartDashboard.putNumber("motor6Pos", motorPos6);
    SmartDashboard.putNumber("motor6Spd", motorSpd6);
  }
}