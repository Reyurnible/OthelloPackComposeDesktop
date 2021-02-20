package game

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.Button
import androidx.compose.material.ButtonConstants
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import game.models.OthelloGame
import game.models.Piece

@Composable
fun OthelloGameView(game: OthelloGame) {
    // 8 x 8
    // Column
    // Row
    Column(
        Modifier.background(Color.Black).padding(4.dp)
    ) {
        (0 until 8).forEach { column ->
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
fun PieceView(piece: Piece, onClicked: () -> Unit = {}) {
    Box {
        Button(
            onClick = onClicked,
            modifier = Modifier.size(64.dp),
            shape = RectangleShape,
            colors = ButtonConstants.defaultButtonColors(
                backgroundColor = Color(0xFF15712A),
                disabledBackgroundColor = Color.LightGray,
                contentColor = when (piece) {
                    Piece.Empty -> Color.Red
                    Piece.White -> Color.White
                    Piece.Black -> Color.Black
                }
            ),
            border = BorderStroke(1.dp, Color.Black)
        ) {
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
