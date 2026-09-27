package org.firstinspires.ftc.teamcode.pedroPathing.pathgen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a Bezier curve path segment defined by control points.
 */
public class BezierCurve {
    private List<Point> controlPoints;

    public BezierCurve(Point... points) {
        this.controlPoints = new ArrayList<>(Arrays.asList(points));
    }

    public List<Point> getControlPoints() {
        return controlPoints;
    }

    public Point getStartPoint() {
        return controlPoints.get(0);
    }

    public Point getEndPoint() {
        return controlPoints.get(controlPoints.size() - 1);
    }
}
