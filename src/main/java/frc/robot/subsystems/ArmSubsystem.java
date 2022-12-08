package frc.robot.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {
    private CANSparkMax m_motor = new CANSparkMax(Constants.CAN_Devices.ARM_MOTOR,
            CANSparkMaxLowLevel.MotorType.kBrushless);
    private RelativeEncoder m_encoder = m_motor.getEncoder();
    private SparkMaxPIDController m_motorPID = m_motor.getPIDController();

    private double
            collectPosition = 0.0,
            drivePosition = 0.0,
            startPosition = 0.0,
            dumpPosition = 0.0;

    private double
            kP = 0.5,
            kI = 0.2,
            kIZone = 0.0;

    /**
     * Creates a new instance of this ArmSubsystem. This constructor
     * is private since this class is a Singleton. Code should use
     * the {@link #getInstance()} method to get the singleton instance.
     */
    private ArmSubsystem() {
        initializeArmEncoder();
        setPID(m_motorPID, kP,kI, kIZone);
    }

    public void initializeArmEncoder() {
        m_motorPID.setReference(startPosition, CANSparkMax.ControlType.kPosition);
    }

    public double getArmPosition() {
        return m_encoder.getPosition();
    }

    private void setPID(SparkMaxPIDController motor, double kP, double kI, double kIZone) {
        motor.setP(kP);
        motor.setI(kI);
        motor.setIZone(kIZone);
        motor.setFF(0.0);
        motor.setD(0.0);
    }

    public void goToCollectPosition() {
        m_motorPID.setReference(collectPosition, CANSparkMax.ControlType.kPosition);
    }

    public void goToDrivePosition() {
        m_motorPID.setReference(drivePosition, CANSparkMax.ControlType.kPosition);
    }

    public void goToDumpPosition() {
        m_motorPID.setReference(dumpPosition, CANSparkMax.ControlType.kPosition);
    }

    /**
     * The Singleton instance of this ArmSubsystem. Code should use
     * the {@link #getInstance()} method to get the single instance (rather
     * than trying to construct an instance of this class.)
     */
    private final static ArmSubsystem INSTANCE = new ArmSubsystem();

    /**
     * Returns the Singleton instance of this ArmSubsystem. This static method
     * should be used, rather than the constructor, to get the single instance
     * of this class. For example: {@code ArmSubsystem.getInstance();}
     */
    @SuppressWarnings("WeakerAccess")
    public static ArmSubsystem getInstance() {
        return INSTANCE;
    }
}

