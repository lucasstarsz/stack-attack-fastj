package tech.fastj.stackattack.animation;

import tech.fastj.engine.FastJEngine;

import tech.fastj.animation.sprite.event.AnimationFlipEvent;
import tech.fastj.gameloop.event.GameEventObserver;
import tech.fastj.stackattack.scenes.game.GameState;
import tech.fastj.stackattack.scenes.game.MainGame;

@SuppressWarnings("rawtypes")
public record IntroFlipObserver(MainGame game) implements GameEventObserver<AnimationFlipEvent> {

    private static final int TriggerGoFrame = 12;

    @Override
    public void eventReceived(AnimationFlipEvent event) {
        if (event.getNewFrame() == TriggerGoFrame) {
            game.changeState(GameState.Playing);
        }
        if (event.getNewFrame() == event.getAnimationData().getLastFrame()) {
            FastJEngine.runAfterRender(game::introEnded);
        }
    }
}
