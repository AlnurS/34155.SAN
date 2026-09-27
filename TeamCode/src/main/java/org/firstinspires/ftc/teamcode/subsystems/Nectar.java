package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.config.RobotConstants;

public class Nectar {

    public enum NectarState {
        POS1,
        POS2,
        POS3
    }

    private Servo rightServo;
    private NectarState state = NectarState.POS1;

    public void init(HardwareMap hardwareMap) {
        rightServo = hardwareMap.get(Servo.class, RobotConstants.SERVO_RIGHT_NAME);
        pos1();
    }

    public void pos1() {
        rightServo.setPosition(RobotConstants.SERVO_POS_1);
        state = NectarState.POS1;
    }

    public void pos2() {
        rightServo.setPosition(RobotConstants.SERVO_POS_2);
        state = NectarState.POS2;
    }

    public void pos3() {
        rightServo.setPosition(RobotConstants.SERVO_POS_3);
        state = NectarState.POS3;
    }

    public NectarState getState() {
        return state;
    }
}