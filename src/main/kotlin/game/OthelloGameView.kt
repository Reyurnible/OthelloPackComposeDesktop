package game

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonConstants
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import game.models.GameBoard
import game.models.GameResult
import game.models.GameState
import game.models.OthelloGame
import game.models.Piece

@Composable
fun OthelloGameView(game: OthelloGame) {
    // 8 x 8
    // Column
    // Row
    Box {
        Column(
            Modifier.background(Color.Black).padding(4.dp)
        ) {
            GameBoard.COLUMN_RANGE.forEach { column ->
                Row {
                    GameBoard.ROW_RANGE.forEach { row ->
                        PieceView(game.board.get(column, row), onClicked = {
                            game.play(column, row)
                        })
                    }
                }
            }
        }
        // End Game
        if (game.state is GameState.Ended) {
            GameEndView(
                (game.state as GameState.Ended).result,
                Modifier.matchParentSize()
            )
        }
    }
}

@Composable
fun GameEndView(result: GameResult, matchParentModifier: Modifier) {
    Column(
        matchParentModifier.background(Color.Black.copy(alpha = 0.7f)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("GAME END", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        Text(
            text = when (result) {
                is GameResult.WinAndLose -> {
                    "Winner ${result.winner.name}"
                }
                is GameResult.Draw -> {
                    "DRAW"
                }
            },
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
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
                    Piece.White -> "●"
                    Piece.Black -> "●"
                },
                fontSize = 48.sp
            )
        }
    }
}
