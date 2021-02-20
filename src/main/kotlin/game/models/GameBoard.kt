package game.models

import androidx.compose.runtime.mutableStateMapOf
import java.lang.IllegalArgumentException

class GameBoard {
    companion object {
        const val MIN = 0
        const val MAX = 8
    }

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

    fun count(piece: Piece): Int =
        values.count { it.value == piece }

    private fun checkPoint(column: Int, row: Int) {
        if (column !in MIN until MAX || row !in MIN until MAX) {
            throw IllegalArgumentException("Invalidate point(column: ${column}, row: ${row}) value.")
        }
    }
}
