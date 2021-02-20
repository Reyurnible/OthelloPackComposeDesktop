package game.models

sealed class GameState {
    object NotStart : GameState()
    data class Playing(val turn: Turn) : GameState()
    object Ended : GameState()
}