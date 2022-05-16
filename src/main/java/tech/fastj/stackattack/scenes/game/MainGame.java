package tech.fastj.stackattack.scenes.game;

import tech.fastj.engine.FastJEngine;
import tech.fastj.logging.Log;
import tech.fastj.math.Pointf;
import tech.fastj.math.Transform2D;
import tech.fastj.graphics.Boundary;
import tech.fastj.graphics.display.FastJCanvas;
import tech.fastj.graphics.game.Polygon2D;
import tech.fastj.graphics.game.Sprite2D;
import tech.fastj.graphics.util.DrawUtil;

import tech.fastj.input.keyboard.KeyboardActionListener;
import tech.fastj.input.keyboard.Keys;
import tech.fastj.input.keyboard.events.KeyboardStateEvent;
import tech.fastj.systems.collections.Pair;
import tech.fastj.systems.control.Scene;

import java.util.ArrayList;
import java.util.List;

import tech.fastj.animation.AnimationStyle;
import tech.fastj.animation.sprite.SpriteAnimationData;
import tech.fastj.animation.sprite.event.AnimationFlipEvent;
import tech.fastj.stackattack.animation.IntroFlipObserver;
import tech.fastj.stackattack.scripts.StackMovement;
import tech.fastj.stackattack.ui.ContentBox;
import tech.fastj.stackattack.user.User;
import tech.fastj.stackattack.user.UserKt;
import tech.fastj.stackattack.util.FilePaths;
import tech.fastj.stackattack.util.Fonts;
import tech.fastj.stackattack.util.SceneNames;
import tech.fastj.stackattack.util.Shapes;

public class MainGame extends Scene {

    private GameState gameState;
    private User user;

    private IntroFlipObserver introFlipObserver;
    private Sprite2D introAnimation;

    private List<Polygon2D> blocks;
    private Polygon2D base;
    private ContentBox scoreBox;

    private PauseMenu pauseMenu;
    private KeyboardActionListener pauseListener;

    private ResultMenu resultMenu;

    public MainGame() {
        super(SceneNames.Game);
    }

    public Pair<Float, Float> getEdgesOfStack() {
        Polygon2D lastDormantBlock = blocks.get(blocks.size() - 2);
        return Pair.of(lastDormantBlock.getBound(Boundary.TopLeft).x, lastDormantBlock.getBound(Boundary.TopRight).x);
    }

