package tech.fastj.stackattack

import tech.fastj.engine.FastJEngine

fun main() {
    FastJEngine.init("FastJ Kotlin Template", GameManager())
    FastJEngine.run()
}
