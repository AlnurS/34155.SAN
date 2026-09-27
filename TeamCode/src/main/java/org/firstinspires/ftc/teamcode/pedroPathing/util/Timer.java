package org.firstinspires.ftc.teamcode.pedroPathing.util;

import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Utility timer wrapper for state machine delays and path timing.
 */
public class Timer {
    private final ElapsedTime timer = new ElapsedTime();

    public void resetTimer() {
        timer.reset();
    }

    public double getElapsedTimeSeconds() {
        return timer.seconds();
    }

    public double getElapsedTime() {
        return timer.milliseconds();
    }
}
