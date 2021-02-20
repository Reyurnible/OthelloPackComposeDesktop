package game

import game.models.GameState
import game.models.OthelloGame
import game.models.Piece
import game.models.Turn
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
        Assert.assertEquals(GameState.Playing(Turn.Black), game.state)
        Assert.assertEquals(4, game.board.values.size)
        Assert.assertEquals(2, game.board.values.filter { it.value == Piece.Black }.size)
        Assert.assertEquals(Piece.White, game.board.get(3, 3))
        Assert.assertEquals(Piece.White, game.board.get(4, 4))
        Assert.assertEquals(Piece.Black, game.board.get(3, 4))
        Assert.assertEquals(Piece.Black, game.board.get(4, 3))
    }

    @Test
    fun play_success_newpiece_f5_horizontal_right() {
        game.start()
        Assert.assertEquals(GameState.Playing(Turn.Black), game.state)
        // 5f
        game.play(4, 5)
        Assert.assertEquals(5, game.board.values.size)
        Assert.assertEquals(4, game.board.values.filter { it.value == Piece.Black }.size)
        // Change to 5e
        Assert.assertEquals(Piece.Black, game.board.get(4, 4))
        Assert.assertEquals(GameState.Playing(Turn.White), game.state)
    }

    @Test
    fun play_success_newpiece_3d_vertical_top() {
        game.start()
        Assert.assertEquals(GameState.Playing(Turn.Black), game.state)
        game.play(2, 3)
        Assert.assertEquals(5, game.board.values.size)
        Assert.assertEquals(4, game.board.values.filter { it.value == Piece.Black }.size)
        Assert.assertEquals(GameState.Playing(Turn.White), game.state)
        // Change to 4d
        Assert.assertEquals(Piece.Black, game.board.get(3, 3))
        Assert.assertEquals(GameState.Playing(Turn.White), game.state)
    }

    @Test
    fun play_invalid_point_not_empty() {
        game.start()
        Assert.assertEquals(GameState.Playing(Turn.Black), game.state)

        // FIXME Current not yet handling check valid empty.
        game.play(3, 3)
        Assert.assertEquals(4, game.board.values.size)
        Assert.assertEquals(2, game.board.values.filter { it.value == Piece.Black }.size)
        Assert.assertEquals(GameState.Playing(Turn.Black), game.state)
    }

    @Test
    fun play_invalid_point_not_take() {
        game.start()
        Assert.assertEquals(GameState.Playing(Turn.Black), game.state)

        // FIXME Current not yet handling check valid play.
        game.play(6, 6)
        Assert.assertEquals(4, game.board.values.size)
        Assert.assertEquals(2, game.board.values.filter { it.value == Piece.Black }.size)
        Assert.assertEquals(GameState.Playing(Turn.Black), game.state)
    }

    @Test
    fun pass() {
        game.start()
        Assert.assertEquals(GameState.Playing(Turn.Black), game.state)
        game.pass()
        Assert.assertEquals(GameState.Playing(Turn.White), game.state)
    }

    @Test
    fun end() {
        game.start()
        Assert.assertEquals(GameState.Playing(Turn.Black), game.state)
        game.end()
        Assert.assertEquals(GameState.Ended, game.state)
    }
}