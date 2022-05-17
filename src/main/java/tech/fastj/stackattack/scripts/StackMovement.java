package tech.fastj.stackattack.scripts;

import tech.fastj.engine.FastJEngine;
import tech.fastj.math.Maths;
import tech.fastj.math.Pointf;
import tech.fastj.graphics.Boundary;
import tech.fastj.graphics.display.FastJCanvas;
import tech.fastj.graphics.game.GameObject;

import tech.fastj.input.mouse.MouseActionListener;
import tech.fastj.input.mouse.events.MouseButtonEvent;
import tech.fastj.systems.behaviors.Behavior;

import java.awt.event.MouseEvent;

import tech.fastj.stackattack.scenes.game.GameState;
import tech.fastj.stackattack.scenes.game.MainGame;
import tech.fastj.stackattack.user.User;
import tech.fastj.stackattack.user.UserKt;

public class StackMovement implements Behavior, MouseActionListener {

    private static final int MovementSpeed = 4;

    private final MainGame mainGame;
    private final int moveDirection;
    private final float leftEdge;
    private final float rightEdge;
    private final User user;

    private boolean isMoving;

    public StackMovement(MainGame mainGame, float leftEdge, float rightEdge) {
        this.mainGame = mainGame;
        this.leftEdge = leftEdge;
        this.rightEdge = rightEdge;
        this.moveDirection = (int) Maths.randomAtEdge(-1, 1);
        this.user = UserKt.getInstance();
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    @Override
    public void init(GameObject gameObject) {
        isMoving = true;
        FastJCanvas canvas = FastJEngine.getCanvas();

        if (moveDirection == -1) {
            gameObject.setTranslation(new Pointf(canvas.getCanvasCenter().x + canvas.getResolution().x, gameObject.getTranslation().y));
        } else {
            gameObject.setTranslation(new Pointf(-canvas.getCanvasCenter().x, gameObject.getTranslation().y));
        }

        mainGame.inputManager.addMouseActionListener(this);
    }

    @Override
    public void fixedUpdate(GameObject gameObject) {
    }

    @Override
    public void update(GameObject gameObject) {
        if (isMoving && mainGame.getGameState() != GameState.Paused) {
            Pointf movement = new Pointf(moveDirection * MovementSpeed * (float) Math.ceil((user.getNumberStacked() + 1) / 10f) * FastJEngine.getDeltaTime() * 100, 0f);
            gameObject.translate(movement);

            if (moveDirection == -1 && gameObject.getBound(Boundary.TopRight).x < leftEdge
                    || moveDirection == 1 && gameObject.getBound(Boundary.TopLeft).x > rightEdge) {
                isMoving = false;
                FastJEngine.runAfterRender(() -> {
                    mainGame.inputManager.removeMouseActionListener(this);
                    mainGame.calculateBlockPoints();
                });
            }
        }
    }

    @Override
    public void onMousePressed(MouseButtonEvent mouseButtonEvent) {
        if (mouseButtonEvent.getMouseButton() != MouseEvent.BUTTON1) {
            return;
        }

        if (mouseButtonEvent.isConsumed() || mainGame.getGameState() == GameState.Paused) {
            return;
        }

        if (isMoving) {
            isMoving = false;
            FastJEngine.runAfterRender(() -> {
                mainGame.inputManager.removeMouseActionListener(this);
                mainGame.calculateBlockPoints();
            });
        }
    }
}
