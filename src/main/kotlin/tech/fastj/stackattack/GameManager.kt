package tech.fastj.stackattack

import tech.fastj.graphics.display.FastJCanvas
import tech.fastj.graphics.display.RenderSettings
import tech.fastj.stackattack.scenes.game.MainGame
import tech.fastj.stackattack.scenes.information.InformationMenu
import tech.fastj.stackattack.scenes.mainmenu.MainMenu
import tech.fastj.stackattack.scenes.settings.Settings
import tech.fastj.systems.control.SceneManager

open class GameManager : SceneManager() {

    private val mainMenu = MainMenu()
    private val infoMenu = InformationMenu()
    private val settings = Settings()
    private val mainGame = MainGame()

    override fun init(canvas: FastJCanvas) {
        canvas.modifyRenderSettings(RenderSettings.Antialiasing.Enable)
        addScene(mainMenu)
        addScene(infoMenu)
        addScene(settings)
        addScene(mainGame)
        setCurrentScene(mainMenu)
        loadCurrentScene()
    }
}
