package game

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
    }

    @Test
    fun play_success_newpiece() {
        game.start()
        Assert.assertEquals(GameState.Playing(Turn.Black), game.state)
        // f5
        game.play(5, 5)
        Assert.assertEquals(5, game.board.values.size)
        Assert.assertEquals(3, game.board.values.filter { it.value == Piece.Black }.size)
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