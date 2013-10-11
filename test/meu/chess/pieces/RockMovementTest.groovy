package meu.chess.pieces;

import static meu.chess.Board.*
import static org.junit.Assert.*;
import meu.chess.MovimentoInvalidoException;

import org.junit.Test;

class RockMovementTest extends TestPieceMovement {


	def WHITE_PAWN = new Pawn(WHITE)
	def BLACK_PAWN = new Pawn(BLACK) 
	
	def WHITE_ROCK = new Rock(WHITE)
	def BLACK_ROCK = new Rock(BLACK)
	
	
	@Test(expected=MovimentoInvalidoException.class) 
	public void naoDevePermitirOMovimentoDaTorreSeHouverUmPeaoAFrente() {

		board.initializeWithInitialPosition( )
		board.movePiece(WHITE_ROCK, "A1", "A3")
		 
	}
	
	@Test(expected=MovimentoInvalidoException.class)
	public void naoDevePermitirATorreBrancaEmB1DevidoAPresencaDoCavalo() {

		board.initializeWithInitialPosition( )
		board.movePiece(WHITE_ROCK, "A1", "B1")
		 
	}

	@Test
	public void deveMoverATorreBrancaParaA3() {
		
		board.initializeWithInitialPosition( )
		board.movePiece(WHITE_PAWN, "A2", "A4")
		board.movePiece(WHITE_ROCK, "A1", "A3")
		
		assertEquals " ___ ___ ___ ___ ___ ___ ___ ___ \n"+
		"|RB |NB*|BB |QB*|KB |BB*|NB |RB*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PB*|PB |PB*|PB |PB*|PB |PB*|PB |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|   |  *|   |  *|   |  *|   |  *|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|  *|   |  *|   |  *|   |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PW |  *|   |  *|   |  *|   |  *|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|RW*|   |  *|   |  *|   |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|   |PW*|PW |PW*|PW |PW*|PW |PW*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|  *|NW |BW*|QW |KW*|BW |NW*|RW |\n"+
		"|___|___|___|___|___|___|___|___|",
		board.draw()
	}
	
	@Test
	public void aTorreNegraDeveCapturaOPeaoDeB2() {
		
		board.initializeWithInitialPosition( )
		board.movePiece(BLACK_PAWN, "A7", "A5")
		board.movePiece(BLACK_ROCK, "A8", "A6")
		board.movePiece(BLACK_ROCK, "A6", "B6")
		board.movePiece(BLACK_ROCK, "B6", "B2")
		
		assertEquals " ___ ___ ___ ___ ___ ___ ___ ___ \n"+
		"|   |NB*|BB |QB*|KB |BB*|NB |RB*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|  *|PB |PB*|PB |PB*|PB |PB*|PB |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|   |  *|   |  *|   |  *|   |  *|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PB*|   |  *|   |  *|   |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|   |  *|   |  *|   |  *|   |  *|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|  *|   |  *|   |  *|   |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PW |RB*|PW |PW*|PW |PW*|PW |PW*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|RW*|NW |BW*|QW |KW*|BW |NW*|RW |\n"+
		"|___|___|___|___|___|___|___|___|",
		board.draw()
	}
	
	@Test
	public void aTorreNegraDeveMoverParaB8() {
		
		board.initializeWithInitialPosition( )
		board.movePiece(new Knight(BLACK), "B8", "C6")
		board.movePiece(BLACK_ROCK, "A8", "B8")
		
		assertEquals " ___ ___ ___ ___ ___ ___ ___ ___ \n"+
		"|   |RB*|BB |QB*|KB |BB*|NB |RB*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PB*|PB |PB*|PB |PB*|PB |PB*|PB |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|   |  *|NB |  *|   |  *|   |  *|\n"+
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
}
