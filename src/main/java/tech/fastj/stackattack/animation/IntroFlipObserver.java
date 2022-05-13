package tech.fastj.stackattack.animation;

import tech.fastj.engine.FastJEngine;

import tech.fastj.stackattack.scenes.game.GameState;
import tech.fastj.stackattack.scenes.game.MainGame;
import tech.fastj.animation.sprite.event.AnimationFlipEvent;
import tech.fastj.gameloop.event.GameEventObserver;

@SuppressWarnings("rawtypes")
public record IntroFlipObserver(MainGame game) implements GameEventObserver<AnimationFlipEvent> {

    private static final int TriggerGoFrame = 12;

    public IntroFlipObserver(MainGame game) {
        this.game = game;
        FastJEngine.getGameLoop().addEventObserver(this, AnimationFlipEvent.class);
    }

    @Override
    public void eventReceived(AnimationFlipEvent event) {
        if (event.getNewFrame() == TriggerGoFrame) {
            game.changeState(GameState.Intro.nextState());
        }
        if (event.getNewFrame() == event.getAnimationData().getLastFrame()) {
            FastJEngine.runAfterRender(() -> {
                FastJEngine.getGameLoop().removeEventObserver(this, AnimationFlipEvent.class);
                game.introEnded();
            });
        }
    }
}
