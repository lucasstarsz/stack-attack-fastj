package tech.fastj.stackattack.user

class User {

    var score: Int = 0
        private set

    var highScore: Int = 0
        private set

    var numberStacked: Int = 0
        private set

    var highestNumberStacked: Int = 0
        private set

    var hasHighScore: Boolean = false
        private set

    var hasHighBlocksStacked: Boolean = false
        private set

    val settings: UserSettings = UserSettings()

    fun addToScore(scoreIncrement: Int) {
        score += scoreIncrement
        if (score > highScore) {
            highScore = score
            hasHighScore = true
        }

        numberStacked++
        if (highestNumberStacked < numberStacked) {
            highestNumberStacked = numberStacked
            hasHighBlocksStacked = true
        }
    }

    fun resetScore() {
        score = 0
        numberStacked = 0
        hasHighScore = false
        hasHighBlocksStacked = false
    }
}

val instance: User = User()