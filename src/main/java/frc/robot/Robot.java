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
  private CANSparkMax motor0; // intake馬達
  private CANSparkMax motor1; // 底盤馬達
  private CANSparkMax motor2; // 底盤馬達
  private CANSparkMax motor3; // 底盤馬達
  private CANSparkMax motor4; // 底盤馬達
  private CANSparkMax motor5; // 升降機構
  private CANSparkMax motor6; // 升降機構
  private CANSparkMax motor7; // Shooter馬達
  private CANSparkMax motor8; // Shooter馬達
  private RelativeEncoder encoder5; // 軸編碼器
  private RelativeEncoder encoder6; // 軸編碼器
  private MotorControllerGroup right;
  private MotorControllerGroup left;
  private DifferentialDrive drive;
  private Joystick joystick;

  @Override
  public void robotInit() {
    motor0 = new CANSparkMax(9, MotorType.kBrushless);
    motor1 = new CANSparkMax(1, MotorType.kBrushless);
    motor2 = new CANSparkMax(2, MotorType.kBrushless);
    motor3 = new CANSparkMax(3, MotorType.kBrushless);
    motor4 = new CANSparkMax(4, MotorType.kBrushless);
    motor5 = new CANSparkMax(5, MotorType.kBrushless);
    motor6 = new CANSparkMax(6, MotorType.kBrushless);
    motor7 = new CANSparkMax(7, MotorType.kBrushless);
    motor8 = new CANSparkMax(8, MotorType.kBrushless);

    encoder5 = motor5.getEncoder();
    encoder6 = motor6.getEncoder();

    motor5.restoreFactoryDefaults();
    motor6.restoreFactoryDefaults();

    encoder5.setPosition(0);
    encoder6.setPosition(0);

    left = new MotorControllerGroup(motor1, motor2);
    right = new MotorControllerGroup(motor3, motor4);

    drive = new DifferentialDrive(left, right);
    joystick = new Joystick(0);
  }

  @Override
  public void teleopPeriodic() {
    // 底盤變數
    double turnSpeed = 0.4 * joystick.getRawAxis(0);
    double driveSpeed = 0.6 * joystick.getRawAxis(5);
    // 編碼器變數 (Pos是位置、Spd是速度)
    double motorPos5 = encoder5.getPosition();
    double motorSpd5 = encoder5.getVelocity();
    double motorPos6 = encoder6.getPosition();
    double motorSpd6 = encoder6.getVelocity();

    // intke運作
    if (joystick.getRawButton(1)) {
      motor0.set(0.8);
    } else if (joystick.getRawButton(2)) {
      motor0.set(-0.8);
    } else {
      motor0.set(0);
    }

    // 升降機構運作
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

    // shooter運作
    if (joystick.getRawButton(3)) {
      motor7.set(0.8);
      motor8.set(0.8);
    } else if (joystick.getRawButton(4)) {
      motor7.set(-0.8);
      motor8.set(-0.8);
    } else {
      motor7.set(0);
      motor8.set(0);
    }

    // 底盤運作
    drive.arcadeDrive(turnSpeed, driveSpeed);

    // 數值監控
    SmartDashboard.putNumber("turnSpeed", turnSpeed);
    SmartDashboard.putNumber("driveSpeed", driveSpeed);
    SmartDashboard.putNumber("motor5Pos", motorPos5);
    SmartDashboard.putNumber("motor5Spd", motorSpd5);
    SmartDashboard.putNumber("motor6Pos", motorPos6);
    SmartDashboard.putNumber("motor6Spd", motorSpd6);
  }
}