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
        val isCheckValidPlayTakeVertical = checkValidPlayTakeVertical(column, row, player)
        println("checkValidPlayTakeVertical: ${isCheckValidPlayTakeVertical}")
        val isCheckHorizontalPlayTakeVertical = checkHorizontalPlayTakeVertical(column, row, player)
        println("checkHorizontalPlayTakeVertical: ${isCheckHorizontalPlayTakeVertical}")
        val isCheckHorizontalPlayTakeCross = checkHorizontalPlayTakeCross(column, row, player)
        println("checkHorizontalPlayTakeCross: ${checkHorizontalPlayTakeCross(column, row, player)}")
        return isCheckValidPlayTakeVertical
            || isCheckHorizontalPlayTakeVertical
            || isCheckHorizontalPlayTakeCross
//        return (checkValidPlayTakeVertical(column, row, player)
//            || checkHorizontalPlayTakeVertical(column, row, player)
//            || checkHorizontalPlayTakeCross(column, row, player))
    }

    private fun checkValidPlayAlreadyExist(column: Int, row: Int): Boolean =
        board.get(column, row) == Piece.Empty

    private fun checkValidPlayTakeVertical(column: Int, row: Int, player: Turn): Boolean =
        isSandArrayPoint((GameBoard.MIN..column).map { it to row }.reversed(), player.piece(), player.toggle().piece())
            || isSandArrayPoint((column until GameBoard.MAX).map { it to row }, player.piece(), player.toggle().piece())

    private fun checkHorizontalPlayTakeVertical(column: Int, row: Int, player: Turn): Boolean =
        isSandArrayPoint((GameBoard.MIN..row).map { column to it }.reversed(), player.piece(), player.toggle().piece())
            || isSandArrayPoint((row until GameBoard.MAX).map { column to it }, player.piece(), player.toggle().piece())

    private fun checkHorizontalPlayTakeCross(column: Int, row: Int, player: Turn): Boolean =
        // Top - Left
        isSandArrayPoint(
            (0 until GameBoard.MAX).map { column - it to row - it },
            player.piece(),
            player.toggle().piece()
        )
            // Top - Right
            || isSandArrayPoint(
            (0 until GameBoard.MAX).map { column - it to row + it },
            player.piece(),
            player.toggle().piece()
        )
            // Bottom - Left
            || isSandArrayPoint(
            (0 until GameBoard.MAX).map { column + it to row - it },
            player.piece(),
            player.toggle().piece()
        )
            // Bottom - Right
            || isSandArrayPoint(
            (0 until GameBoard.MAX).map { column + it to row + it },
            player.piece(),
            player.toggle().piece()
        )

    private fun isSandArrayPoint(points: Iterable<Pair<Int, Int>>, current: Piece, target: Piece): Boolean {
        var count = 0
        points
            .filter { (column, row) ->
                column in GameBoard.MIN until GameBoard.MAX
                    && row in GameBoard.MIN until GameBoard.MAX
            }
            .let {
                println("points: ${it}")
                it
            }
            .forEachIndexed { index, point ->
                when {
                    index == 0 -> {
                        // Nothing
                    }
                    index > 0 && board.values[point] == target -> {
                        count++
                    }
                    index > 0 && board.values[point] == current -> {
                        return count > 0
                    }
                }
            }
        return false
    }

    private fun checkEnd(): Boolean {
        // * one player can not make a valid move to outflank the opponent.
        // * both players have no valid moves.
        return false
    }
}