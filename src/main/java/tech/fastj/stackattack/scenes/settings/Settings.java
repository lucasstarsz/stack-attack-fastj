package tech.fastj.stackattack.scenes.settings;

import tech.fastj.engine.FastJEngine;
import tech.fastj.logging.Log;
import tech.fastj.math.Pointf;
import tech.fastj.math.Transform2D;
import tech.fastj.graphics.display.FastJCanvas;
import tech.fastj.graphics.game.Text2D;
import tech.fastj.graphics.ui.elements.Button;

import tech.fastj.systems.control.Scene;
import tech.fastj.systems.control.SceneManager;

import java.awt.Color;

import tech.fastj.stackattack.scenes.game.GameStartDifficulty;
import tech.fastj.stackattack.scenes.game.MainGame;
import tech.fastj.stackattack.ui.ArrowButton;
import tech.fastj.stackattack.user.User;
import tech.fastj.stackattack.util.Colors;
import tech.fastj.stackattack.util.Fonts;
import tech.fastj.stackattack.util.SceneNames;
import tech.fastj.stackattack.util.Shapes;

public class Settings extends Scene {

    private final User user = User.getInstance();

    private Text2D difficultyText;
    private ArrowButton gameDifficulties;

    private Text2D titleText;
    private Button mainMenuButton;

    public Settings() {
        super(SceneNames.Settings);
    }

    @Override
    public void load(FastJCanvas canvas) {
        Log.debug(MainGame.class, "loading {}", getSceneName());
        Pointf center = canvas.getCanvasCenter();

        titleText = Text2D.create("Settings")
                .withFont(Fonts.TitleTextFont)
                .withTransform(Pointf.subtract(center, 100f, 200f), Transform2D.DefaultRotation, Transform2D.DefaultScale)
                .build();
        drawableManager.addGameObject(titleText);

        difficultyText = Text2D.create("Game Difficulty")
                .withFont(Fonts.StatTextFont)
                .withTransform(Pointf.subtract(center, 65f, 30f), Transform2D.DefaultRotation, Transform2D.DefaultScale)
                .build();
        drawableManager.addGameObject(difficultyText);

        gameDifficulties = new ArrowButton(this, Pointf.subtract(center, 200f, 0f), Shapes.ButtonSize.copy().multiply(2f, 1.25f), GameStartDifficulty.DifficultiesList, 1);
        gameDifficulties.setFill(Color.white);
        gameDifficulties.setFont(Fonts.ButtonTextFont);
        gameDifficulties.getArrowLeft().setFill(Colors.LightSnowy);
        gameDifficulties.getArrowRight().setFill(Colors.LightSnowy);
        gameDifficulties.setOnAction(mouseButtonEvent -> {
            mouseButtonEvent.consume();
            user.getSettings().setGameStartDifficulty(GameStartDifficulty.values()[gameDifficulties.getSelectedOption()]);
        });

        mainMenuButton = new Button(this, canvas.getCanvasCenter().add(-100f, 150f), Shapes.ButtonSize);
        mainMenuButton.setText("Back");
        mainMenuButton.setFill(Color.white);
        mainMenuButton.setFont(Fonts.ButtonTextFont);
        mainMenuButton.setOnAction(mouseButtonEvent -> {
            mouseButtonEvent.consume();
            FastJEngine.runAfterRender(() -> FastJEngine.<SceneManager>getLogicManager().switchScenes(SceneNames.MainMenu));
        });
        Log.debug(MainGame.class, "loaded {}", getSceneName());
    }

    @Override
    public void unload(FastJCanvas canvas) {
        Log.debug(MainGame.class, "unloading {}", getSceneName());
        if (titleText != null) {
            titleText.destroy(this);
            titleText = null;
        }

        if (difficultyText != null) {
            difficultyText.destroy(this);
            difficultyText = null;
        }

        if (gameDifficulties != null) {
            gameDifficulties.destroy(this);
            gameDifficulties = null;
        }

        if (mainMenuButton != null) {
            mainMenuButton.destroy(this);
            mainMenuButton = null;
        }
        setInitialized(false);
        Log.debug(MainGame.class, "unloaded {}", getSceneName());
    }

    @Override
    public void fixedUpdate(FastJCanvas canvas) {
    }

    @Override
    public void update(FastJCanvas canvas) {
    }
}
