package game.models

enum class GamePlayer {
    Black,
    White,
    ;

    fun toggle(): GamePlayer = when (this) {
        Black -> White
        White -> Black
    }

    fun piece(): Piece = when(this) {
        Black -> Piece.Black
        White -> Piece.White
    }
}