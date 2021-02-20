package game.models

import androidx.compose.runtime.mutableStateMapOf
import java.lang.IllegalArgumentException

typealias BoardPoint = Pair<BoardColumn, BoardRow>
typealias BoardColumn = Int
typealias BoardRow = Char

class GameBoard {
    companion object {
        const val SIZE = 8
        private const val COLUMN_MIN = 1
        private const val COLUMN_MAX = SIZE
        private const val ROW_MIN = 'a'
        private const val ROW_MAX = 'h'

        val COLUMN_RANGE = COLUMN_MIN .. COLUMN_MAX
        val ROW_RANGE = ROW_MIN .. ROW_MAX

        fun isContains(column: BoardColumn, row: BoardRow) =
            column in COLUMN_RANGE && row in ROW_RANGE
    }

    val values: Map<BoardPoint, Piece> get() = _values
    private val _values = mutableStateMapOf<BoardPoint, Piece>()

    fun set(point: BoardPoint, value: Piece) {
        set(point.first, point.second, value)
    }

    fun set(column: BoardColumn, row: BoardRow, value: Piece) {
        checkPoint(column, row)
        _values[column to row] = value
    }

    fun get(point: BoardPoint): Piece =
        get(point.first, point.second)

    fun get(column: BoardColumn, row: BoardRow): Piece {
        checkPoint(column, row)
        return _values.getOrDefault(column to row, Piece.Empty)
    }

    fun clear() {
        _values.clear()
    }

    fun count(piece: Piece): Int =
        values.count { it.value == piece }

    private fun checkPoint(column: BoardColumn, row: BoardRow) {
        if (!isContains(column, row)) {
            throw IllegalArgumentException("Invalidate point(column: ${column}, row: ${row}) value.")
        }
    }
}
