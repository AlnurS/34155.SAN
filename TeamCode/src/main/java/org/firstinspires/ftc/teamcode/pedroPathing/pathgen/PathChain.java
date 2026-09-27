package org.firstinspires.ftc.teamcode.pedroPathing.pathgen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a sequence/chain of paths.
 */
public class PathChain {
    private List<Path> paths;

    public PathChain(Path... paths) {
        this.paths = new ArrayList<>(Arrays.asList(paths));
    }

    public PathChain(List<Path> paths) {
        this.paths = new ArrayList<>(paths);
    }

    public List<Path> getPaths() {
        return paths;
    }

    public int size() {
        return paths.size();
    }

    public Path get(int index) {
        return paths.get(index);
    }
}
