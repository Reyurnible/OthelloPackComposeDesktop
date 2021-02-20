package game.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.lang.IllegalArgumentException

class OthelloGame {
    val board = GameBoard()
    var state by mutableStateOf<GameState>(GameState.NotStart)

    // Fun Game Action by Human
    fun start() {
        board.clear()
        board.set(4, 'd', Piece.White)
        board.set(5, 'e', Piece.White)
        board.set(4, 'e', Piece.Black)
        board.set(5, 'd', Piece.Black)

        state = GameState.Playing(player = GamePlayer.Black)
    }

    fun play(column: BoardColumn, row: BoardRow) {
        if (state !is GameState.Playing) {
            return
        }

        val turnPlayer = (state as GameState.Playing).player
        if (!checkValidPlay(column, row, turnPlayer)) {
            return
        }
        board.set(column, row, value = turnPlayer.piece())
        checkPointsList(column, row)
            .forEach {
                takeSandArrayPoint(it, turnPlayer.piece(), turnPlayer.toggle().piece())
            }

        if (checkEnd()) {
            end()
            return
        }

        state = GameState.Playing(turnPlayer.toggle())
    }

    fun pass() {
        if (state !is GameState.Playing) {
            return
        }
        val newTurn = (state as GameState.Playing).player.toggle()
        state = GameState.Playing(newTurn)
    }

    fun end() {
        state = GameState.Ended(
            when {
                board.count(Piece.Black) > board.count(Piece.White) -> GameResult.WinAndLose(GamePlayer.Black)
                board.count(Piece.White) > board.count(Piece.Black) -> GameResult.WinAndLose(GamePlayer.White)
                else -> GameResult.Draw
            }
        )
    }

    private fun checkValidPlay(column: BoardColumn, row: BoardRow, player: GamePlayer): Boolean {
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

    private fun checkValidPlayAlreadyExist(column: BoardColumn, row: BoardRow): Boolean =
        board.isEmpty(column, row)

    private fun checkValidPlayTake(column: BoardColumn, row: BoardRow, player: GamePlayer): Boolean =
        checkPointsList(column, row)
            .any {
                isSandArrayPoint(it, player.piece(), player.toggle().piece())
            }

    private fun checkPointsList(column: BoardColumn, row: BoardRow): List<List<BoardPoint>> =
        (0 until GameBoard.SIZE)
            .let { range ->
                arrayOf(
                    // Vertical
                    range.map { column - it to row },
                    range.map { column + it to row },
                    // Horizontal
                    range.map { column to row - it },
                    range.map { column to row + it },
                    // Cross
                    range.map { column - it to row - it },
                    range.map { column - it to row + it },
                    range.map { column + it to row - it },
                    range.map { column + it to row + it },
                ).map {
                    it.filter { (column, row) ->
                        GameBoard.isContains(column, row)
                    }
                }
            }

    private fun isSandArrayPoint(points: Iterable<BoardPoint>, current: Piece, target: Piece): Boolean {
        if (current == Piece.Empty || target == Piece.Empty) {
            throw IllegalArgumentException("Invalid piece is not Empty value to current and target.")
        }
        var count = 0
        points
            // 最初の自分のコマは無視する
            .filterIndexed { index, _ -> index > 0 }
            .forEach {
                when {
                    board.get(it) == target -> {
                        count++
                    }
                    board.get(it) == current -> {
                        return count > 0
                    }
                    board.isEmpty(it) -> {
                        return false
                    }
                }
            }
        return false
    }

    private fun takeSandArrayPoint(points: Iterable<BoardPoint>, current: Piece, target: Piece) {
        if (!isSandArrayPoint(points, current, target)) {
            return
        }
        points
            .filterIndexed { index, _ -> index > 0 }
            .forEach {
                when {
                    board.get(it) == target -> {
                        board.set(it, current)
                    }
                    board.get(it) != target -> {
                        return
                    }
                }
            }
    }

    private fun checkEnd(): Boolean {
        // * one player can not make a valid move to outflank the opponent.
        // * both players have no valid moves.
        return countPlayingValue() <= 0
    }

    private fun countPlayingValue(): Int {
        var count = 0
        GameBoard.COLUMN_RANGE.forEach { column ->
            GameBoard.ROW_RANGE.forEach { row ->
                if (board.get(column, row) == Piece.Empty) {
                    if (checkValidPlay(column, row, GamePlayer.Black) ||
                        checkValidPlay(column, row, GamePlayer.White)) {
                        count++
                    }
                }
            }
        }
        return count
    }
}