package game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun OthelloGameView() {
    // 8 x 8
    // Column
    // Row
    (0 until 8).forEach {
        Column {
            Row {
                (0 until 8).forEach {
                    PieceView(Piece.Empty)
                }
            }
        }
    }
}

@Composable
fun PieceView(piece: Piece) {
    var text by remember {
        mutableStateOf("")
    }

    Box {
        Button(onClick = {
            text = when (piece) {
                is Piece.Empty -> "○"
                is Piece.White -> "●"
                is Piece.Black -> ""
            }
        }) {
            Text(text)
        }
    }
}

sealed class Piece {
    object Empty : Piece()
    object White : Piece()
    object Black : Piece()
}