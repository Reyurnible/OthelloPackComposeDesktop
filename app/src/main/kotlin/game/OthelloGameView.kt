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
import androidx.compose.runtime.snapshots.SnapshotStateMap
import java.lang.IllegalArgumentException

@Composable
fun OthelloGameView() {
    var turn by remember { mutableStateOf<Turn>(Turn.Black) }
    val board = remember {
        GameBoard().apply {
            start()
        }
    }

    // 8 x 8
    // Column
    // Row
    (0 until 8).forEach { colum ->
        Column {
            Row {
                (0 until 8).forEach { row ->
                    PieceView(board.get(colum, row), onClicked = {
                        when (turn) {
                            Turn.Black -> {
                                board.set(colum, row, value = Piece.Black)
                                turn = Turn.White
                            }
                            Turn.White -> {
                                board.set(colum, row, value = Piece.White)
                                turn = Turn.Black
                            }
                        }
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
                    is Piece.Empty -> ""
                    is Piece.White -> "○"
                    is Piece.Black -> "●"
                }
            )
        }
    }
}

class GameBoard {
    val boardValues = mutableStateMapOf<Pair<Int, Int>, Piece>()

    fun start() {
        boardValues.clear()
        boardValues[3 to 3] = Piece.White
        boardValues[4 to 4] = Piece.White
        boardValues[3 to 4] = Piece.Black
        boardValues[4 to 3] = Piece.Black
    }

    fun set(column: Int, row: Int, value: Piece) {
        checkPoint(column, row)
        boardValues[column to row] = value
    }

    fun get(column: Int, row: Int): Piece {
        checkPoint(column, row)
        return boardValues.getOrDefault(column to row, Piece.Empty)
    }

    private fun checkPoint(column: Int, row: Int) {
        if (column !in 0 until 8 || row !in 0 until 8) {
            throw IllegalArgumentException("Invalidate point(column: ${column}, row: ${row}) value.")
        }
    }
}

sealed class Turn {
    object Black : Turn()
    object White : Turn()
}

sealed class Piece {
    object Empty : Piece()
    object White : Piece()
    object Black : Piece()
}