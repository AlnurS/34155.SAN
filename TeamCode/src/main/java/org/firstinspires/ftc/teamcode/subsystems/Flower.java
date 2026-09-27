package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.config.RobotConstants;

public class Flower {

    public enum FlowerState {
        POS1,
        POS2,
        POS3
    }

    private Servo leftServo;
    private FlowerState state = FlowerState.POS1;

    public void init(HardwareMap hardwareMap) {
        leftServo = hardwareMap.get(Servo.class, RobotConstants.SERVO_LEFT_NAME);
        pos1();
    }

    public void pos1() {
        leftServo.setPosition(RobotConstants.SERVO_POS_1);
        state = FlowerState.POS1;
    }

    public void pos2() {
        leftServo.setPosition(RobotConstants.SERVO_POS_2);
        state = FlowerState.POS2;
    }

    public void pos3() {
        leftServo.setPosition(RobotConstants.SERVO_POS_3);
        state = FlowerState.POS3;
    }

    public FlowerState getState() {
        return state;
    }
}