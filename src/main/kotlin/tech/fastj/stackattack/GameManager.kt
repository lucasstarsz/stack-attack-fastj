package tech.fastj.stackattack

import tech.fastj.graphics.display.FastJCanvas
import tech.fastj.graphics.display.RenderSettings
import tech.fastj.stackattack.scenes.game.MainGame
import tech.fastj.stackattack.scenes.mainmenu.MainMenu
import tech.fastj.systems.control.SceneManager

open class GameManager : SceneManager() {

    private val mainGame = MainGame()
    private val mainMenu = MainMenu()

    override fun init(canvas: FastJCanvas) {
        canvas.modifyRenderSettings(RenderSettings.Antialiasing.Enable)
        addScene(mainGame)
        addScene(mainMenu)
        setCurrentScene(mainMenu)
        loadCurrentScene()
    }
}
