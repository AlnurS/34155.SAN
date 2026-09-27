package org.firstinspires.ftc.teamcode.pedroPathing.pathgen;

import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;

/**
 * Represents a 2D coordinate point (X, Y) in inches.
 */
public class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Pose pose) {
        this(pose.getX(), pose.getY());
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double distanceFrom(Point other) {
        return Math.hypot(other.x - this.x, other.y - this.y);
    }
}
