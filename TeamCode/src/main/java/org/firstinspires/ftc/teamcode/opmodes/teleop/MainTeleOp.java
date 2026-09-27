package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.config.RobotConstants;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

@TeleOp(name = "Main TeleOp", group = "TeleOp")
public class MainTeleOp extends LinearOpMode {

    private final Drivetrain drivetrain = new Drivetrain();

    @Override
    public void runOpMode() throws InterruptedException {
        drivetrain.init(hardwareMap);

        telemetry.addData("Status", "Pinpoint Готов! Нажмите Play.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Обновляем состояние Pinpoint
            drivetrain.updateOdometry();

            // Сброс координат кнопкой Y
            if (gamepad1.y) {
                drivetrain.resetHeading();
            }

            // Переключение скорости
            if (gamepad1.left_bumper) {
                drivetrain.setSpeedMultiplier(RobotConstants.SLOW_SPEED);
            } else {
                drivetrain.setSpeedMultiplier(RobotConstants.NORMAL_SPEED);
            }

            double drive = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;

            drivetrain.driveMecanumSmooth(drive, strafe, turn);

            // Вывод данных с Pinpoint
            telemetry.addData("--- PINPOINT ODOMETRY ---", "");
            telemetry.addData("X (Inches)", "%.2f in", drivetrain.getXInches());
            telemetry.addData("Y (Inches)", "%.2f in", drivetrain.getYInches());
            telemetry.addData("Heading (IMU)", "%.2f deg", drivetrain.getHeadingDegrees());
            telemetry.addData("--- POWER ---", "Battery: %.2f V", drivetrain.getBatteryVoltage());
            telemetry.update();
        }
    }
}
