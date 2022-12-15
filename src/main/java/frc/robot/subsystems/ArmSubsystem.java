package frc.robot.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {
    private CANSparkMax m_motor = new CANSparkMax(Constants.CAN_Devices.ARM_MOTOR,
            CANSparkMaxLowLevel.MotorType.kBrushless);
    private RelativeEncoder m_encoder = m_motor.getEncoder();
    private SparkMaxPIDController m_motorPID = m_motor.getPIDController();

    private int COLLECT_POSITION = 1, DRIVE_POSITION = 2, START_POSITION = 0, DUMP_POSITION = 3;

    private final double positionInc = 2.0;

    private double positions[] = {168.256, 0.0, 90.0, 181.5};
    private int currentIndex;

    private double
            kP = 0.1,
            kI = 0.0005,
            kIZone = 2.0;

    /**
     * Creates a new instance of this ArmSubsystem. This constructor
     * is private since this class is a Singleton. Code should use
     * the {@link #getInstance()} method to get the singleton instance.
     */
    private ArmSubsystem() {
        m_motor.restoreFactoryDefaults();
        m_motor.setInverted(true);
        initializeArmEncoder();
        setPID(m_motorPID, kP,kI, kIZone);
    }

    public void initializeArmEncoder() {
        m_encoder.setPosition(positions[START_POSITION]);
        currentIndex = START_POSITION;
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
        currentIndex = COLLECT_POSITION;
        goToPosition();
    }

    public void goToDrivePosition() {
        currentIndex = DRIVE_POSITION;
        goToPosition();
    }

    public void goToDumpPosition() {
        currentIndex = DUMP_POSITION;
        goToPosition();
    }

    public void goToPosition() {
        m_motorPID.setReference(positions[currentIndex], CANSparkMax.ControlType.kPosition);
    }

    public void bumpUp() {
        positions[currentIndex] += positionInc;
        goToPosition();
    }

    public void bumpDown() {
        positions[currentIndex] -= positionInc;
        goToPosition();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Arm Position", getArmPosition());
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

