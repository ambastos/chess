package meu.chess.pieces;

import static meu.chess.Board.*
import static org.junit.Assert.*;
import meu.chess.MovimentoInvalidoException;

import org.junit.Test;

class QueenMovementTest extends TestPieceMovement {

	def WHITE_QUEEN = new Queen(WHITE)
	def BLACK_QUEEN = new Queen(BLACK)
	def WHITE_PAWN = new Pawn(WHITE)
	def BLACK_PAWN = new Pawn(BLACK)
	
	@Test
	public void deveMoverARainhaEmLinhaRetaParaAD3() {
		
		board.movePiece(WHITE_PAWN , "D2", "D4")
		board.movePiece(WHITE_QUEEN, "D1", "D3")
		
		assertEquals " ___ ___ ___ ___ ___ ___ ___ ___ \n"+
		"|RB |NB*|BB |QB*|KB |BB*|NB |RB*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PB*|PB |PB*|PB |PB*|PB |PB*|PB |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|   |  *|   |  *|   |  *|   |  *|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|  *|   |  *|   |  *|   |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|   |  *|   |PW*|   |  *|   |  *|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|  *|   |  *|QW |  *|   |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PW |PW*|PW |  *|PW |PW*|PW |PW*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|RW*|NW |BW*|   |KW*|BW |NW*|RW |\n"+
		"|___|___|___|___|___|___|___|___|",
		board.draw()
		
	}
	
	@Test(expected=MovimentoInvalidoException.class)
	public void naoDevePermitirARainhaPularAPecaEmE2() {
		
		board.movePiece(WHITE_QUEEN, "D1", "H5")
		
	}
	
	@Test
	public void deveMoverARainhaBrancaDiagonalmenteParaA4() {
		
		board.movePiece(WHITE_PAWN , "C2", "C4")
		board.movePiece(WHITE_QUEEN, "D1", "A4")
		
		assertEquals " ___ ___ ___ ___ ___ ___ ___ ___ \n"+
		"|RB |NB*|BB |QB*|KB |BB*|NB |RB*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PB*|PB |PB*|PB |PB*|PB |PB*|PB |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|   |  *|   |  *|   |  *|   |  *|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|  *|   |  *|   |  *|   |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|QW |  *|PW |  *|   |  *|   |  *|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|  *|   |  *|   |  *|   |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PW |PW*|   |PW*|PW |PW*|PW |PW*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|RW*|NW |BW*|   |KW*|BW |NW*|RW |\n"+
		"|___|___|___|___|___|___|___|___|",
		board.draw()
		
	}

	@Test
	public void deveCapturarOPeaoNegroEmA7() {
		
		board.movePiece(WHITE_PAWN , "C2", "C4")
		board.movePiece(WHITE_QUEEN, "D1", "A4")
		board.movePiece(WHITE_QUEEN, "A4", "A7")
		
		assertEquals " ___ ___ ___ ___ ___ ___ ___ ___ \n"+
		"|RB |NB*|BB |QB*|KB |BB*|NB |RB*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|QW*|PB |PB*|PB |PB*|PB |PB*|PB |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|   |  *|   |  *|   |  *|   |  *|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|  *|   |  *|   |  *|   |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|   |  *|PW |  *|   |  *|   |  *|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|  *|   |  *|   |  *|   |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PW |PW*|   |PW*|PW |PW*|PW |PW*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|RW*|NW |BW*|   |KW*|BW |NW*|RW |\n"+
		"|___|___|___|___|___|___|___|___|",
		board.draw()
		
	}
	
	@Test
	public void deveCapturarOPeaoBrancoEmF2() {
		
		board.movePiece(BLACK_PAWN , "E7", "E5")
		board.movePiece(BLACK_QUEEN, "D8", "H4")
		board.movePiece(BLACK_QUEEN, "H4", "F2")
		
		assertEquals " ___ ___ ___ ___ ___ ___ ___ ___ \n"+
		"|RB |NB*|BB |  *|KB |BB*|NB |RB*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PB*|PB |PB*|PB |  *|PB |PB*|PB |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|   |  *|   |  *|   |  *|   |  *|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|  *|   |  *|   |PB*|   |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|   |  *|   |  *|   |  *|   |  *|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|  *|   |  *|   |  *|   |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PW |PW*|PW |PW*|PW |QB*|PW |PW*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|RW*|NW |BW*|QW |KW*|BW |NW*|RW |\n"+
		"|___|___|___|___|___|___|___|___|",
		board.draw()
	}
	
	@Test
	public void deveCapturarOCavaloBrancoEmF3() {
		
		board.movePiece(BLACK_PAWN , "E7", "E5")
		board.movePiece(BLACK_QUEEN, "D8", "F6")
		board.movePiece(new Knight(WHITE), "G1", "F3")
		board.movePiece(BLACK_QUEEN, "F6", "F3")
		
		assertEquals " ___ ___ ___ ___ ___ ___ ___ ___ \n"+
		"|RB |NB*|BB |  *|KB |BB*|NB |RB*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PB*|PB |PB*|PB |  *|PB |PB*|PB |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|   |  *|   |  *|   |  *|   |  *|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|  *|   |  *|   |PB*|   |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|   |  *|   |  *|   |  *|   |  *|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|  *|   |  *|   |  *|QB |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PW |PW*|PW |PW*|PW |PW*|PW |PW*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|RW*|NW |BW*|QW |KW*|BW |  *|RW |\n"+
		"|___|___|___|___|___|___|___|___|",
		board.draw()
	}

	@Test(expected=MovimentoInvalidoException.class)
	public void deveLancarErroSeOMovimentoDaDamaNaoForUmaHorizontalValida() {
		
		board.movePiece(BLACK_PAWN , "E7", "E5")
		board.movePiece(BLACK_QUEEN, "D8", "F6")
		board.movePiece(new Knight(WHITE), "G1", "F3")
		board.movePiece(BLACK_QUEEN, "F6", "H5")
		
	}
	
}