    public GameState getGameState() {
        return gameState;
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

        if (user != null) {
            user.resetScore();
            user = null;
        }

        if (introAnimation != null) {
            introAnimation.destroy(this);
            introAnimation = null;
        }

        if (scoreBox != null) {
            scoreBox.destroy(this);
            scoreBox = null;
        }

        if (base != null) {
            base.destroy(this);
            base = null;
        }

        if (blocks != null) {
            for (Polygon2D block : blocks) {
                block.destroy(this);
            }
            blocks.clear();
            blocks = null;
        }

        if (pauseMenu != null) {
            pauseMenu.destroy(this);
            pauseMenu = null;
        }

        if (pauseListener != null) {
            inputManager.removeKeyboardActionListener(pauseListener);
            pauseListener = null;
        }

        if (resultMenu != null) {
            resultMenu.destroy(this);
            resultMenu = null;
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
                if (gameState == GameState.Results) {
                    for (Polygon2D block : blocks) {
                        block.destroy(this);
                    }
                    blocks.clear();
                    blocks = null;

                    base.destroy(this);
                    base = null;

                    scoreBox.destroy(this);
                    scoreBox = null;

                    user.resetScore();
                    user = null;

                    resultMenu.destroy(this);
                    resultMenu = null;
                }

                FastJCanvas canvas = FastJEngine.getCanvas();

                introFlipObserver = new IntroFlipObserver(this);
                FastJEngine.getGameLoop().addEventObserver(introFlipObserver, AnimationFlipEvent.class);

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
                if (gameState == GameState.Intro) {
                    user = UserKt.getInstance();

                    scoreBox = new ContentBox(this, "Score", "" + user.getScore());
                    scoreBox.setTranslation(new Pointf(30f));
                    scoreBox.getStatDisplay().setFont(Fonts.MonoStatTextFont);
                    drawableManager.addUIElement(scoreBox);

                    blocks = new ArrayList<>();

                    base = Shapes.generateGround(user.getSettings().getGameStartDifficulty().getValue());
                    drawableManager.addGameObject(base);
                    nextBlock(new Pointf(user.getSettings().getGameStartDifficulty().getValue(), 30f));

                    pauseListener = new KeyboardActionListener() {
                        @Override
                        public void onKeyReleased(KeyboardStateEvent event) {
                            if (event.isConsumed() || gameState != GameState.Playing) {
                                return;
                            }

                            if (event.getKey() == Keys.P || event.getKey() == Keys.Escape) {
                                event.consume();
                                FastJEngine.runAfterUpdate(() -> changeState(GameState.Paused));
                            }
                        }
                    };

                } else if (gameState == GameState.Paused) {
                    pauseMenu.setShouldRender(false);

                    if (introAnimation != null) {
                        introAnimation.setPaused(true);
                    }
                }

                inputManager.addKeyboardActionListener(pauseListener);
            }
            case Paused -> {
                if (pauseMenu == null) {
                    pauseMenu = new PauseMenu(this);
                    drawableManager.addUIElement(pauseMenu);
                }

                if (introAnimation != null) {
                    introAnimation.setPaused(true);
                }

                pauseMenu.setShouldRender(true);
                inputManager.removeKeyboardActionListener(pauseListener);
            }
            case Results -> resultMenu = new ResultMenu(this, user.getScore());
        }
        gameState = next;
    }

    private void nextBlock(Pointf size) {
        Log.debug(MainGame.class, "next block");

        Polygon2D block = Shapes.generateBlock(size);
        StackMovement blockMovement;
        if (blocks.size() > 1) {
            Pair<Float, Float> stackEdges = getEdgesOfStack();
            blockMovement = new StackMovement(this, stackEdges.getLeft(), stackEdges.getRight());
        } else {
            blockMovement = new StackMovement(this, base.getBound(Boundary.TopLeft).x, base.getBound(Boundary.TopRight).x);
        }
        block.addLateBehavior(blockMovement, this);
        drawableManager.addGameObject(block);
        blocks.add(block);

        tryRemoveOldestBlock();
    }

    private void tryRemoveOldestBlock() {
        Log.debug(MainGame.class, "thinking about removing things...");
        Log.debug(MainGame.class, "{} {}", blocks.get(0).getBound(Boundary.TopLeft).y, FastJEngine.getCanvas().getResolution().y);
        if (blocks.get(0).getBound(Boundary.TopLeft).y > FastJEngine.getCanvas().getResolution().y) {
            Polygon2D removed = blocks.remove(0);
            Log.debug(MainGame.class, "Removing {}", removed);
            removed.setShouldRender(false);
            FastJEngine.runAfterUpdate(() -> removed.destroy(this));
        }
    }

    public void introEnded() {
        FastJEngine.getGameLoop().removeEventObserver(introFlipObserver, AnimationFlipEvent.class);
        introFlipObserver = null;
        introAnimation.destroy(this);
        introAnimation = null;
    }

    public void calculateBlockPoints() {
        Pair<Float, Float> edgesOfStack;
        if (blocks.size() > 1) {
            edgesOfStack = getEdgesOfStack();
        } else {
            edgesOfStack = Pair.of(base.getTranslation().x, base.getTranslation().x + base.width());
        }

        Polygon2D lastBlock = blocks.get(blocks.size() - 1);
        Pair<Float, Float> lastBlockPositions = Pair.of(
                lastBlock.getTranslation().x,
                lastBlock.getTranslation().x + lastBlock.width()
        );

        if (lastBlockPositions.getRight() < edgesOfStack.getLeft() || lastBlockPositions.getLeft() > edgesOfStack.getRight()) {
            Log.debug(MainGame.class, "You lose");
            FastJEngine.runAfterUpdate(() -> changeState(GameState.Results));
        } else {
            if (lastBlockPositions.getRight() < edgesOfStack.getRight()) {
                Pointf newPosition = new Pointf(edgesOfStack.getLeft(), lastBlock.getBound(Boundary.TopLeft).y);
                Pointf newSize = new Pointf(lastBlock.getBound(Boundary.TopRight).x - edgesOfStack.getLeft(), lastBlock.height());
                Pointf[] newMesh = DrawUtil.createBox(newPosition, newSize);
                lastBlock.modifyPoints(newMesh, true, false, false);
                Shapes.removePersonality(lastBlock);

                shiftBlocksDown();
                nextBlock(newSize);
            } else if (lastBlockPositions.getLeft() > edgesOfStack.getLeft()) {
                Pointf newPosition = lastBlock.getBound(Boundary.TopLeft);
                Pointf newSize = new Pointf(edgesOfStack.getRight() - lastBlock.getBound(Boundary.TopLeft).x, lastBlock.height());
                Pointf[] newMesh = DrawUtil.createBox(newPosition, newSize);
                lastBlock.modifyPoints(newMesh, true, false, false);
                Shapes.removePersonality(lastBlock);

                shiftBlocksDown();
                nextBlock(newSize);
            } else {
                Log.info(MainGame.class, "Perfect?");
                Shapes.removePersonality(lastBlock);

                shiftBlocksDown();
                nextBlock(new Pointf(lastBlock.width(), lastBlock.height()));
            }

            user.addToScore(Math.round(100f * lastBlock.width() / (lastBlockPositions.getRight() - lastBlockPositions.getLeft())));
            scoreBox.setContent("" + user.getScore());
        }
    }

    private void shiftBlocksDown() {
        Pointf translation = new Pointf(0f, 34f);
        base.translate(translation);
        for (Polygon2D block : blocks) {
            block.translate(translation);
        }
    }
}
