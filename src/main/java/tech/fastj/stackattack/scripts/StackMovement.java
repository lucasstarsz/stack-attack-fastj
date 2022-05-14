package tech.fastj.stackattack.scripts;

import tech.fastj.engine.FastJEngine;
import tech.fastj.logging.Log;
import tech.fastj.math.Maths;
import tech.fastj.math.Pointf;
import tech.fastj.graphics.display.FastJCanvas;
import tech.fastj.graphics.game.GameObject;

import tech.fastj.systems.behaviors.Behavior;

import tech.fastj.gameloop.Timer;
import tech.fastj.stackattack.scenes.game.MainGame;

public class StackMovement implements Behavior {

    private static final int MovementSpeed = 100;

    private final MainGame mainGame;
    private final int moveDirection;

    private boolean isMoving;
    private final Timer testTimer;

    public StackMovement(MainGame mainGame) {
        this.mainGame = mainGame;
        this.moveDirection = (int) Maths.randomAtEdge(-1, 1);
        testTimer = new Timer();
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
        testTimer.evalDeltaTime();

        if (moveDirection == -1) {
            gameObject.setTranslation(new Pointf(canvas.getCanvasCenter().x + canvas.getResolution().x, 200f));
        } else {
            gameObject.setTranslation(new Pointf(-canvas.getCanvasCenter().x, 200f));
        }
    }

    @Override
    public void fixedUpdate(GameObject gameObject) {
    }

    @Override
    public void update(GameObject gameObject) {
        if (isMoving) {
            Log.debug(StackMovement.class, "Move {} {} * {} * {}px", gameObject.getID(), moveDirection, MovementSpeed, FastJEngine.getDeltaTime());
            Log.debug(StackMovement.class, "compare to {}", testTimer.evalDeltaTime());
            Pointf movement = new Pointf(moveDirection * MovementSpeed * FastJEngine.getDeltaTime(), 0f);
            gameObject.translate(movement);
        }
    }
}
