package org.firstinspires.ftc.teamcode.pedroPathing.follower;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.config.RobotConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathgen.Path;
import org.firstinspires.ftc.teamcode.pedroPathing.pathgen.PathChain;
import org.firstinspires.ftc.teamcode.pedroPathing.pathgen.Point;

/**
 * Pedro Pathing Follower class: Handles odometry updates, path tracking, and motor outputs.
 */
public class Follower {
    private DcMotor leftFront, rightFront, leftBack, rightBack;
    private GoBildaPinpointDriver pinpoint;
    private VoltageSensor voltageSensor;

    private Pose currentPose = new Pose(0, 0, 0);
    private Pose targetPose = new Pose(0, 0, 0);

    private PathChain currentPathChain;
    private int currentPathIndex = 0;
    private boolean isFollowing = false;
    private boolean holdPositionAtEnd = true;

    private double maxPower = 1.0;
    private final ElapsedTime pathTimer = new ElapsedTime();

    public Follower(HardwareMap hardwareMap) {
        // Initialize motors
        leftFront = hardwareMap.get(DcMotor.class, FConstants.leftFrontMotorName);
        rightFront = hardwareMap.get(DcMotor.class, FConstants.rightFrontMotorName);
        leftBack = hardwareMap.get(DcMotor.class, FConstants.leftRearMotorName);
        rightBack = hardwareMap.get(DcMotor.class, FConstants.rightRearMotorName);

        leftFront.setDirection(FConstants.leftFrontMotorDirection);
        leftBack.setDirection(FConstants.leftRearMotorDirection);
        rightFront.setDirection(FConstants.rightFrontMotorDirection);
        rightBack.setDirection(FConstants.rightRearMotorDirection);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Voltage sensor
        try {
            voltageSensor = hardwareMap.voltageSensor.iterator().next();
        } catch (Exception e) {
            voltageSensor = null;
        }

        // Pinpoint Odometry
        try {
            pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, LConstants.pinpointName);
            pinpoint.setOffsets(LConstants.xOffsetMM, LConstants.yOffsetMM, LConstants.distanceUnit);
            pinpoint.setEncoderResolution(LConstants.encoderResolution);
            pinpoint.setEncoderDirections(LConstants.xDirection, LConstants.yDirection);
            pinpoint.resetPosAndIMU();
        } catch (Exception e) {
            pinpoint = null;
        }
    }

    public void setStartingPose(Pose pose) {
        this.currentPose = pose;
        this.targetPose = pose;
        if (pinpoint != null) {
            pinpoint.setPosition(new Pose2D(DistanceUnit.INCH, pose.getX(), pose.getY(), AngleUnit.RADIANS, pose.getHeading()));
        }
    }

    public void setPose(Pose pose) {
        setStartingPose(pose);
    }

    public Pose getPose() {
        return currentPose;
    }

    public void followPath(Path path, boolean holdPosition) {
        followPath(new PathChain(path), holdPosition);
    }

    public void followPath(Path path) {
        followPath(path, true);
    }

    public void followPath(PathChain pathChain, boolean holdPosition) {
        this.currentPathChain = pathChain;
        this.currentPathIndex = 0;
        this.isFollowing = true;
        this.holdPositionAtEnd = holdPosition;
        this.pathTimer.reset();
        
        if (pathChain.size() > 0) {
            Path firstPath = pathChain.get(0);
            Point endPt = firstPath.getEndPoint();
            this.targetPose = new Pose(endPt.getX(), endPt.getY(), firstPath.getEndHeading());
        }
    }

    public void followPath(PathChain pathChain) {
        followPath(pathChain, true);
    }

    public void breakFollowing() {
        isFollowing = false;
        currentPathChain = null;
        stopMotors();
    }

    public boolean isBusy() {
        return isFollowing;
    }

    public void setMaxPower(double maxPower) {
        this.maxPower = Range.clip(maxPower, 0.0, 1.0);
    }

    public void update() {
        updatePose();

        if (!isFollowing) {
            return;
        }

        if (currentPathChain == null || currentPathIndex >= currentPathChain.size()) {
            isFollowing = false;
            stopMotors();
            return;
        }

        Path currentPath = currentPathChain.get(currentPathIndex);
        Point targetPoint = currentPath.getEndPoint();
        double targetHeading = currentPath.getEndHeading();
        targetPose = new Pose(targetPoint.getX(), targetPoint.getY(), targetHeading);

        double xError = targetPose.getX() - currentPose.getX();
        double yError = targetPose.getY() - currentPose.getY();
        double distanceError = Math.hypot(xError, yError);
        
        double headingError = AngleUnit.normalizeRadians(targetHeading - currentPose.getHeading());

        // Check path completion
        if (distanceError < FConstants.pathEndTranslationalConstraint && 
            Math.abs(headingError) < FConstants.pathEndHeadingConstraint) {
            
            currentPathIndex++;
            pathTimer.reset();

            if (currentPathIndex >= currentPathChain.size()) {
                isFollowing = false;
                if (!holdPositionAtEnd) {
                    stopMotors();
                }
                return;
            } else {
                // Move to next path segment
                Path nextPath = currentPathChain.get(currentPathIndex);
                Point nextPoint = nextPath.getEndPoint();
                targetPose = new Pose(nextPoint.getX(), nextPoint.getY(), nextPath.getEndHeading());
            }
        }

        if (pathTimer.seconds() > FConstants.pathEndTimeout) {
            // Timeout safety check
            currentPathIndex++;
            pathTimer.reset();
            if (currentPathIndex >= currentPathChain.size()) {
                isFollowing = false;
                stopMotors();
                return;
            }
        }

        // PID calculations
        double drivePower = xError * FConstants.transP;
        double strafePower = yError * FConstants.transP;
        double turnPower = headingError * FConstants.headingP;

        // Convert field centric to robot centric
        double botHeading = currentPose.getHeading();
        double rotX = drivePower * Math.cos(-botHeading) - strafePower * Math.sin(-botHeading);
        double rotY = drivePower * Math.sin(-botHeading) + strafePower * Math.cos(-botHeading);

        driveMecanum(rotX, rotY, turnPower);
    }

    private void updatePose() {
        if (pinpoint != null) {
            pinpoint.update();
            Pose2D p = pinpoint.getPosition();
            currentPose = new Pose(
                p.getX(DistanceUnit.INCH),
                p.getY(DistanceUnit.INCH),
                p.getHeading(AngleUnit.RADIANS)
            );
        }
    }

    public void driveMecanum(double drive, double strafe, double turn) {
        double leftFrontPower = (drive + strafe + turn) * maxPower;
        double rightFrontPower = (drive - strafe - turn) * maxPower;
        double leftBackPower = (drive - strafe + turn) * maxPower;
        double rightBackPower = (drive + strafe - turn) * maxPower;

        double voltageFactor = getVoltageCompensationFactor();
        leftFrontPower *= voltageFactor;
        rightFrontPower *= voltageFactor;
        leftBackPower *= voltageFactor;
        rightBackPower *= voltageFactor;

        double max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        max = Math.max(max, Math.abs(leftBackPower));
        max = Math.max(max, Math.abs(rightBackPower));

        if (max > 1.0) {
            leftFrontPower /= max;
            rightFrontPower /= max;
            leftBackPower /= max;
            rightBackPower /= max;
        }

        leftFront.setPower(leftFrontPower);
        rightFront.setPower(rightFrontPower);
        leftBack.setPower(leftBackPower);
        rightBack.setPower(rightBackPower);
    }

    public void stopMotors() {
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
    }

    private double getVoltageCompensationFactor() {
        if (voltageSensor == null) return 1.0;
        double v = voltageSensor.getVoltage();
        if (v < 6.0) return 1.0;
        return RobotConstants.NOMINAL_VOLTAGE / v;
    }
}
