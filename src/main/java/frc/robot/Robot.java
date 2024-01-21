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

public class Robot extends TimedRobot {

  private MotorControllerGroup m_right;
  private MotorControllerGroup m_left;
  private DifferentialDrive m_drive;
  private Joystick joystick;
  private CANSparkMax m_motor1;
  private CANSparkMax m_motor2;
  private CANSparkMax m_motor3;
  private CANSparkMax m_motor4;

  private CANSparkMax m_motor5;
  private RelativeEncoder m_encoder5;

  @Override
  public void robotInit() {
    m_motor1 = new CANSparkMax(1, MotorType.kBrushless);
    m_motor2 = new CANSparkMax(2, MotorType.kBrushless);
    m_motor3 = new CANSparkMax(3, MotorType.kBrushless);
    m_motor4 = new CANSparkMax(4, MotorType.kBrushless);
    m_motor5 = new CANSparkMax(5, MotorType.kBrushless);

    m_motor5.restoreFactoryDefaults();
    m_encoder5 = m_motor5.getEncoder();
    m_encoder5.setPosition(0);

    m_left = new MotorControllerGroup(m_motor1, m_motor2);
    m_right = new MotorControllerGroup(m_motor3, m_motor4);

    m_drive = new DifferentialDrive(m_left, m_right);
    joystick = new Joystick(0);
  }

  @Override
  public void teleopPeriodic() {
    double turnSpeed = 0.5 * joystick.getRawAxis(0);
    double driveSpeed = 0.7 * joystick.getRawAxis(5);
    double position5 = m_encoder5.getPosition();
    double speedMotor5 = m_encoder5.getVelocity();

    if (joystick.getRawButton(1)) {
      m_motor5.set(0.5);
    } else if (joystick.getRawButton(2)) {
      m_motor5.set(-0.5);
    } else {
      m_motor5.set(0);
    }

    m_drive.arcadeDrive(turnSpeed, driveSpeed);

    System.out.println("position: " + position5 + ", speed: " + speedMotor5);
  }
}