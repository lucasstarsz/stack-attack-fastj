package tech.fastj.stackattack;

import tech.fastj.graphics.display.FastJCanvas;
import tech.fastj.graphics.display.RenderSettings;

import tech.fastj.systems.control.SceneManager;

import tech.fastj.stackattack.scenes.game.MainGame;
import tech.fastj.stackattack.scenes.information.InformationMenu;
import tech.fastj.stackattack.scenes.mainmenu.MainMenu;
import tech.fastj.stackattack.scenes.settings.Settings;
import tech.fastj.stackattack.util.Colors;

public class GameManager extends SceneManager {

    private final MainMenu mainMenu = new MainMenu();
    private final InformationMenu infoMenu = new InformationMenu();
    private final Settings settings = new Settings();
    private final MainGame mainGame = new MainGame();

    @Override
    public void init(FastJCanvas canvas) {
        canvas.modifyRenderSettings(RenderSettings.Antialiasing.Enable);
        canvas.setBackgroundColor(Colors.LightSnowy);
        addScene(mainMenu);
        addScene(infoMenu);
        addScene(settings);
        addScene(mainGame);
        setCurrentScene(mainMenu);
        loadCurrentScene();
    }
}
