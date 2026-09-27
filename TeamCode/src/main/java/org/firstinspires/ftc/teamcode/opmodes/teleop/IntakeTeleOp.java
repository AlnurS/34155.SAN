package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

@TeleOp(name = "Intake Control (L2 / R2) Dual", group = "TeleOp")
public class IntakeTeleOp extends LinearOpMode {

    private final Intake intake = new Intake();

    @Override
    public void runOpMode() throws InterruptedException {
        
        intake.init(hardwareMap);

        telemetry.addData("Status", "Initialized. Ready for Start!");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            double l2_trigger = gamepad1.left_trigger;
            double r2_trigger = gamepad1.right_trigger;

            // L2 -> Power Positive (M1:1, M2:-1)
            // R2 -> Power Negative (M1:-1, M2:1)
            double power = l2_trigger - r2_trigger;
            intake.setPower(power);
            intake.update();

            telemetry.addData("Target Power", "%.2f", power);
            telemetry.addData("Compensated Power", "%.2f", intake.getCompensatedPower());
            telemetry.addData("Power Coefficient", "%.2f", intake.getPowerCoefficient());
            telemetry.addData("Battery Voltage", "%.2f V", intake.getBatteryVoltage());
            telemetry.addData("Voltage Factor", "%.2f", intake.getVoltageCompensationFactor());
            telemetry.addData("Pos1", intake.getPos1());
            telemetry.addData("Pos2", intake.getPos2());
            telemetry.addData("Diff", intake.getPos1() - intake.getPos2());
            telemetry.update();
        }
    }
}
