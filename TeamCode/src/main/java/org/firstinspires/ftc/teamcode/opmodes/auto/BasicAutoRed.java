package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.config.RobotConstants;

/**
 * Simple Timer-based Autonomous OpMode for RED Alliance with Drive and Strafe.
 */
@Autonomous(name = "Basic Auto RED Alliance", group = "Autonomous")
public class BasicAutoRed extends LinearOpMode {

    private DcMotor leftFront, rightFront, leftBack, rightBack;

    @Override
    public void runOpMode() {
        // Инициализация моторов
        leftFront = hardwareMap.get(DcMotor.class, RobotConstants.LEFT_FRONT);
        rightFront = hardwareMap.get(DcMotor.class, RobotConstants.RIGHT_FRONT);
        leftBack = hardwareMap.get(DcMotor.class, RobotConstants.LEFT_BACK);
        rightBack = hardwareMap.get(DcMotor.class, RobotConstants.RIGHT_BACK);

        // Направление моторов для меканума
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.FORWARD);

        // Торможение при нулевой мощности
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Alliance", "RED");
        telemetry.addData("Status", "Initialized. Ready to start.");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {
            // 1. Едем вперед со скоростью 0.5 в течение 500 мс (0.5 сек)
            telemetry.addData("Status", "Driving Forward...");
            telemetry.update();
            setDrivePower(0.35);
            sleep(500);

            // 2. Едем в бок со скоростью 0.5 в течение 1000 мс (1 сек)
            telemetry.addData("Status", "Strafing...");
            telemetry.update();
            setStrafePower(0.5); // Используйте -0.5 для стрейфа в другую сторону
            sleep(300);

            // 3. Останавливаем моторы
            setDrivePower(0.0);
            telemetry.addData("Status", "Done.");
            telemetry.update();
        }
    }

    private void setDrivePower(double power) {
        leftFront.setPower(power);
        rightFront.setPower(power);
        leftBack.setPower(power);
        rightBack.setPower(power);
    }

    private void setStrafePower(double power) {
        leftFront.setPower(power);
        rightFront.setPower(-power);
        leftBack.setPower(-power);
        rightBack.setPower(power);
    }
}
