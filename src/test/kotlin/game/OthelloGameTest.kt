package game

import game.models.GameResult
import game.models.GameState
import game.models.OthelloGame
import game.models.Piece
import game.models.GamePlayer
import org.junit.Assert
import org.junit.Before
import org.junit.Test

internal class OthelloGameTest {
    private lateinit var game: OthelloGame

    @Before
    fun setup() {
        game = OthelloGame()
    }

    @Test
    fun start() {
        Assert.assertEquals(GameState.NotStart, game.state)

        game.start()
        Assert.assertEquals(GameState.Playing(GamePlayer.Black), game.state)
        Assert.assertEquals(4, game.board.values.size)
        Assert.assertEquals(2, game.board.values.filter { it.value == Piece.Black }.size)
        Assert.assertEquals(Piece.White, game.board.get(4, 'd'))
        Assert.assertEquals(Piece.White, game.board.get(5, 'e'))
        Assert.assertEquals(Piece.Black, game.board.get(4, 'e'))
        Assert.assertEquals(Piece.Black, game.board.get(5, 'd'))
    }

    @Test
    fun play_success_newpiece_f5_horizontal_right() {
        game.start()
        Assert.assertEquals(GameState.Playing(GamePlayer.Black), game.state)
        // 5f
        game.play(5, 'f')
        Assert.assertEquals(5, game.board.values.size)
        Assert.assertEquals(4, game.board.values.filter { it.value == Piece.Black }.size)
        // Change to 5e
        Assert.assertEquals(Piece.Black, game.board.get(5, 'e'))
        Assert.assertEquals(GameState.Playing(GamePlayer.White), game.state)
    }

    @Test
    fun play_success_newpiece_3d_vertical_top() {
        game.start()
        Assert.assertEquals(GameState.Playing(GamePlayer.Black), game.state)
        game.play(3, 'd')
        Assert.assertEquals(5, game.board.values.size)
        Assert.assertEquals(4, game.board.values.filter { it.value == Piece.Black }.size)
        Assert.assertEquals(GameState.Playing(GamePlayer.White), game.state)
        // Change to 4d
        Assert.assertEquals(Piece.Black, game.board.get(4, 'd'))
        Assert.assertEquals(GameState.Playing(GamePlayer.White), game.state)
    }

    @Test
    fun play_invalid_point_not_empty() {
        game.start()
        Assert.assertEquals(GameState.Playing(GamePlayer.Black), game.state)

        // Current not yet handling check valid empty.
        game.play(4, 'd')
        Assert.assertEquals(4, game.board.values.size)
        Assert.assertEquals(2, game.board.values.filter { it.value == Piece.Black }.size)
        Assert.assertEquals(GameState.Playing(GamePlayer.Black), game.state)
    }

    @Test
    fun play_invalid_point_not_take() {
        game.start()
        Assert.assertEquals(GameState.Playing(GamePlayer.Black), game.state)

        // Current not yet handling check valid play.
        game.play(7, 'g')
        Assert.assertEquals(4, game.board.values.size)
        Assert.assertEquals(2, game.board.values.filter { it.value == Piece.Black }.size)
        Assert.assertEquals(GameState.Playing(GamePlayer.Black), game.state)
    }

    @Test
    fun pass() {
        game.start()
        Assert.assertEquals(GameState.Playing(GamePlayer.Black), game.state)
        game.pass()
        Assert.assertEquals(GameState.Playing(GamePlayer.White), game.state)
    }

    @Test
    fun end_draw() {
        game.start()
        game.end()
        Assert.assertEquals(GameState.Ended(GameResult.Draw), game.state)
    }

    @Test
    fun end_winner_black() {
        game.start()
        game.play(5, 'f')
        // Black : White = 4 : 1
        game.end()
        Assert.assertEquals(GameState.Ended(GameResult.WinAndLose(GamePlayer.Black)), game.state)
    }
}