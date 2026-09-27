package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.config.RobotConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;

/**
 * Simplified Mecanum Drivetrain with GoBilda Pinpoint Odometry & Pedro Pathing support.
 */
public class Drivetrain {
    private DcMotor leftFront, rightFront, leftBack, rightBack;
    private GoBildaPinpointDriver pinpoint;
    private VoltageSensor voltageSensor;
    private Follower pedroFollower;

    private double speedMultiplier = RobotConstants.NORMAL_SPEED;

    public void init(HardwareMap hardwareMap) {
        leftFront = hardwareMap.get(DcMotor.class, RobotConstants.LEFT_FRONT);
        rightFront = hardwareMap.get(DcMotor.class, RobotConstants.RIGHT_FRONT);
        leftBack = hardwareMap.get(DcMotor.class, RobotConstants.LEFT_BACK);
        rightBack = hardwareMap.get(DcMotor.class, RobotConstants.RIGHT_BACK);

        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);

        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Датчик напряжения
        try {
            voltageSensor = hardwareMap.voltageSensor.iterator().next();
        } catch (Exception e) {
            voltageSensor = null;
        }

        // Инициализация Pinpoint Odometry
        try {
            pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, RobotConstants.PINPOINT_NAME);
            pinpoint.setOffsets(RobotConstants.X_POD_OFFSET_MM, RobotConstants.Y_POD_OFFSET_MM, DistanceUnit.MM);
            pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_SWINGARM_POD);
            pinpoint.setEncoderDirections(RobotConstants.X_DIRECTION, RobotConstants.Y_DIRECTION);
            pinpoint.resetPosAndIMU();
        } catch (Exception e) {
            pinpoint = null;
        }

        // Pedro Pathing Follower
        try {
            pedroFollower = new Follower(hardwareMap);
        } catch (Exception e) {
            pedroFollower = null;
        }
    }

    public Follower getPedroFollower() {
        return pedroFollower;
    }

    public void updateOdometry() {
        if (pinpoint != null) {
            pinpoint.update();
        }
    }

    public double getBatteryVoltage() {
        if (voltageSensor == null) return RobotConstants.NOMINAL_VOLTAGE;
        return voltageSensor.getVoltage();
    }

    public void driveMecanum(double drive, double strafe, double turn) {
        double lf = (drive + strafe + turn) * speedMultiplier;
        double rf = (drive - strafe - turn) * speedMultiplier;
        double lb = (drive - strafe + turn) * speedMultiplier;
        double rb = (drive + strafe - turn) * speedMultiplier;

        double max = Math.max(Math.abs(lf), Math.abs(rf));
        max = Math.max(max, Math.abs(lb));
        max = Math.max(max, Math.abs(rb));

        if (max > 1.0) {
            lf /= max;
            rf /= max;
            lb /= max;
            rb /= max;
        }

        leftFront.setPower(lf);
        rightFront.setPower(rf);
        leftBack.setPower(lb);
        rightBack.setPower(rb);
    }

    public void driveMecanumSmooth(double drive, double strafe, double turn) {
        driveMecanum(Math.pow(drive, 3), Math.pow(strafe, 3), Math.pow(turn, 3));
    }

    public void stopMotors() {
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
    }

    public Pose2D getPose() {
        return pinpoint != null ? pinpoint.getPosition() : new Pose2D(DistanceUnit.INCH, 0, 0, AngleUnit.DEGREES, 0);
    }

    public double getXInches() {
        return pinpoint != null ? pinpoint.getPosition().getX(DistanceUnit.INCH) : 0;
    }

    public double getYInches() {
        return pinpoint != null ? pinpoint.getPosition().getY(DistanceUnit.INCH) : 0;
    }

    public double getHeadingDegrees() {
        return pinpoint != null ? pinpoint.getPosition().getHeading(AngleUnit.DEGREES) : 0;
    }

    public void resetHeading() {
        if (pinpoint != null) {
            pinpoint.resetPosAndIMU();
        }
    }

    public void setSpeedMultiplier(double multiplier) {
        this.speedMultiplier = multiplier;
    }

    public void setCurrentPose(double xInches, double yInches, double headingDegrees) {
        if (pinpoint != null) {
            pinpoint.setPosition(new Pose2D(DistanceUnit.INCH, xInches, yInches, AngleUnit.DEGREES, headingDegrees));
        }
    }

    private void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior) {
        leftFront.setZeroPowerBehavior(behavior);
        rightFront.setZeroPowerBehavior(behavior);
        leftBack.setZeroPowerBehavior(behavior);
        rightBack.setZeroPowerBehavior(behavior);
    }
}
