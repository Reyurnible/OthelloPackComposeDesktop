package game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.lang.IllegalArgumentException

@Composable
fun OthelloGameView(game: OthelloGame) {

    // 8 x 8
    // Column
    // Row
    (0 until 8).forEach { column ->
        Column {
            Row {
                (0 until 8).forEach { row ->
                    PieceView(game.board.get(column, row), onClicked = {
                        game.play(column, row)
                    })
                }
            }
        }
    }
}

@Composable
fun PieceView(piece: Piece, onClicked: () -> Unit) {
    Box {
        Button(onClick = onClicked) {
            Text(
                text = when (piece) {
                    Piece.Empty -> ""
                    Piece.White -> "○"
                    Piece.Black -> "●"
                }
            )
        }
    }
}

class OthelloGame {
    // var turn by mutableStateOf<Turn>(Turn.Black)
    val board = GameBoard()
    var state by mutableStateOf<GameState>(GameState.NotStart)

    // Fun Game Action by Human
    fun start() {
        board.clear()
        board.set(3, 3, Piece.White)
        board.set(4, 4, Piece.White)
        board.set(3, 4, Piece.Black)
        board.set(4, 3, Piece.Black)

        state = GameState.Playing(turn = Turn.Black)
    }

    fun play(column: Int, row: Int) {
        if (state !is GameState.Playing) {
            return
        }

        val turn = (state as GameState.Playing).turn
        if (!checkValidPlay(column, row, turn)) {
            return
        }
        board.set(
            column, row, value = when (turn) {
                Turn.Black -> Piece.Black
                Turn.White -> Piece.White
            }
        )
        if (checkEnd()) {
            end()
            return
        }

        state = GameState.Playing(turn.toggle())
    }

    fun pass() {
        if (state !is GameState.Playing) {
            return
        }
        val newTurn = (state as GameState.Playing).turn.toggle()
        state = GameState.Playing(newTurn)
    }

    fun end() {
        state = GameState.Ended
    }

    private fun checkValidPlay(column: Int, row: Int, player: Turn): Boolean {
        return true
    }

    private fun checkEnd(): Boolean {
        // * one player can not make a valid move to outflank the opponent.
        // * both players have no valid moves.
        return false
    }
}

class GameBoard {
    val values: Map<Pair<Int, Int>, Piece> get() = _values
    private val _values = mutableStateMapOf<Pair<Int, Int>, Piece>()

    fun set(column: Int, row: Int, value: Piece) {
        checkPoint(column, row)
        _values[column to row] = value
    }

    fun get(column: Int, row: Int): Piece {
        checkPoint(column, row)
        return _values.getOrDefault(column to row, Piece.Empty)
    }

    fun clear() {
        _values.clear()
    }

    private fun checkPoint(column: Int, row: Int) {
        if (column !in 0 until 8 || row !in 0 until 8) {
            throw IllegalArgumentException("Invalidate point(column: ${column}, row: ${row}) value.")
        }
    }
}

sealed class GameState {
    object NotStart : GameState()
    data class Playing(val turn: Turn) : GameState()
    object Ended : GameState()
}

enum class Turn {
    Black,
    White,
    ;

    fun toggle(): Turn = when (this) {
        Black -> White
        White -> Black
    }
}

enum class Piece {
    Empty,
    White,
    Black,
    ;
}