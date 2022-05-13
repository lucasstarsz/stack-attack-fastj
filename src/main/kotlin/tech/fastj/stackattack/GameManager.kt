package tech.fastj.stackattack

import tech.fastj.graphics.display.FastJCanvas
import tech.fastj.graphics.display.RenderSettings
import tech.fastj.stackattack.scenes.Game
import tech.fastj.systems.control.SceneManager

open class GameManager : SceneManager() {

    private val game = Game()

    override fun init(canvas: FastJCanvas) {
        canvas.modifyRenderSettings(RenderSettings.Antialiasing.Enable)
        addScene(game)
        setCurrentScene(game)
    }
}

