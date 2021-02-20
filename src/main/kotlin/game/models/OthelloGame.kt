package game.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

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