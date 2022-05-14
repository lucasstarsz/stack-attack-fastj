package tech.fastj.stackattack

import tech.fastj.engine.FastJEngine
import tech.fastj.logging.LogLevel
import tech.fastj.math.Point

fun main() {
    FastJEngine.init("Stack Attack WIP", GameManager())
    FastJEngine.configureCanvasResolution(Point(800, 600))
    FastJEngine.configureWindowResolution(Point(800, 600))
    FastJEngine.setTargetUPS(1)
    FastJEngine.configureLogging(LogLevel.Debug)
    try {
        FastJEngine.run()
    } catch (exception: Exception) {
        exception.printStackTrace()
    }
}
