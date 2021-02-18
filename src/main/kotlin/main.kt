import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
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
                Text(when (game.state) {
                    is GameState.NotStart -> "START"
                    is GameState.Playing -> "END"
                    is GameState.Ended -> "RESTART"
                })
            }
        }
    }
}