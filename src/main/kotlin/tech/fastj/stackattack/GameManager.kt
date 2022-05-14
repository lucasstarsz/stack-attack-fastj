package tech.fastj.stackattack

import tech.fastj.graphics.display.FastJCanvas
import tech.fastj.graphics.display.RenderSettings
import tech.fastj.stackattack.scenes.game.MainGame
import tech.fastj.systems.control.SceneManager

open class GameManager : SceneManager() {

    private val mainGame = MainGame()

    override fun init(canvas: FastJCanvas) {
        canvas.modifyRenderSettings(RenderSettings.Antialiasing.Enable)
        addScene(mainGame)
        setCurrentScene(mainGame)
        loadCurrentScene()
    }
}
