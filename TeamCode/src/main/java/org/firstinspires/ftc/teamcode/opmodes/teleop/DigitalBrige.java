package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooting;
import org.firstinspires.ftc.teamcode.subsystems.Stopper;

@TeleOp(name = "DigitalBrige", group = "TeleOp")
public class DigitalBrige extends LinearOpMode {

    private final Drivetrain drivetrain = new Drivetrain();
    private final Intake intake = new Intake();
    private final Stopper stopper = new Stopper();
    private final Shooting shooting = new Shooting();

    @Override
    public void runOpMode() throws InterruptedException {
        // Инициализация подсистем
        drivetrain.init(hardwareMap);
        intake.init(hardwareMap);
        stopper.init(hardwareMap);
        shooting.init(hardwareMap);
        shooting.setPowerCoefficient(1.0); // Устанавливаем коэффициенты прямого регулирования мощности

        telemetry.addData("Status", "DigitalBrige Ready!");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Обновляем одометрию Pinpoint
            drivetrain.updateOdometry();

            // Сброс угла кнопкой Y
            if (gamepad1.y) {
                drivetrain.resetHeading();
            }

            // ДВИЖЕНИЕ РОБОТА (Левый стик: Вперед/Назад & Стрейф, Правый стик: Поворот)
            double drive = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;

            drivetrain.driveMecanum(drive, strafe, turn);

            // УПРАВЛЕНИЕ INTAKE (L1 / R1)
            // L1 -> Забор (1.0), R1 -> Реверс (-1.0)
            double intakePower = 0.0;
            if (gamepad1.left_bumper) {
                intakePower = 1.0;
            } else if (gamepad1.right_bumper) {
                intakePower = -1.0;
            }
            intake.setPower(intakePower);
            intake.update();

            // УПРАВЛЕНИЕ STOPPER (Кнопка O / Circle / B -> 70°, Иначе -> 90°)
            if (gamepad1.circle || gamepad1.b) {
                stopper.setDegrees(70.0);
            } else {
                stopper.setDegrees(90.0);
            }

            // УПРАВЛЕНИЕ СТРЕЛЬБОЙ (По умолчанию ~0.40, при зажатом R2 (right_trigger) -> ~0.75)
            boolean shooterBoost = gamepad1.right_trigger > 0.1;
            if (shooterBoost) {
                shooting.setPower(0.60);
            } else {
                shooting.setPower(0.3);
            }
            shooting.update();

            // ТЕЛЕМЕТРИЯ НА DRIVER HUB
            telemetry.addData("--- STOPPER SERVO ---", "");
            telemetry.addData("Stopper Degree", "%.1f°", stopper.getDegrees());
            telemetry.addData("Stopper Position", "%.2f", stopper.getPosition());

            telemetry.addData("--- SHOOTING (DcMotorEx PIDF) ---", "");
            telemetry.addData("Target Power", "%.2f", shooterBoost ? 0.70 : 0.40);
            telemetry.addData("Motor 1 Velocity", "%.0f t/s", shooting.getVelocity1());
            telemetry.addData("Motor 2 Velocity", "%.0f t/s", shooting.getVelocity2());

            telemetry.addData("--- ODOMETRY (Pinpoint) ---", "");
            telemetry.addData("X (Inches)", "%.2f in", drivetrain.getXInches());
            telemetry.addData("Y (Inches)", "%.2f in", drivetrain.getYInches());
            telemetry.addData("Heading", "%.2f deg", drivetrain.getHeadingDegrees());

            telemetry.addData("--- INTAKE ---", "");
            telemetry.addData("Target Power", "%.2f", intakePower);
            telemetry.addData("Compensated Power", "%.2f", intake.getCompensatedPower());

            telemetry.addData("--- SYSTEM ---", "");
            telemetry.addData("Battery Voltage", "%.2f V", drivetrain.getBatteryVoltage());
            telemetry.update();
        }
    }
}
