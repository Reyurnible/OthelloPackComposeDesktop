package game.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.lang.IllegalArgumentException

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
        board.set(column, row, value = turn.piece())
        checkPointsList(column, row)
            .forEach {
                takeSandArrayPoint(it, turn.piece(), turn.toggle().piece())
            }

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
        println("checkValidPlay(${column}, ${row}, ${player})")
        // Check already exist
        if (!checkValidPlayAlreadyExist(column, row)) {
            return false
        }

        // Check take piece
        if (!checkValidPlayTake(column, row, player)) {
            return false
        }

        return true
    }

    private fun checkValidPlayAlreadyExist(column: Int, row: Int): Boolean =
        board.get(column, row) == Piece.Empty

    private fun checkValidPlayTake(column: Int, row: Int, player: Turn): Boolean =
        checkPointsList(column, row)
            .any {
                isSandArrayPoint(it, player.piece(), player.toggle().piece())
            }

    private fun checkPointsList(column: Int, row: Int): List<List<Pair<Int, Int>>> =
        arrayOf(
            // Vertical
            (0 until GameBoard.MAX).map { column - it to row },
            (0 until GameBoard.MAX).map { column + it to row },
            // Horizontal
            (0 until GameBoard.MAX).map { column to row - it },
            (0 until GameBoard.MAX).map { column to row + it },
            // Cross
            (0 until GameBoard.MAX).map { column - it to row - it },
            (0 until GameBoard.MAX).map { column - it to row + it },
            (0 until GameBoard.MAX).map { column + it to row - it },
            (0 until GameBoard.MAX).map { column + it to row + it },
        ).map {
            it.filter { (column, row) ->
                column in GameBoard.MIN until GameBoard.MAX && row in GameBoard.MIN until GameBoard.MAX
            }
        }

    private fun isSandArrayPoint(points: Iterable<Pair<Int, Int>>, current: Piece, target: Piece): Boolean {
        var count = 0
        if (current == Piece.Empty || target == Piece.Empty) {
            throw IllegalArgumentException("Invalid piece is not Empty value to current and target.")
        }
        points
            .forEachIndexed { index, (column, row) ->
                when {
                    index == 0 -> {
                        // Nothing
                    }
                    index > 0 && board.get(column, row) == target -> {
                        count++
                    }
                    index > 0 && board.get(column, row) == current -> {
                        return count > 0
                    }
                }
            }
        return false
    }

    private fun takeSandArrayPoint(points: Iterable<Pair<Int, Int>>, current: Piece, target: Piece) {
        if (isSandArrayPoint(points, current, target)) {
            points
                .forEachIndexed { index, (column, row) ->
                    when {
                        index > 0 && board.get(column, row) == target -> {
                            board.set(column, row, current)
                        }
                        index > 0 && board.get(column, row) != current -> {
                            return@forEachIndexed
                        }
                    }
                }
        }

    }

    private fun checkEnd(): Boolean {
        // * one player can not make a valid move to outflank the opponent.
        // * both players have no valid moves.
        return false
    }
}