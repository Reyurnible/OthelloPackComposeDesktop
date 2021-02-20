package game.models

sealed class GameResult {
    data class WinAndLose(val winner: Turn) : GameResult()
    object Draw : GameResult()
}
