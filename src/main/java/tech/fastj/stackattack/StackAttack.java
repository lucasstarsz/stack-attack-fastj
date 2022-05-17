package tech.fastj.stackattack;

import tech.fastj.engine.FastJEngine;
import tech.fastj.logging.LogLevel;
import tech.fastj.math.Point;

public class StackAttack {
    public static void main(String[] args) {
        FastJEngine.init("Stack Attack", new GameManager());
        FastJEngine.configureCanvasResolution(new Point(800, 600));
        FastJEngine.configureWindowResolution(new Point(800, 600));
        FastJEngine.setTargetUPS(1);
        FastJEngine.configureLogging(LogLevel.Debug);
        try {
            FastJEngine.run();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        System.exit(0);
    }
}
