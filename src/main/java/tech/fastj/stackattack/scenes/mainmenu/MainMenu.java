package tech.fastj.stackattack.scenes.mainmenu;

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

import tech.fastj.stackattack.util.Fonts;
import tech.fastj.stackattack.util.SceneNames;
import tech.fastj.stackattack.util.Shapes;

public class MainMenu extends Scene {

    private Text2D titleText;
    private Button playButton;
    private Button settingsButton;
    private Button exitButton;

    public MainMenu() {
        super(SceneNames.MainMenu);
    }

    @Override
    public void load(FastJCanvas canvas) {
        Pointf center = canvas.getCanvasCenter();

        titleText = Text2D.create("Stack Attack")
                .withFont(Fonts.TitleTextFont)
                .withTransform(Pointf.subtract(center, 150f, 200f), Transform2D.DefaultRotation, Transform2D.DefaultScale)
                .build();
        drawableManager.addGameObject(titleText);

        playButton = new Button(this, Pointf.subtract(center, 100f, 50f), Shapes.ButtonSize);
        playButton.setText("Play Game");
        playButton.setFill(Color.white);
        playButton.setFont(Fonts.ButtonTextFont);
        playButton.setOnAction(mouseButtonEvent -> {
            mouseButtonEvent.consume();
            FastJEngine.runAfterRender(() -> FastJEngine.<SceneManager>getLogicManager().switchScenes(SceneNames.Game));
        });

        settingsButton = new Button(this, canvas.getCanvasCenter().add(-100f, 50f), Shapes.ButtonSize);
        settingsButton.setText("Settings");
        settingsButton.setFill(Color.white);
        settingsButton.setFont(Fonts.ButtonTextFont);
        settingsButton.setOnAction(mouseButtonEvent -> {
            mouseButtonEvent.consume();
            Log.info(MainMenu.class, "TODO: open Settings");
        });

        exitButton = new Button(this, canvas.getCanvasCenter().add(-100f, 150f), Shapes.ButtonSize);
        exitButton.setText("Exit Game");
        exitButton.setFill(Color.white);
        exitButton.setFont(Fonts.ButtonTextFont);
        exitButton.setOnAction(mouseButtonEvent -> {
            mouseButtonEvent.consume();
            FastJEngine.runAfterRender(FastJEngine.getDisplay()::close);
        });
    }

    @Override
    public void unload(FastJCanvas canvas) {

        if (titleText != null) {
            titleText.destroy(this);
            titleText = null;
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
    }

    @Override
    public void fixedUpdate(FastJCanvas canvas) {
    }

    @Override
    public void update(FastJCanvas canvas) {
    }
}
