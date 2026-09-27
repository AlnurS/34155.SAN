package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.config.RobotConstants;

/**
 * Subsystem for Stopper Servo.
 */
public class Stopper {
    private ServoImplEx stopperServo;
    private AnalogInput analogEncoder;

    public void init(HardwareMap hardwareMap) {
        try {
            stopperServo = hardwareMap.get(ServoImplEx.class, RobotConstants.STOPPER_SERVO_NAME);
            // Устанавливаем сервопривод в исходный угол 90 градусов
            setDegrees(90.0);
        } catch (Exception e) {
            stopperServo = null;
        }

        try {
            // Опционально: считывание физического угла через аналоговый энкодер
            analogEncoder = hardwareMap.get(AnalogInput.class, RobotConstants.STOPPER_SERVO_NAME);
        } catch (Exception e) {
            analogEncoder = null;
        }
    }

    /**
     * Устанавливает угол сервопривода в градусах (0° - 180°).
     * 90° = 0.5 позиции сервопривода.
     * @param degrees Угол в градусах (например, 90, 100, 80)
     */
    public void setDegrees(double degrees) {
        if (stopperServo != null) {
            enablePwm();
            double clampedDegrees = Range.clip(degrees, 0.0, 180.0);
            stopperServo.setPosition(clampedDegrees / 180.0);
        }
    }

    /**
     * Отключает удержание (PWM Off) — сервопривод не зажат и свободно вращается.
     */
    public void disablePwm() {
        if (stopperServo != null) {
            stopperServo.setPwmDisable();
        }
    }

    /**
     * Включает удержание (PWM On).
     */
    public void enablePwm() {
        if (stopperServo != null) {
            stopperServo.setPwmEnable();
        }
    }

    /**
     * Возвращает текущий угол в градусах (0° - 180° или 0° - 360° для аналогового энкодера).
     */
    public double getDegrees() {
        if (analogEncoder != null) {
            return (analogEncoder.getVoltage() / 3.3) * 360.0;
        }
        if (stopperServo != null) {
            return stopperServo.getPosition() * 180.0;
        }
        return 0.0;
    }

    public double getPosition() {
        if (stopperServo != null) {
            return stopperServo.getPosition();
        }
        return 0.0;
    }
}
