package tech.fastj.stackattack.user

class User {
    var score: Int = 0
        private set

    fun addToScore(scoreIncrement: Int) {
        score += scoreIncrement
    }

    fun resetScore() {
        score = 0
    }
}

val instance: User = User()