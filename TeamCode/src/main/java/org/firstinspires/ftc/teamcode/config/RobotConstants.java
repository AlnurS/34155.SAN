package org.firstinspires.ftc.teamcode.config;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;

public class RobotConstants {
    // Drivetrain
    public static final String LEFT_FRONT = "leftFront";
    public static final String RIGHT_FRONT = "rightFront";
    public static final String LEFT_BACK = "leftBack";
    public static final String RIGHT_BACK = "rightBack";

    public static final double NORMAL_SPEED = 1.0;
    public static final double SLOW_SPEED = 0.3;

    // Pinpoint Odometry
    public static final String PINPOINT_NAME = "pinpoint";
    public static final GoBildaPinpointDriver.EncoderDirection X_DIRECTION = GoBildaPinpointDriver.EncoderDirection.FORWARD;
    public static final GoBildaPinpointDriver.EncoderDirection Y_DIRECTION = GoBildaPinpointDriver.EncoderDirection.FORWARD;

    // Смещение одо-подов относительно центра вращения робота (в мм) - итерация калибровки 2
    public static final double X_POD_OFFSET_MM = -86.0;
    public static final double Y_POD_OFFSET_MM = 3.9;

    // Voltage Compensation
    public static final double NOMINAL_VOLTAGE = 12.0;

    // Lift система лифтов
    public static final String LIFT_MOTOR_LEFT_NAME = "liftLeft";
    public static final String LIFT_MOTOR_RIGHT_NAME = "liftRight";
    public static final int LIFT_POSITION_DOWN = 0;
    public static final int LIFT_POSITION_UP = 2000;
    public static final int LIFT_POSITION_TOLERANCE = 20;
    public static final double LIFT_MAX_POWER = 0.8;
    public static final int LIFT_SOFT_LIMIT_MIN = -50;
    public static final int LIFT_SOFT_LIMIT_MAX = 2100;

    // Intake ЧИТАТЬ С ЛИЦЕВОЙ СТОРОНЫ РОБОТА
    public static final String INTAKE1_MOTOR_NAME = "intake1";
    public static final String INTAKE2_MOTOR_NAME = "intake2";
    public static final double INTAKE_POWER_COLLECT = 1;
    public static final double INTAKE_POWER_REVERSE = -1;
    public static final double INTAKE_SYNC_KP = 0.002;
    public static final double INTAKE_POWER_COEFFICIENT = 1;

    // Shooting (бывший Launcher)
    public static final String SHOOTING1_MOTOR_NAME = "shooting1";
    public static final String SHOOTING2_MOTOR_NAME = "shooting2";
    public static final String FEEDER_SERVO_NAME = "feederServo";
    public static final double SHOOTING_POWER_SPINUP = 1;
    public static final double SHOOTING_POWER_REVERSE = -1;
    public static final double SHOOTING_SYNC_KP = 0.0067;
    public static final double SHOOTING_POWER_COEFFICIENT = 1;
    public static final double SHOOTING_IDLE_POWER = 0.5;
    public static final double SHOOTING_SPINUP_TIME_SEC = 1.0;
    public static final double FEEDER_POSITION_OPEN = 1.0;
    public static final double FEEDER_POSITION_CLOSED = 0.0;

    // Common Servo Constants (Left & Right) intakeSERVO ЧИТАТЬ С ЛИЦЕВОЙ СТОРОНЫ РОБОТА
    public static final String SERVO_LEFT_NAME = "servoLeft";
    public static final String SERVO_RIGHT_NAME = "servoRight";
    public static final double SERVO_POS_1 = 0.0;
    public static final double SERVO_POS_2 = 0.5;
    public static final double SERVO_POS_3 = 1.0;

    // Stopper Servo
    public static final String STOPPER_SERVO_NAME = "stopperServo";
    public static final double STOPPER_POS_CLOSED = 0.0; // 0°
    public static final double STOPPER_POS_OPEN = 1.0;   // 180°
}