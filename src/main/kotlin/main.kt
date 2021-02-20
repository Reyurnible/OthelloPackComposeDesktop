import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import game.OthelloGameView
import game.models.GameState
import game.models.OthelloGame

fun main() = Window {
    val game = remember { OthelloGame() }

    MaterialTheme {
        Row(Modifier.fillMaxWidth()) {
            Spacer(Modifier.fillMaxWidth().weight(1f))
            GameMenuView(game)
            Spacer(Modifier.width(32.dp))
            OthelloGameView(game)
            Spacer(Modifier.fillMaxWidth().weight(1f))
        }
    }
}

@Composable
fun GameMenuView(game: OthelloGame) {
    Column {
        Button(onClick = {
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
        }) {
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