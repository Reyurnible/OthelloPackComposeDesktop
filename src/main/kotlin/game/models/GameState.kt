package game.models

sealed class GameState {
    object NotStart : GameState()
    data class Playing(val player: GamePlayer) : GameState()
    data class Ended(val result: GameResult) : GameState()
}