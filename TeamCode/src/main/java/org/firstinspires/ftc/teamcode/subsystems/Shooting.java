package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.config.RobotConstants;

public class Shooting {
    private DcMotorEx shooting1, shooting2;
    private Servo feederServo;
    private VoltageSensor voltageSensor;

    private double targetPower = 0;
    private double powerCoefficient = RobotConstants.SHOOTING_POWER_COEFFICIENT;

    // Максимальная скорость моторов в тиках в секунду (для 6000 RPM / Yellowjacket 1:1)
    public static final double DEFAULT_MAX_TICKS_PER_SECOND = 2800.0;

    private final ElapsedTime spinTimer = new ElapsedTime();
    private boolean spinningUp = false;

    public void init(HardwareMap hardwareMap) {
        try {
            shooting1 = hardwareMap.get(DcMotorEx.class, RobotConstants.SHOOTING1_MOTOR_NAME);
            shooting2 = hardwareMap.get(DcMotorEx.class, RobotConstants.SHOOTING2_MOTOR_NAME);

            // Режим RUN_USING_ENCODER включает аппаратный PIDF регулятор на REV Control Hub (1000 Гц)
            shooting1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            shooting2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            // Разнонаправленные моторы (инвертированное направление стрельбы)
            shooting1.setDirection(DcMotor.Direction.REVERSE);
            shooting2.setDirection(DcMotor.Direction.FORWARD);

            shooting1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            shooting2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        } catch (Exception e) {
            shooting1 = null;
            shooting2 = null;
        }

        try {
            voltageSensor = hardwareMap.voltageSensor.iterator().next();
            if (voltageSensor != null && voltageSensor.getVoltage() > 6.0) {
                filteredVoltage = voltageSensor.getVoltage();
            }
        } catch (Exception e) {
            voltageSensor = null;
        }

        try {
            feederServo = hardwareMap.get(Servo.class, RobotConstants.FEEDER_SERVO_NAME);
        } catch (Exception e) {
            feederServo = null;
        }
    }

    public void spinUp() {
        setPower(RobotConstants.SHOOTING_POWER_SPINUP);
        spinningUp = true;
        spinTimer.reset();
    }

    public void idle() {
        setPower(RobotConstants.SHOOTING_IDLE_POWER);
        spinningUp = false;
    }

    public void reverse() {
        setPower(RobotConstants.SHOOTING_POWER_REVERSE);
        spinningUp = false;
    }

    public void setPower(double power) {
        this.targetPower = power;
    }

    public void setPowerCoefficient(double coefficient) {
        this.powerCoefficient = coefficient;
    }

    public double getPowerCoefficient() {
        return powerCoefficient;
    }

    private double filteredVoltage = RobotConstants.NOMINAL_VOLTAGE;

    public double getBatteryVoltage() {
        if (voltageSensor == null) return RobotConstants.NOMINAL_VOLTAGE;
        double currentVoltage = voltageSensor.getVoltage();
        if (currentVoltage < 6.0) {
            return RobotConstants.NOMINAL_VOLTAGE;
        }
        filteredVoltage = 0.05 * currentVoltage + 0.95 * filteredVoltage;
        return filteredVoltage;
    }

    public double getVoltageCompensationFactor() {
        double currentVoltage = getBatteryVoltage();
        return RobotConstants.NOMINAL_VOLTAGE / currentVoltage;
    }

    private double maxVelocityTicksPerSecond = DEFAULT_MAX_TICKS_PER_SECOND;

    public void setMaxVelocityTicksPerSecond(double maxVelocity) {
        this.maxVelocityTicksPerSecond = maxVelocity;
    }

    public double getMaxVelocity() {
        return maxVelocityTicksPerSecond;
    }

    public void update() {
        if (shooting1 == null || shooting2 == null) {
            return;
        }

        if (Math.abs(targetPower) < 0.05) {
            shooting1.setVelocity(0);
            shooting2.setVelocity(0);
            return;
        }

        // Вычисляем целевую скорость в тиках в секунду (ticks / sec)
        double maxVel = getMaxVelocity();
        double targetVelocity = targetPower * powerCoefficient * maxVel;

        // Передаем одинаковую целевую скорость в аппаратный PIDF-контроллер REV Hub для обоих моторов.
        // Поскольку моторы на одном валу, аппаратный PIDF каждого мотора поддерживает скорость независимо,
        // предотвращая борьбу моторов и перегрев.
        shooting1.setVelocity(targetVelocity);
        shooting2.setVelocity(targetVelocity);
    }

    public double getVelocity1() {
        return shooting1 != null ? shooting1.getVelocity() : 0.0;
    }

    public double getVelocity2() {
        return shooting2 != null ? shooting2.getVelocity() : 0.0;
    }

    public boolean isReady() {
        return spinningUp && spinTimer.seconds() > RobotConstants.SHOOTING_SPINUP_TIME_SEC;
    }

    public void feed() {
        if (feederServo != null) {
            feederServo.setPosition(RobotConstants.FEEDER_POSITION_OPEN);
        }
    }

    public void stopFeed() {
        if (feederServo != null) {
            feederServo.setPosition(RobotConstants.FEEDER_POSITION_CLOSED);
        }
    }

    public void stop() {
        setPower(0);
        spinningUp = false;
    }

    public int getPos1() { return shooting1 != null ? shooting1.getCurrentPosition() : 0; }
    public int getPos2() { return shooting2 != null ? shooting2.getCurrentPosition() : 0; }
}
