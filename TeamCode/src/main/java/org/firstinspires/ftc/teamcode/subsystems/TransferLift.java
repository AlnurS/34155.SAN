package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.config.RobotConstants;

public class TransferLift {
    private DcMotor liftLeft, liftRight;

    private int targetPosition = RobotConstants.LIFT_POSITION_DOWN;
    private boolean moving = false;

    public void init(HardwareMap hardwareMap) {
        liftLeft = hardwareMap.get(DcMotor.class, RobotConstants.LIFT_MOTOR_LEFT_NAME);
        liftRight = hardwareMap.get(DcMotor.class, RobotConstants.LIFT_MOTOR_RIGHT_NAME);

        liftRight.setDirection(DcMotor.Direction.REVERSE);
        liftLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void moveToPosition(int ticks) {
        if (ticks < RobotConstants.LIFT_SOFT_LIMIT_MIN) ticks = RobotConstants.LIFT_SOFT_LIMIT_MIN;
        if (ticks > RobotConstants.LIFT_SOFT_LIMIT_MAX) ticks = RobotConstants.LIFT_SOFT_LIMIT_MAX;

        targetPosition = ticks;
        moving = true;
    }

    public void moveUp() {
        moveToPosition(RobotConstants.LIFT_POSITION_UP);
    }

    public void moveDown() {
        moveToPosition(RobotConstants.LIFT_POSITION_DOWN);
    }

    public void update() {
        int currentPos = getCurrentPosition();
        int error = targetPosition - currentPos;

        if (Math.abs(error) < RobotConstants.LIFT_POSITION_TOLERANCE) {
            liftLeft.setPower(0);
            liftRight.setPower(0);
            moving = false;
            return;
        }

        double power = error * 0.005;
        power = Math.max(-RobotConstants.LIFT_MAX_POWER, Math.min(RobotConstants.LIFT_MAX_POWER, power));

        liftLeft.setPower(power);
        liftRight.setPower(power);
    }

    public boolean isBusy() {
        return moving;
    }

    public int getCurrentPosition() {
        return (liftLeft.getCurrentPosition() + liftRight.getCurrentPosition()) / 2;
    }

    public void stop() {
        liftLeft.setPower(0);
        liftRight.setPower(0);
    }
}