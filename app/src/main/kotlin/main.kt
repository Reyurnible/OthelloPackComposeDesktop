import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import game.OthelloGameView

fun main() = Window {
    MaterialTheme {
        Column {
            OthelloGameView()
            Button(onClick = {

            }) {
                Text("START")
            }
        }
    }
}