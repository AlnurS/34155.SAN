package org.firstinspires.ftc.teamcode.pedroPathing.localization;

import org.firstinspires.ftc.teamcode.pedroPathing.pathgen.Point;

/**
 * Represents a 2D pose (X, Y in inches, Heading in radians).
 */
public class Pose {
    private double x;
    private double y;
    private double heading;

    public Pose(double x, double y, double heading) {
        this.x = x;
        this.y = y;
        this.heading = heading;
    }

    public Pose(double x, double y) {
        this(x, y, 0.0);
    }

    public Pose() {
        this(0.0, 0.0, 0.0);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getHeading() {
        return heading;
    }

    public void setHeading(double heading) {
        this.heading = heading;
    }

    public Point getPoint() {
        return new Point(x, y);
    }

    public String toString() {
        return String.format("Pose(X: %.2f in, Y: %.2f in, Heading: %.2f deg)", x, y, Math.toDegrees(heading));
    }
}
