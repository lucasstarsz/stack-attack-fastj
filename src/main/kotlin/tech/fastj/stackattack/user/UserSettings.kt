package tech.fastj.stackattack.user

import tech.fastj.stackattack.scenes.game.GameStartDifficulty

class UserSettings(difficulty: Int = 0, startDifficulty: GameStartDifficulty = GameStartDifficulty.Normal) {

    var highestDifficultyReached: Int
        private set
    var gameStartDifficulty: GameStartDifficulty

    init {
        highestDifficultyReached = difficulty
        gameStartDifficulty = startDifficulty
    }

    fun incrementDifficulty() {
        highestDifficultyReached++
    }
}