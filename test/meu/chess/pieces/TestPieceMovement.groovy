package meu.chess.pieces

import org.junit.Before;

import meu.chess.Board;

class TestPieceMovement {

	Board board
	@Before
	void before() {
		board = new Board()
		board.initializeWithInitialPosition()
	}
}
