package org.firstinspires.ftc.teamcode.pedroPathing.pathgen;

import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for constructing PathChains fluently.
 */
public class PathBuilder {
    private List<Path> pathList = new ArrayList<>();

    public PathBuilder addPath(Path path) {
        pathList.add(path);
        return this;
    }

    public PathBuilder addPath(BezierLine line) {
        pathList.add(new Path(line));
        return this;
    }

    public PathBuilder addPath(BezierCurve curve) {
        pathList.add(new Path(curve));
        return this;
    }

    public PathBuilder addPath(Pose startPose, Pose endPose) {
        pathList.add(new Path(startPose, endPose));
        return this;
    }

    public PathChain build() {
        return new PathChain(pathList);
    }
}
