package tech.fastj.stackattack.scenes.game;

import java.util.Arrays;
import java.util.List;

public enum GameStartDifficulty {
    Easy(200),
    Normal(100),
    Difficult(50),
    WhyAreYouLikeThis(10);

    public static final List<String> DifficultiesList = Arrays.stream(GameStartDifficulty.values())
            .map(GameStartDifficulty::toString)
            .toList();

    private final int value;

    GameStartDifficulty(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
