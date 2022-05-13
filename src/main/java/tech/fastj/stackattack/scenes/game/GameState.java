package tech.fastj.stackattack.scenes.game;

public enum GameState {
    Intro {
        @Override
        public GameState nextState() {
            return Playing;
        }
    },
    Playing {
        @Override
        public GameState nextState() {
            return Paused;
        }
    },
    Paused {
        @Override
        public GameState nextState() {
            return Playing;
        }
    },
    Results {
        @Override
        public GameState nextState() {
            return Intro;
        }
    };

    public abstract GameState nextState();
}