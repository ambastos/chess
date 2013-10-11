package meu.chess.pieces;

import static meu.chess.Board.*;
import static org.junit.Assert.*;
import meu.chess.MovimentoInvalidoException;

import org.junit.Test;

class KingMovementTest extends TestPieceMovement {

	def WHITE_KING = new King(WHITE)
	def BLACK_KING = new King(BLACK)
	
	def static WHITE_PAWN =  new Pawn(WHITE)
	def static BLACK_PAWN =  new Pawn(BLACK)
	@Test
	public void deveMoverOReiBrancoUmaCasaAFrente() {
		
		board.movePiece(WHITE_PAWN, "E2", "E4")
		board.movePiece(WHITE_KING, "E1", "E2")
		
		assertEquals " ___ ___ ___ ___ ___ ___ ___ ___ \n"+
		"|RB |NB*|BB |QB*|KB |BB*|NB |RB*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PB*|PB |PB*|PB |PB*|PB |PB*|PB |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|   |  *|   |  *|   |  *|   |  *|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|  *|   |  *|   |  *|   |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|   |  *|   |  *|PW |  *|   |  *|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|  *|   |  *|   |  *|   |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PW |PW*|PW |PW*|KW |PW*|PW |PW*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|RW*|NW |BW*|QW |  *|BW |NW*|RW |\n"+
		"|___|___|___|___|___|___|___|___|",
		board.draw()
	}

	@Test
	public void deveMoverOReiBrancoUmaCasaAoLado() {
		
		board.movePiece(WHITE_PAWN, "E2", "E4")
		board.movePiece(new Bishop(WHITE), "F1", "D3")
		board.movePiece(WHITE_KING, "E1", "F1")
		
		assertEquals " ___ ___ ___ ___ ___ ___ ___ ___ \n"+
		"|RB |NB*|BB |QB*|KB |BB*|NB |RB*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PB*|PB |PB*|PB |PB*|PB |PB*|PB |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|   |  *|   |  *|   |  *|   |  *|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|  *|   |  *|   |  *|   |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|   |  *|   |  *|PW |  *|   |  *|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|  *|   |  *|BW |  *|   |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PW |PW*|PW |PW*|   |PW*|PW |PW*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|RW*|NW |BW*|QW |  *|KW |NW*|RW |\n"+
		"|___|___|___|___|___|___|___|___|",
		board.draw()
	}
	
	@Test
	public void deveMoverOReiBrancoUmaCasaAoLadoEsquerdo() {
		
		board.movePiece(WHITE_PAWN, "E2", "E4")
		board.movePiece(new Queen(WHITE), "D1", "F3")
		board.movePiece(WHITE_KING, "E1", "D1")
		
		assertEquals " ___ ___ ___ ___ ___ ___ ___ ___ \n"+
		"|RB |NB*|BB |QB*|KB |BB*|NB |RB*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PB*|PB |PB*|PB |PB*|PB |PB*|PB |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|   |  *|   |  *|   |  *|   |  *|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|  *|   |  *|   |  *|   |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|   |  *|   |  *|PW |  *|   |  *|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|  *|   |  *|   |  *|QW |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PW |PW*|PW |PW*|   |PW*|PW |PW*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|RW*|NW |BW*|KW |  *|BW |NW*|RW |\n"+
		"|___|___|___|___|___|___|___|___|",
		board.draw()
	}
	
	@Test
	public void deveMoverOReiBrancoParaDiagonalEsquerda() {
		
		board.movePiece(WHITE_PAWN, "F2", "F3")
		board.movePiece(WHITE_KING, "E1", "F2")
		
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
		"|  *|   |  *|   |  *|PW |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PW |PW*|PW |PW*|PW |KW*|PW |PW*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|RW*|NW |BW*|QW |  *|BW |NW*|RW |\n"+
		"|___|___|___|___|___|___|___|___|",
		board.draw()
	}
	
	@Test
	public void deveMoverOReiBrancoParaDiagonalDireita() {
		
		board.movePiece(WHITE_PAWN, "D2", "D3")
		board.movePiece(WHITE_KING, "E1", "D2")
		
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
		"|  *|   |  *|PW |  *|   |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PW |PW*|PW |KW*|PW |PW*|PW |PW*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|RW*|NW |BW*|QW |  *|BW |NW*|RW |\n"+
		"|___|___|___|___|___|___|___|___|",
		board.draw()
	}
	
	@Test
	public void deveMoverOReiNegroNaDiagonalDireita() {
		
		board.movePiece(BLACK_PAWN, "F7", "F6")
		board.movePiece(BLACK_KING, "E8", "F7")
		
		assertEquals " ___ ___ ___ ___ ___ ___ ___ ___ \n"+
		"|RB |NB*|BB |QB*|   |BB*|NB |RB*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PB*|PB |PB*|PB |PB*|KB |PB*|PB |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|   |  *|   |  *|   |PB*|   |  *|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|  *|   |  *|   |  *|   |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|   |  *|   |  *|   |  *|   |  *|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|  *|   |  *|   |  *|   |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PW |PW*|PW |PW*|PW |PW*|PW |PW*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|RW*|NW |BW*|QW |KW*|BW |NW*|RW |\n"+
		"|___|___|___|___|___|___|___|___|",
		board.draw()
	}
	
	@Test(expected=MovimentoInvalidoException.class)
	void naoDevePermitirMoverOReiNegroParaUmaCasaEmXeque() {
		
		board.movePiece(BLACK_PAWN, "E7", "E5")
		board.movePiece(BLACK_PAWN, "F7", "F5")
		board.movePiece(WHITE_PAWN, "E2", "E4")
		board.movePiece(new Queen(WHITE), "D1", "H5")
		board.movePiece(BLACK_KING, "E8", "F7")
		board.movePiece(BLACK_KING, "F7", "E8")
		
	}
}
