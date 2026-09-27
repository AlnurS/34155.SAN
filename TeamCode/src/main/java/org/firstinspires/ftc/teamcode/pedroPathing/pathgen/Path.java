package org.firstinspires.ftc.teamcode.pedroPathing.pathgen;

import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;

/**
 * Represents a single path segment wrapping a Bezier line or curve with heading constraints.
 */
public class Path {
    public enum HeadingInterpolation {
        LINEAR,
        CONSTANT,
        TANGENT
    }

    private BezierLine line;
    private BezierCurve curve;
    private HeadingInterpolation interpolationMode = HeadingInterpolation.LINEAR;
    
    private double startHeading = 0.0;
    private double endHeading = 0.0;

    public Path(BezierLine line) {
        this.line = line;
    }

    public Path(BezierCurve curve) {
        this.curve = curve;
    }

    public Path(Pose startPose, Pose endPose) {
        this.line = new BezierLine(startPose, endPose);
        this.startHeading = startPose.getHeading();
        this.endHeading = endPose.getHeading();
        this.interpolationMode = HeadingInterpolation.LINEAR;
    }

    public Path setLinearHeadingInterpolation(double startHeading, double endHeading) {
        this.interpolationMode = HeadingInterpolation.LINEAR;
        this.startHeading = startHeading;
        this.endHeading = endHeading;
        return this;
    }

    public Path setConstantHeadingInterpolation(double heading) {
        this.interpolationMode = HeadingInterpolation.CONSTANT;
        this.startHeading = heading;
        this.endHeading = heading;
        return this;
    }

    public Path constant(Pose targetPose) {
        return setConstantHeadingInterpolation(targetPose.getHeading());
    }

    public Path constant(double headingRadians) {
        return setConstantHeadingInterpolation(headingRadians);
    }

    public Path linear(double startHeading, double endHeading) {
        return setLinearHeadingInterpolation(startHeading, endHeading);
    }

    public Path setTangentHeadingInterpolation() {
        this.interpolationMode = HeadingInterpolation.TANGENT;
        return this;
    }

    public Point getStartPoint() {
        return line != null ? line.getStartPoint() : curve.getStartPoint();
    }

    public Point getEndPoint() {
        return line != null ? line.getEndPoint() : curve.getEndPoint();
    }

    public double getStartHeading() {
        return startHeading;
    }

    public double getEndHeading() {
        return endHeading;
    }

    public HeadingInterpolation getInterpolationMode() {
        return interpolationMode;
    }
}
