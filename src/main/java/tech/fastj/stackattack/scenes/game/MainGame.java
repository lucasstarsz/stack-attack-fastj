package tech.fastj.stackattack.scenes.game;

import tech.fastj.engine.FastJEngine;
import tech.fastj.math.Transform2D;
import tech.fastj.graphics.display.FastJCanvas;
import tech.fastj.graphics.game.Sprite2D;

import tech.fastj.systems.control.Scene;

import tech.fastj.animation.AnimationStyle;
import tech.fastj.animation.sprite.SpriteAnimationData;
import tech.fastj.stackattack.animation.IntroFlipObserver;
import tech.fastj.stackattack.util.FilePaths;
import tech.fastj.stackattack.util.SceneNames;

public class MainGame extends Scene {

    private GameState gameState;
    private IntroFlipObserver introFlipObserver;
    private Sprite2D introAnimation;

    public MainGame() {
        super(SceneNames.Game);
    }

    @Override
    public void load(FastJCanvas canvas) {
        FastJEngine.log("load");
        changeState(GameState.Intro);
    }

    @Override
    public void unload(FastJCanvas canvas) {
        gameState = null;
        introFlipObserver = null;
        if (introAnimation != null) {
            introAnimation.destroy(this);
            introAnimation = null;
        }
        setInitialized(false);
    }

    @Override
    public void fixedUpdate(FastJCanvas canvas) {
    }

    @Override
    public void update(FastJCanvas canvas) {
    }

    public void changeState(GameState next) {
        switch (next) {
            case Intro -> {
                FastJCanvas canvas = FastJEngine.getCanvas();
                introFlipObserver = new IntroFlipObserver(this);
                introAnimation = Sprite2D.create(FilePaths.IntroAnimation)
                        .withTransform(canvas.getCanvasCenter().subtract(100f), Transform2D.DefaultRotation, Transform2D.DefaultScale)
                        .withImageCount(4, 4)
                        .withAnimationFPS(4)
                        .withStartingFrame(1)
                        .withAnimationData(new SpriteAnimationData(
                                "Countdown",
                                AnimationStyle.PlayUntilEnd,
                                0,
                                15
                        ))
                        .withStartingAnimation("Countdown")
                        .build();
                drawableManager.addGameObject(introAnimation);
            }
            case Playing -> {
            }
            case Paused -> {
            }
            case Results -> {
            }
        }
        gameState = next;
    }

    public void introEnded() {
        introFlipObserver = null;
        introAnimation.destroy(this);
        introAnimation = null;
    }
}
