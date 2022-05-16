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
import tech.fastj.stackattack.user.UserKt;
import tech.fastj.stackattack.util.Fonts;
import tech.fastj.stackattack.util.SceneNames;
import tech.fastj.stackattack.util.Shapes;

public class Settings extends Scene {

    private final User user = UserKt.getInstance();
    private ArrowButton gameDifficulties;

    private Text2D titleText;
    private Button playButton;
    private Button settingsButton;
    private Button exitButton;

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

        gameDifficulties = new ArrowButton(this, Pointf.subtract(center, 200f, 0f), Shapes.ButtonSize.copy().multiply(2f, 1.25f), GameStartDifficulty.DifficultiesList, 1);
        gameDifficulties.setFill(Color.white);
        gameDifficulties.setFont(Fonts.ButtonTextFont);
        gameDifficulties.setOnAction(mouseButtonEvent -> {
            mouseButtonEvent.consume();
            user.getSettings().setGameStartDifficulty(GameStartDifficulty.values()[gameDifficulties.getSelectedOption()]);
        });

//        playButton = new Button(this, Pointf.subtract(center, 100f, 50f), Shapes.ButtonSize);
//        playButton.setText("Play Game");
//        playButton.setFill(Color.white);
//        playButton.setFont(Fonts.ButtonTextFont);
//        playButton.setOnAction(mouseButtonEvent -> {
//            mouseButtonEvent.consume();
//            FastJEngine.runAfterRender(() -> FastJEngine.<SceneManager>getLogicManager().switchScenes(SceneNames.Game));
//        });
//
//        settingsButton = new Button(this, canvas.getCanvasCenter().add(-100f, 50f), Shapes.ButtonSize);
//        settingsButton.setText("Settings");
//        settingsButton.setFill(Color.white);
//        settingsButton.setFont(Fonts.ButtonTextFont);
//        settingsButton.setOnAction(mouseButtonEvent -> {
//            mouseButtonEvent.consume();
//            FastJEngine.runAfterRender(() -> FastJEngine.<SceneManager>getLogicManager().switchScenes(SceneNames.Settings));
//        });

        exitButton = new Button(this, canvas.getCanvasCenter().add(-100f, 150f), Shapes.ButtonSize);
        exitButton.setText("Main Menu");
        exitButton.setFill(Color.white);
        exitButton.setFont(Fonts.ButtonTextFont);
        exitButton.setOnAction(mouseButtonEvent -> {
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

        if (gameDifficulties != null) {
            gameDifficulties.destroy(this);
            gameDifficulties = null;
        }

        if (playButton != null) {
            playButton.destroy(this);
            playButton = null;
        }

        if (settingsButton != null) {
            settingsButton.destroy(this);
            settingsButton = null;
        }

        if (exitButton != null) {
            exitButton.destroy(this);
            exitButton = null;
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
