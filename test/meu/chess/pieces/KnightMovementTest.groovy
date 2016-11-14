package meu.chess.pieces;

import static Board.*
import static org.junit.Assert.*;

import meu.chess.Board;
import meu.chess.MovimentoInvalidoException;

import org.junit.Before;
import org.junit.Test;

class KnightMovementTest extends TestPieceMovement {
	
	def static WHITE_PAWN =  new Pawn(WHITE)
	def static BLACK_PAWN =  new Pawn(BLACK)
	def static WHITE_KNIGHT =  new Knight(WHITE)
	def static BLACK_KNIGHT =  new Knight(BLACK)
	
	@Before
	void before() {
		board = new Board()
	}

	@Test
	void deveLancarUmErroAoTentarMoverOCavaloParaUmaCasaInvalida() {
		
		board.initializeWithInitialPosition()
		try {
			board.movePiece(BLACK_KNIGHT, "B8", "B6")
			fail()
		} catch (Exception e) {
			assertTrue(true)
		}
	}
	
	@Test(expected=MovimentoInvalidoException.class)
	void deveLancarUmErroAoTentarMoverOCavaloParaUmaComUmaPecaDeMesmaCor() {
		
		board.initializeWithInitialPosition()
		board.movePiece(WHITE_PAWN, "C2", "C3")
	
		//Há uma peão branco na casa C3 o que invalida o movimento
		board.movePiece(WHITE_KNIGHT, "B1", "C3")
		
	}
	
	@Test
	void validaMovimentoDoCavaloParaF6() {
		
		board.initializeWithInitialPosition()
		board.movePiece(WHITE_KNIGHT, "B1", "C3")
		board.movePiece(BLACK_PAWN, "F7", "F5")
	
		//Há uma peão branco na casa F5 que não invalida o movimento
		board.movePiece(BLACK_KNIGHT, "G8", "F6")
		
	}
	
	@Test(expected= MovimentoInvalidoException.class)
	void naoDevePermitirMovimentarAPecaNumaCordenadaInvalida() {
		
		board.initializeWithInitialPosition()
		board.movePiece(BLACK_KNIGHT, "G8", "F10")//F10 e invalido
	}
	
	@Test
	public void oCavaloDeF1DeveSeMoverParaACasaF3() {
		
		board.initializeWithInitialPosition()
		
		board.movePiece(WHITE_KNIGHT, "G1","F3")
		assertEquals " ___ ___ ___ ___ ___ ___ ___ ___ \n"+
		"|RB |NB*|BB |QB*|KB |BB*|NB |RB*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PB*|PB |PB*|PB |PB*|PB |PB*|PB |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|   |  *|   |  *|   |  *|   |  *|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|  *|   |  *|   |  *|   |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|   |  *|   |  *|   |  *|   |  *|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|  *|   |  *|   |  *|NW |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PW |PW*|PW |PW*|PW |PW*|PW |PW*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|RW*|NW |BW*|QW |KW*|BW |  *|RW |\n"+
		"|[_]|___|___|___|___|___|___|___|",
		board.draw()

	}
	
	@Test
	public void oCavaloDeveCapturaOPeao() {
		
		board.initializeWithInitialPosition()
		board.movePiece(WHITE_KNIGHT, "G1","F3")
		board.movePiece(BLACK_PAWN, "E7","E5")
		board.movePiece(WHITE_KNIGHT, "F3","E5")
		
		assertEquals " ___ ___ ___ ___ ___ ___ ___ ___ \n"+
		"|RB |NB*|BB |QB*|KB |BB*|NB |RB*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PB*|PB |PB*|PB |  *|PB |PB*|PB |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|   |  *|   |  *|   |  *|   |  *|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|  *|   |  *|   |NW*|   |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|   |  *|   |  *|   |  *|   |  *|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|  *|   |  *|   |  *|   |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PW |PW*|PW |PW*|PW |PW*|PW |PW*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|RW*|NW |BW*|QW |KW*|BW |  *|RW |\n"+
		"|[ ]|___|___|___|___|___|___|___|",
		board.draw()
	}

}
