package game.models

enum class Turn {
    Black,
    White,
    ;

    fun toggle(): Turn = when (this) {
        Black -> White
        White -> Black
    }

    fun piece(): Piece = when(this) {
        Black -> Piece.Black
        White -> Piece.White
    }
}