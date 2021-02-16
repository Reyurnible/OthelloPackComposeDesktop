import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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