import androidx.compose.desktop.Window
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.ButtonConstants
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import game.OthelloGameView
import game.PieceView
import game.models.GameState
import game.models.OthelloGame
import game.models.Piece
import game.models.Turn

fun main() = Window {
    val game = remember { OthelloGame() }

    Column(
        Modifier.fillMaxWidth().fillMaxHeight().background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row {
            GameMenuView(game)
            Spacer(Modifier.width(32.dp))
            OthelloGameView(game)
        }
    }
}

@Composable
fun GameMenuView(game: OthelloGame) {
    Column(Modifier.padding(vertical = 48.dp)) {
        Text("TURN", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(4.dp))
        PieceView((game.state as? GameState.Playing)?.let {
            when (it.turn) {
                Turn.Black -> Piece.Black
                Turn.White -> Piece.White
            }
        } ?: Piece.Empty, onClicked = {})

        Spacer(Modifier.height(24.dp))

        Text("SCORE", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(4.dp))
        // Black
        Row(verticalAlignment = Alignment.CenterVertically) {
            PieceView(Piece.Black)
            Spacer(Modifier.width(16.dp))
            Text(
                game.board.count(Piece.Black).toString(),
                color = Color.White,
                fontSize = 24.sp
            )
        }
        // White
        Row(verticalAlignment = Alignment.CenterVertically) {
            PieceView(Piece.White)
            Spacer(Modifier.width(16.dp))
            Text(
                game.board.count(Piece.White).toString(),
                color = Color.White,
                fontSize = 24.sp
            )
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                when (game.state) {
                    is GameState.NotStart -> {
                        game.start()
                    }
                    is GameState.Playing -> {
                        game.end()
                    }
                    is GameState.Ended -> {
                        game.start()
                    }
                }
            },
            modifier = Modifier.width(120.dp),
            colors = ButtonConstants.defaultButtonColors(
                backgroundColor = Color(0xFF15712A),
                disabledBackgroundColor = Color.LightGray,
                contentColor = Color.White
            ),
            border = BorderStroke(2.dp, Color.White)
        ) {
            Text(
                when (game.state) {
                    is GameState.NotStart -> "START"
                    is GameState.Playing -> "END"
                    is GameState.Ended -> "RESTART"
                }
            )
        }
    }
}