package game.models

sealed class GameState {
    object NotStart : GameState()
    data class Playing(val turn: Turn) : GameState()
    data class Ended(val result: GameResult) : GameState()
}