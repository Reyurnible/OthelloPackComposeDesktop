package game.models

sealed class GameResult {
    data class WinAndLose(val winner: GamePlayer) : GameResult()
    object Draw : GameResult()
}
