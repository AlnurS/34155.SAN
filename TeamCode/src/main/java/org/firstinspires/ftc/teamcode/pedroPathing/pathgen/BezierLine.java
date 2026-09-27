package org.firstinspires.ftc.teamcode.pedroPathing.pathgen;

import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;

/**
 * Represents a straight line path segment defined by two points.
 */
public class BezierLine {
    private Point startPoint;
    private Point endPoint;

    public BezierLine(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public BezierLine(Pose startPose, Pose endPose) {
        this(startPose.getPoint(), endPose.getPoint());
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }
}
