package tech.fastj.stackattack

import tech.fastj.engine.FastJEngine
import tech.fastj.logging.LogLevel

fun main() {
    FastJEngine.init("FastJ Kotlin Template", GameManager())
    FastJEngine.setTargetUPS(1)
    FastJEngine.configureLogging(LogLevel.Debug)
    FastJEngine.run()
}
