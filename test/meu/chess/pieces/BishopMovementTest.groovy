package meu.chess.pieces;

import org.junit.Test;
import static meu.chess.Board.*
import static org.junit.Assert.*;

import meu.chess.MovimentoInvalidoException;

import org.junit.Test;

class BishopMovementTest extends TestPieceMovement {

	Bishop BLACK_BISHOP = new Bishop(BLACK)
	Bishop WHITE_BISHOP = new Bishop(WHITE)
	def static WHITE_PAWN =  new Pawn(WHITE)
	def static BLACK_PAWN =  new Pawn(BLACK)
	 
	@Test
	public void deveMoverOBispoDeC8ParaG5() {
		
			
			board.initializeWithInitialPosition() 
			
			board.movePiece(BLACK_PAWN, "D7","D6")
			board.movePiece(BLACK_BISHOP, "C8","G4")
			
			assertEquals " ___ ___ ___ ___ ___ ___ ___ ___ \n"+
			"|RB |NB*|   |QB*|KB |BB*|NB |RB*|\n"+
			"|___|___|___|___|___|___|___|___|\n"+
			"|PB*|PB |PB*|   |PB*|PB |PB*|PB |\n"+
			"|___|___|___|___|___|___|___|___|\n"+
			"|   |  *|   |PB*|   |  *|   |  *|\n"+
			"|___|___|___|___|___|___|___|___|\n"+
			"|  *|   |  *|   |  *|   |  *|   |\n"+
			"|___|___|___|___|___|___|___|___|\n"+
			"|   |  *|   |  *|   |  *|BB |  *|\n"+
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
	public void deveLancarUmErroSeOBispoDeC8ParaG5SeMoverComUmPeaoAFrenteDoSeuMovimento() {
		
			board.initializeWithInitialPosition()
			board.movePiece(BLACK_BISHOP, "C8","G4")
	}
	
	@Test
	public void deveMoverOBispoDeF1ParaD3() {
		
			
			board.initializeWithInitialPosition()
			
			board.movePiece(WHITE_PAWN, "E2","E4")
			board.movePiece(WHITE_BISHOP, "F1","D3")
			
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
			"|RW*|NW |BW*|QW |KW*|   |NW*|RW |\n"+
			"|___|___|___|___|___|___|___|___|",
			board.draw()
			
	}
	
	@Test(expected=MovimentoInvalidoException.class)
	void naoDevePermitirMovimentoInvalidoDoBispoBranco() {
		
			board.initializeWithInitialPosition()
			
			board.movePiece(WHITE_PAWN, "E2","E4")
			board.movePiece(WHITE_BISHOP, "F1","D4")
	}
	
	@Test(expected=MovimentoInvalidoException.class)
	void naoDevePermitirMoverOBispoBrancoParaUmaCasaQueUltrapassaOsLimitesDoTabuleiro() {
		
			board.initializeWithInitialPosition()
			
			board.movePiece(WHITE_PAWN, "E2","E4")
			board.movePiece(WHITE_BISHOP, "F1","I6")
	}
	
	
	@Test
	public void deveMoverOBispoParaTrasAteE2() {
		
			
			board.initializeWithInitialPosition()
			
			board.movePiece(WHITE_PAWN, "E2","E4")
			board.movePiece(WHITE_BISHOP, "F1","D3")
			board.movePiece(WHITE_BISHOP, "D3","E2")
			
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
			"|PW |PW*|PW |PW*|BW |PW*|PW |PW*|\n"+
			"|___|___|___|___|___|___|___|___|\n"+
			"|RW*|NW |BW*|QW |KW*|   |NW*|RW |\n"+
			"|___|___|___|___|___|___|___|___|",
			board.draw()
			
	}
	
	@Test
	public void deveMoverOBispoParaTrasAteH3() {
		
			
			board.initializeWithInitialPosition()
			
			board.movePiece(WHITE_PAWN, "G2","G3")
			board.movePiece(WHITE_BISHOP, "F1","H3")
			
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
			"|  *|   |  *|   |  *|   |PW*|BW |\n"+
			"|___|___|___|___|___|___|___|___|\n"+
			"|PW |PW*|PW |PW*|PW |PW*|   |PW*|\n"+
			"|___|___|___|___|___|___|___|___|\n"+
			"|RW*|NW |BW*|QW |KW*|   |NW*|RW |\n"+
			"|___|___|___|___|___|___|___|___|",
			board.draw()
			
	}
	
	@Test
	public void deveMoverOBispoPretoParaTrasAteB4() {
			
			board.initializeWithInitialPosition()
			
			board.movePiece(BLACK_PAWN, "E7","E5")
			board.movePiece(BLACK_BISHOP, "F8","A3")
			board.movePiece(BLACK_BISHOP, "A3","B4")
			
			assertEquals " ___ ___ ___ ___ ___ ___ ___ ___ \n"+
			"|RB |NB*|BB |QB*|KB |  *|NB |RB*|\n"+
			"|___|___|___|___|___|___|___|___|\n"+
			"|PB*|PB |PB*|PB |  *|PB |PB*|PB |\n"+
			"|___|___|___|___|___|___|___|___|\n"+
			"|   |  *|   |  *|   |  *|   |  *|\n"+
			"|___|___|___|___|___|___|___|___|\n"+
			"|  *|   |  *|   |PB*|   |  *|   |\n"+
			"|___|___|___|___|___|___|___|___|\n"+
			"|   |BB*|   |  *|   |  *|   |  *|\n"+
			"|___|___|___|___|___|___|___|___|\n"+
			"|  *|   |  *|   |  *|   |  *|   |\n"+
			"|___|___|___|___|___|___|___|___|\n"+
			"|PW |PW*|PW |PW*|PW |PW*|PW |PW*|\n"+
			"|___|___|___|___|___|___|___|___|\n"+
			"|RW*|NW |BW*|QW |KW*|BW |NW*|RW |\n"+
			"|___|___|___|___|___|___|___|___|",
			board.draw()
			
	}
	
	@Test
	public void oBispoDeveCapturaOPeaoPretoEmE7() {
			
			board.initializeWithInitialPosition()
			
			board.movePiece(WHITE_PAWN, "D2","D3")
			board.movePiece(WHITE_BISHOP, "C1","G5")
			board.movePiece(WHITE_BISHOP, "G5","E7")
			
			assertEquals " ___ ___ ___ ___ ___ ___ ___ ___ \n"+
			"|RB |NB*|BB |QB*|KB |BB*|NB |RB*|\n"+
			"|___|___|___|___|___|___|___|___|\n"+
			"|PB*|PB |PB*|PB |BW*|PB |PB*|PB |\n"+
			"|___|___|___|___|___|___|___|___|\n"+
			"|   |  *|   |  *|   |  *|   |  *|\n"+
			"|___|___|___|___|___|___|___|___|\n"+
			"|  *|   |  *|   |  *|   |  *|   |\n"+
			"|___|___|___|___|___|___|___|___|\n"+
			"|   |  *|   |  *|   |  *|   |  *|\n"+
			"|___|___|___|___|___|___|___|___|\n"+
			"|  *|   |  *|PW |  *|   |  *|   |\n"+
			"|___|___|___|___|___|___|___|___|\n"+
			"|PW |PW*|PW |  *|PW |PW*|PW |PW*|\n"+
			"|___|___|___|___|___|___|___|___|\n"+
			"|RW*|NW |  *|QW |KW*|BW |NW*|RW |\n"+
			"|___|___|___|___|___|___|___|___|",
			board.draw()
			
	}

}

