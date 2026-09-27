package org.firstinspires.ftc.teamcode.pedroPathing.constants;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.teamcode.config.RobotConstants;

/**
 * Follower PID and physical robot constants for Pedro Pathing.
 */
public class FConstants {
    public static String leftFrontMotorName = RobotConstants.LEFT_FRONT;
    public static String rightFrontMotorName = RobotConstants.RIGHT_FRONT;
    public static String leftRearMotorName = RobotConstants.LEFT_BACK;
    public static String rightRearMotorName = RobotConstants.RIGHT_BACK;

    public static DcMotorSimple.Direction leftFrontMotorDirection = DcMotorSimple.Direction.REVERSE;
    public static DcMotorSimple.Direction leftRearMotorDirection = DcMotorSimple.Direction.REVERSE;
    public static DcMotorSimple.Direction rightFrontMotorDirection = DcMotorSimple.Direction.FORWARD;
    public static DcMotorSimple.Direction rightRearMotorDirection = DcMotorSimple.Direction.FORWARD;

    // Translational PID coefficients (X, Y motion)
    public static double transP = 0.12;
    public static double transI = 0.0;
    public static double transD = 0.01;

    // Heading PID coefficients (Rotation)
    public static double headingP = 0.9;
    public static double headingI = 0.0;
    public static double headingD = 0.05;

    // Path End Constraints
    public static double pathEndTimeout = 3.0; // Seconds
    public static double pathEndTranslationalConstraint = 0.8; // Inches
    public static double pathEndHeadingConstraint = Math.toRadians(2.0); // Radians
}
