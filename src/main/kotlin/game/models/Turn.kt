package game.models

enum class Turn {
    Black,
    White,
    ;

    fun toggle(): Turn = when (this) {
        Black -> White
        White -> Black
    }
}