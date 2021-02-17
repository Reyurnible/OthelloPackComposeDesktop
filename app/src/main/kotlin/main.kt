import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import game.GameState
import game.OthelloGame
import game.OthelloGameView

fun main() = Window {
    val game = remember { OthelloGame() }

    MaterialTheme {
        Column {
            OthelloGameView(game)
            Button(onClick = {
                when (game.state) {
                    GameState.NotStart -> {
                        game.start()
                    }
                    GameState.Playing -> {
                        game.end()
                    }
                    GameState.Ended -> {
                        game.start()
                    }
                }
            }) {
                Text(when (game.state) {
                    GameState.NotStart -> "START"
                    GameState.Playing -> "END"
                    GameState.Ended -> "RESTART"
                })
            }
        }
    }
}