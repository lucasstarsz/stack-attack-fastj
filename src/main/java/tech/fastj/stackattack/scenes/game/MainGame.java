package tech.fastj.stackattack.scenes.game;

import tech.fastj.engine.FastJEngine;
import tech.fastj.logging.Log;
import tech.fastj.math.Transform2D;
import tech.fastj.graphics.display.FastJCanvas;
import tech.fastj.graphics.game.Polygon2D;
import tech.fastj.graphics.game.Sprite2D;

import tech.fastj.systems.collections.Pair;
import tech.fastj.systems.control.Scene;

import java.util.ArrayList;
import java.util.List;

import tech.fastj.animation.AnimationStyle;
import tech.fastj.animation.sprite.SpriteAnimationData;
import tech.fastj.stackattack.animation.IntroFlipObserver;
import tech.fastj.stackattack.scripts.StackMovement;
import tech.fastj.stackattack.util.FilePaths;
import tech.fastj.stackattack.util.SceneNames;
import tech.fastj.stackattack.util.Shapes;

public class MainGame extends Scene {

    private GameState gameState;

    private IntroFlipObserver introFlipObserver;
    private Sprite2D introAnimation;

    private List<Polygon2D> blocks;

    public MainGame() {
        super(SceneNames.Game);
    }

    public Pair<Float, Float> getEdgesOfStack() {
        Polygon2D lastDormantBlock = blocks.get(blocks.size() - 2);
        return Pair.of(lastDormantBlock.getTranslation().x, lastDormantBlock.width() + lastDormantBlock.getTranslation().x);
    }

    @Override
    public void load(FastJCanvas canvas) {
        Log.debug(MainGame.class, "loading {}", getSceneName());
        changeState(GameState.Intro);
        Log.debug(MainGame.class, "loaded {}", getSceneName());
    }

    @Override
    public void unload(FastJCanvas canvas) {
        Log.debug(MainGame.class, "unloading {}", getSceneName());
        gameState = null;
        introFlipObserver = null;
        if (introAnimation != null) {
            introAnimation.destroy(this);
            introAnimation = null;
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

    public void changeState(GameState next) {
        Log.debug(MainGame.class, "changing state from {} to {}", gameState, next);
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
                blocks = new ArrayList<>();
                nextBlock();
            }
            case Paused -> {
            }
            case Results -> {
            }
        }
        gameState = next;
    }

    private void nextBlock() {
        Log.debug(MainGame.class, "next block");

        Polygon2D block = Shapes.generateBlock();
        StackMovement blockMovement = new StackMovement(this);
        block.addLateBehavior(blockMovement, this);
        drawableManager.addGameObject(block);
        blocks.add(block);

        tryRemoveOldestBlock();
    }

    private void tryRemoveOldestBlock() {
        if (blocks.get(0).getTranslation().y > FastJEngine.getCanvas().getResolution().y) {
            Polygon2D removed = blocks.remove(0);
            removed.setShouldRender(false);
            FastJEngine.runAfterRender(() -> removed.destroy(this));
        }
    }

    public void introEnded() {
        introFlipObserver = null;
        introAnimation.destroy(this);
        introAnimation = null;
    }
}
