package frc.robot.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class CollectorSubsystem extends SubsystemBase {
    private final CANSparkMax m_motor = new CANSparkMax(Constants.CAN_Devices.COLLECTOR_MOTOR,
            CANSparkMaxLowLevel.MotorType.kBrushless);

    private final double speed = 0.65;

    private CollectorSubsystem() {
        m_motor.restoreFactoryDefaults();
    }

    public void spinForward() {
        m_motor.set(speed);
    }

    public void spinBackward() {
        m_motor.set(-speed);
    }

    public void stop() {
        m_motor.set(0.0);
    }

    /**
     * The Singleton instance of this CollectorSubsystem. Code should use
     * the {@link #getInstance()} method to get the single instance (rather
     * than trying to construct an instance of this class.)
     */
    private final static CollectorSubsystem INSTANCE = new CollectorSubsystem();

    /**
     * Returns the Singleton instance of this CollectorSubsystem. This static method
     * should be used, rather than the constructor, to get the single instance
     * of this class. For example: {@code CollectorSubsystem.getInstance();}
     */
    @SuppressWarnings("WeakerAccess")
    public static CollectorSubsystem getInstance() {
        return INSTANCE;
    }
}

