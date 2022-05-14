package tech.fastj.stackattack.scripts;

import tech.fastj.engine.FastJEngine;
import tech.fastj.math.Maths;
import tech.fastj.math.Pointf;
import tech.fastj.graphics.display.FastJCanvas;
import tech.fastj.graphics.game.GameObject;
import tech.fastj.graphics.game.Polygon2D;

import tech.fastj.input.mouse.MouseActionListener;
import tech.fastj.input.mouse.events.MouseButtonEvent;
import tech.fastj.systems.behaviors.Behavior;

import tech.fastj.gameloop.Timer;
import tech.fastj.stackattack.scenes.game.MainGame;

public class StackMovement implements Behavior, MouseActionListener {

    private static final int MovementSpeed = 5;

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
            gameObject.setTranslation(new Pointf(canvas.getCanvasCenter().x + canvas.getResolution().x, gameObject.getTranslation().y));
        } else {
            gameObject.setTranslation(new Pointf(-canvas.getCanvasCenter().x, gameObject.getTranslation().y));
        }

        mainGame.inputManager.addMouseActionListener(this);
        System.out.println("next block with color " + ((Polygon2D) gameObject).getFill());
    }

    @Override
    public void fixedUpdate(GameObject gameObject) {
    }

    @Override
    public void update(GameObject gameObject) {
        if (isMoving) {
//            Log.debug(StackMovement.class, "Move {} as {} * {} * {}px", gameObject.getID(), moveDirection, MovementSpeed, FastJEngine.getDeltaTime());
//            Log.debug(StackMovement.class, "compare to {}", testTimer.evalDeltaTime());
            Pointf movement = new Pointf(moveDirection * MovementSpeed * FastJEngine.getDeltaTime() * 100, 0f);
            gameObject.translate(movement);
        }
    }

    @Override
    public void onMouseClicked(MouseButtonEvent mouseButtonEvent) {
        if (isMoving) {
            isMoving = false;
            FastJEngine.runAfterRender(() -> {
                mainGame.inputManager.removeMouseActionListener(this);
                mainGame.calculateBlockPoints();
            });
        }
    }
}
