package meu.chess

import static meu.chess.Board.*
import static org.junit.Assert.*;

import java.awt.event.KeyEvent;

import meu.chess.pieces.Knight
import meu.chess.pieces.Pawn
import meu.chess.pieces.Piece;

import org.junit.Before;
import org.junit.Test;



class PrintBoardTest   {
	
	Board board
	
	@Before
	void before() {
		board = new Board()
	}

    @Test
    void deveImprimirOTabuleiroCom8Por8CasasSemPecas() {
        
		board.initialize(null)
		
        assertEquals " ___ ___ ___ ___ ___ ___ ___ ___ \n"+
					 "|   |  *|   |  *|   |  *|   |  *|\n"+
					 "|___|___|___|___|___|___|___|___|\n"+
					 "|  *|   |  *|   |  *|   |  *|   |\n"+
					 "|___|___|___|___|___|___|___|___|\n"+
					 "|   |  *|   |  *|   |  *|   |  *|\n"+
					 "|___|___|___|___|___|___|___|___|\n"+
					 "|  *|   |  *|   |  *|   |  *|   |\n"+
					 "|___|___|___|___|___|___|___|___|\n"+
					 "|   |  *|   |  *|   |  *|   |  *|\n"+
					 "|___|___|___|___|___|___|___|___|\n"+
					 "|  *|   |  *|   |  *|   |  *|   |\n"+
					 "|___|___|___|___|___|___|___|___|\n"+
					 "|   |  *|   |  *|   |  *|   |  *|\n"+
					 "|___|___|___|___|___|___|___|___|\n"+
					 "|  *|   |  *|   |  *|   |  *|   |\n"+
					 "|___|___|___|___|___|___|___|___|",
					 board.draw()
    }

	@Test
	void deveImprimirUmaTorreNegraNoTabuleiroNaCasaA8() {
		def pieces = [:] 
		pieces.put("A8", new Piece("RB"))
			
		board.initialize(pieces)
		
		assertEquals " ___ ___ ___ ___ ___ ___ ___ ___ \n"+
					 "|RB |  *|   |  *|   |  *|   |  *|\n"+
					 "|___|___|___|___|___|___|___|___|\n"+
					 "|  *|   |  *|   |  *|   |  *|   |\n"+
					 "|___|___|___|___|___|___|___|___|\n"+
					 "|   |  *|   |  *|   |  *|   |  *|\n"+
					 "|___|___|___|___|___|___|___|___|\n"+
					 "|  *|   |  *|   |  *|   |  *|   |\n"+
					 "|___|___|___|___|___|___|___|___|\n"+
					 "|   |  *|   |  *|   |  *|   |  *|\n"+
					 "|___|___|___|___|___|___|___|___|\n"+
					 "|  *|   |  *|   |  *|   |  *|   |\n"+
					 "|___|___|___|___|___|___|___|___|\n"+
					 "|   |  *|   |  *|   |  *|   |  *|\n"+
					 "|___|___|___|___|___|___|___|___|\n"+
					 "|  *|   |  *|   |  *|   |  *|   |\n"+
					 "|___|___|___|___|___|___|___|___|", 
					 board.draw()
					 
					 
	}	
	
	@Test
	void deveImprimirReiBrancoNaCasaE1EUmaTorreNaCasaA8() {

		def pieces = ["A8":new Piece("RB"), "E1":new Piece("KW")]
			
		board.initialize(pieces)
		
		
		assertEquals " ___ ___ ___ ___ ___ ___ ___ ___ \n"+
					 "|RB |  *|   |  *|   |  *|   |  *|\n"+
					 "|___|___|___|___|___|___|___|___|\n"+
					 "|  *|   |  *|   |  *|   |  *|   |\n"+
					 "|___|___|___|___|___|___|___|___|\n"+
					 "|   |  *|   |  *|   |  *|   |  *|\n"+
					 "|___|___|___|___|___|___|___|___|\n"+
					 "|  *|   |  *|   |  *|   |  *|   |\n"+
					 "|___|___|___|___|___|___|___|___|\n"+
					 "|   |  *|   |  *|   |  *|   |  *|\n"+
					 "|___|___|___|___|___|___|___|___|\n"+
					 "|  *|   |  *|   |  *|   |  *|   |\n"+
					 "|___|___|___|___|___|___|___|___|\n"+
					 "|   |  *|   |  *|   |  *|   |  *|\n"+
					 "|___|___|___|___|___|___|___|___|\n"+
					 "|  *|   |  *|   |KW*|   |  *|   |\n"+
					 "|___|___|___|___|___|___|___|___|",
					 board.draw()
	}
	
	@Test
	void deveImprimirOTabuleiroComTodasAPecas() {
		
		board.initializeWithInitialPosition()
	
	
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
				 "|  *|   |  *|   |  *|   |  *|   |\n"+
				 "|___|___|___|___|___|___|___|___|\n"+
				 "|PW |PW*|PW |PW*|PW |PW*|PW |PW*|\n"+
				 "|___|___|___|___|___|___|___|___|\n"+
				 "|RW*|NW |BW*|QW |KW*|BW |NW*|RW |\n"+
				 "|___|___|___|___|___|___|___|___|",
				 board.draw()
				 
	}
	
	@Test
	void deveImprimirComOCursorNaPrimeiraCasa() {
		
		board.setApplication(true)
		board.initializeWithInitialPosition()
	
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
				 "|  *|   |  *|   |  *|   |  *|   |\n"+
				 "|___|___|___|___|___|___|___|___|\n"+
				 "|PW |PW*|PW |PW*|PW |PW*|PW |PW*|\n"+
				 "|___|___|___|___|___|___|___|___|\n"+
				 "|RW*|NW |BW*|QW |KW*|BW |NW*|RW |\n"+
				 "|[_]|___|___|___|___|___|___|___|",
				 board.draw()
				 
	}
	
	@Test
	void deveImprimirComOCursorParaCasaB1ParaDireita() {
		
		board.setApplication(true)
		board.initializeWithInitialPosition()
	
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
				 "|  *|   |  *|   |  *|   |  *|   |\n"+
				 "|___|___|___|___|___|___|___|___|\n"+
				 "|PW |PW*|PW |PW*|PW |PW*|PW |PW*|\n"+
				 "|___|___|___|___|___|___|___|___|\n"+
				 "|RW*|NW |BW*|QW |KW*|BW |NW*|RW |\n"+
				 "|___|[_]|___|___|___|___|___|___|",
				 board.draw(KeyEvent.VK_RIGHT)
				 
	}
	
	@Test
	void deveImprimirComOCursorParaCasaA1ParaEsquerda() {
		
		board.setApplication(true)
		board.initializeWithInitialPosition()
	
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
				 "|  *|   |  *|   |  *|   |  *|   |\n"+
				 "|___|___|___|___|___|___|___|___|\n"+
				 "|PW |PW*|PW |PW*|PW |PW*|PW |PW*|\n"+
				 "|___|___|___|___|___|___|___|___|\n"+
				 "|RW*|NW |BW*|QW |KW*|BW |NW*|RW |\n"+
				 "|[_]|___|___|___|___|___|___|___|",
				 board.draw(KeyEvent.VK_LEFT)
				 
	}
	
	@Test
	void deveMoverOCursorDaCasaE1ParaD1() {
		
		board.setApplication(true)
		board.initializeWithInitialPosition()
		board.cursorCordinate = "E1"
	
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
				 "|  *|   |  *|   |  *|   |  *|   |\n"+
				 "|___|___|___|___|___|___|___|___|\n"+
				 "|PW |PW*|PW |PW*|PW |PW*|PW |PW*|\n"+
				 "|___|___|___|___|___|___|___|___|\n"+
				 "|RW*|NW |BW*|QW |KW*|BW |NW*|RW |\n"+
				 "|___|___|___|[_]|___|___|___|___|",
				 board.draw(KeyEvent.VK_LEFT)
				 
	}
	
	@Test
	void deveMoverOCursorDaCasaE1ParaE2() {
		
		board.setApplication(true)
		board.initializeWithInitialPosition()
		board.cursorCordinate = "E1"
	
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
				 "|  *|   |  *|   |  *|   |  *|   |\n"+
				 "|___|___|___|___|___|___|___|___|\n"+
				 "|PW |PW*|PW |PW*|PW |PW*|PW |PW*|\n"+
				 "|___|___|___|___|[_]|___|___|___|\n"+
				 "|RW*|NW |BW*|QW |KW*|BW |NW*|RW |\n"+
				 "|___|___|___|___|___|___|___|___|",
				 board.draw(KeyEvent.VK_UP)
				 
	}
	
	@Test
	void deveMoverOCursorDaCasaE8ParaE7() {
		
		board.setApplication(true)
		board.initializeWithInitialPosition()
		board.cursorCordinate = "E8"
	
		assertEquals " ___ ___ ___ ___ ___ ___ ___ ___ \n"+
				 "|RB |NB*|BB |QB*|KB |BB*|NB |RB*|\n"+
				 "|___|___|___|___|___|___|___|___|\n"+
				 "|PB*|PB |PB*|PB |PB*|PB |PB*|PB |\n"+
				 "|___|___|___|___|[_]|___|___|___|\n"+
				 "|   |  *|   |  *|   |  *|   |  *|\n"+
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
				 board.draw(KeyEvent.VK_DOWN)
				 
	}
	
	@Test
	void deveMoverOCursorDaCasaE8ParaE4() {
		
		board.setApplication(true)
		board.initializeWithInitialPosition()
		board.cursorCordinate = "E8"
	
		board.draw(KeyEvent.VK_DOWN)
		board.draw(KeyEvent.VK_DOWN)
		board.draw(KeyEvent.VK_DOWN)
		board.draw(KeyEvent.VK_DOWN)
		
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
				 "|___|___|___|___|[_]|___|___|___|\n"+
				 "|  *|   |  *|   |  *|   |  *|   |\n"+
				 "|___|___|___|___|___|___|___|___|\n"+
				 "|PW |PW*|PW |PW*|PW |PW*|PW |PW*|\n"+
				 "|___|___|___|___|___|___|___|___|\n"+
				 "|RW*|NW |BW*|QW |KW*|BW |NW*|RW |\n"+
				 "|___|___|___|___|___|___|___|___|",
				 board.draw()
				 
	}
	
	@Test
	void deveMoverOPeaoDeE2ParaE4AtraveDoCursor() {
		
		board.setApplication(true)
		board.initializeWithInitialPosition()
		
		board.draw(KeyEvent.VK_RIGHT)
		board.draw(KeyEvent.VK_RIGHT)
		board.draw(KeyEvent.VK_RIGHT)
		board.draw(KeyEvent.VK_RIGHT)
		board.draw(KeyEvent.VK_UP)
		board.draw(KeyEvent.VK_SPACE)
		board.draw(KeyEvent.VK_UP)
		board.draw(KeyEvent.VK_UP)
		board.draw(KeyEvent.VK_SPACE)
		
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
				 "|___|___|___|___|[_]|___|___|___|\n"+
				 "|  *|   |  *|   |  *|   |  *|   |\n"+
				 "|___|___|___|___|___|___|___|___|\n"+
				 "|PW |PW*|PW |PW*|   |PW*|PW |PW*|\n"+
				 "|___|___|___|___|___|___|___|___|\n"+
				 "|RW*|NW |BW*|QW |KW*|BW |NW*|RW |\n"+
				 "|___|___|___|___|___|___|___|___|",
				 board.draw()
				 
	}
	
	@Test
	void deveMoverOCavadoDeF1ParaG3AtraveDoCursor() {
		
		board.setApplication(true)
		board.initializeWithInitialPosition()
		
		board.draw(KeyEvent.VK_RIGHT)
		board.draw(KeyEvent.VK_RIGHT)
		board.draw(KeyEvent.VK_RIGHT)
		board.draw(KeyEvent.VK_RIGHT)
		board.draw(KeyEvent.VK_RIGHT)
		board.draw(KeyEvent.VK_RIGHT)
		board.draw(KeyEvent.VK_SPACE)
		board.draw(KeyEvent.VK_UP)
		board.draw(KeyEvent.VK_UP)
		board.draw(KeyEvent.VK_LEFT)
		board.draw(KeyEvent.VK_SPACE)
		
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
				 "|___|___|___|___|___|[_]|___|___|\n"+
				 "|PW |PW*|PW |PW*|PW |PW*|PW |PW*|\n"+
				 "|___|___|___|___|___|___|___|___|\n"+
				 "|RW*|NW |BW*|QW |KW*|BW |  *|RW |\n"+
				 "|___|___|___|___|___|___|___|___|",
				 board.draw()
	}
	
	@Test
	void deveIgnorarOMovimentoSeACasaDeDestinoForAMesmDeOrigem() {
		
		board.setApplication(true)
		board.initializeWithInitialPosition()
		
		board.draw(KeyEvent.VK_SPACE)
		board.draw(KeyEvent.VK_SPACE)
		
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
				 "|  *|   |  *|   |  *|   |  *|   |\n"+
				 "|___|___|___|___|___|___|___|___|\n"+
				 "|PW |PW*|PW |PW*|PW |PW*|PW |PW*|\n"+
				 "|___|___|___|___|___|___|___|___|\n"+
				 "|RW*|NW |BW*|QW |KW*|BW |NW*|RW |\n"+
				 "|[_]|___|___|___|___|___|___|___|",
				 board.draw()
	}
	
	@Test
	void aCasaEntreB4EF8FormaUmaDiagonal() {
		assertTrue(board.isDiagonal("B4", "F8")) 
	}
	
	@Test
	void aCasaEntreB4EC6naoFormaUmaDiagonal() {
		assertFalse(board.isDiagonal("B4", "C6"))
	}
	
	@Test
	void aCasaEntreB4EA3FormaUmaDiagonal() {
		assertTrue(board.isDiagonal("B4", "A3"))
	}
	
	@Test
	void aCasaEntreB4EH4FormaUmaHorizontal() {
		assertTrue(board.isHorizontal("B4", "H4"))
	}
	
	@Test
	void aCasaEntreB4EH5NaoFormaUmaHorizontal() {
		assertFalse(board.isHorizontal("B4", "H5"))
	}
	
	@Test
	void aCasaEntreC8EC1FormaUmVertical() {
		assertTrue(board.isVertical("C8", "C1"))
	}
	
	@Test
	void aCasaEntreC8EB1NaoFormaUmVertical() {
		assertFalse(board.isVertical("C8", "B1"))
	}
	
	@Test
	void deveRetornarAsCasasDaDiagonalB4AteF8() {
		
		board.initializeWithInitialPosition()
		
		def diagonal = board.getDiagonal("B4", "F8")
		assertEquals "  ", diagonal[0].content.toString()  
		assertEquals "  ", diagonal[1].content.toString()
		assertEquals "  ", diagonal[2].content.toString()
		assertEquals "PB", diagonal[3].content.toString()
		assertEquals "BB", diagonal[4].content.toString()
	}
	
	@Test
	void deveRetornarAsPossiveisDiagonaisDaCasaE5() {
		
		board.initializeWithInitialPosition()
		def diagonals = board.getDiagonals("E5")
		
		assert 2 == diagonals.size()
		assert "A1" == diagonals[1].diagonal[0].cordinate 
		assert "B2" == diagonals[1].diagonal[1].cordinate
		assert "C3" == diagonals[1].diagonal[2].cordinate
		assert "D4" == diagonals[1].diagonal[3].cordinate
		assert "E5" == diagonals[1].diagonal[4].cordinate
		assert "F6" == diagonals[1].diagonal[5].cordinate
		assert "G7" == diagonals[1].diagonal[6].cordinate
		assert "H8" == diagonals[1].diagonal[7].cordinate
		
		assert "B8" == diagonals[2].diagonal[0].cordinate
		assert "C7" == diagonals[2].diagonal[1].cordinate
		assert "D6" == diagonals[2].diagonal[2].cordinate
		assert "E5" == diagonals[2].diagonal[3].cordinate
		assert "F4" == diagonals[2].diagonal[4].cordinate
		assert "G3" == diagonals[2].diagonal[5].cordinate
		assert "H2" == diagonals[2].diagonal[6].cordinate
		
	}
	
	@Test
	void deveRetornarAsPossiveisDiagonaisDaCasaE5DividindoAPartirDeE5() {
		
		board.initializeWithInitialPosition()
		def diagonals = board.getDiagonals("E5")
		
		assert 2 == diagonals.size()
		assert ["D4","C3", "B2", "A1"] == diagonals[1].cordinatesFromSource()[1]
		assert ["F6","G7", "H8"] == diagonals[1].cordinatesFromSource()[2]
		
		assert ["D6","C7", "B8"] == diagonals[2].cordinatesFromSource()[1]
		assert ["F4","G3", "H2"] == diagonals[2].cordinatesFromSource()[2]
		
		
	}
	
	@Test
	void deveRetornarAsPossiveisDiagonaisDaCasaE8DividindoAsPartirDeE8() {
		
		board.initializeWithInitialPosition()
		def diagonals = board.getDiagonals("E8")
		
		assert 2 == diagonals[1].cordinatesFromSource().size()
		assert ["D7","C6", "B5", "A4"] == diagonals[1].cordinatesFromSource()[1]
		assert ["F7","G6", "H5"] == diagonals[1].cordinatesFromSource()[2]
		
		
	}
	
	@Test
	void deveRetornarTrueSeACordenateExistirNoTabuleiro() {
		board.initializeWithInitialPosition()
		assert true == board.hasThisSquare("A8")
	}
	
	@Test
	void deveRetornarFalseSeACordenateNaoExistirNoTabuleiro() {
		board.initializeWithInitialPosition()
		assert false == board.hasThisSquare("A9")	
	}
	
	@Test
	void deveRetornarAsCasasDaDiagonalA4AteC6() {
		
		board.initializeWithInitialPosition()
		board.movePiece(new Knight(BLACK), "B8", "C6")
		
		def diagonal = board.getDiagonal("A4", "C6")
		assertEquals "  ", diagonal[0].content.toString()
		assertEquals "  ", diagonal[1].content.toString()
		assertEquals "NB", diagonal[2].content.toString()
	}
	
	@Test
	void deveRetornarAsCasasDaHorizontalA1A4() {
		board.initializeWithInitialPosition()
		
		def horizontal = board.getHorizontal("A1","D1") 
		assertEquals "RW", horizontal[0].content.toString()
		assertEquals "NW", horizontal[1].content.toString()
		assertEquals "BW", horizontal[2].content.toString()
		assertEquals "QW", horizontal[3].content.toString()
	}
	
	@Test
	void deveRetornarAsCasasDaHorizontalH7H5() {
		board.initializeWithInitialPosition()
		board.movePiece(new Pawn(BLACK), "H7", "H5")
		
		def horizontal = board.getHorizontal("H7","F7") 
		assertEquals "  ", horizontal[0].content.toString()
		assertEquals "PB", horizontal[1].content.toString()
		assertEquals "PB", horizontal[2].content.toString()
	}
	
	@Test
	void deveRetornarAsCasasDaVertiacalH1H3() {
		board.initializeWithInitialPosition()
		
		def vertical = board.getVertical("H1", "H3")
		assertEquals "RW", vertical[0].content.toString()
		assertEquals "PW", vertical[1].content.toString()
		assertEquals "  ", vertical[2].content.toString()
	}
	
	@Test
	void ehUmMovimentoEmLDeB1C3() {
	 	assertTrue board.isLShape("B1", "C3")
	}
	
	@Test
	void ehUmMovimentoEmLDeE5C6() {
		 assertTrue board.isLShape("E5", "C6")
	}
	
	@Test
	void naoEhUmMovimentoEmLDeB1C4() {
		 assertFalse board.isLShape("B1", "C4")
	}
	
	@Test
	void deveRetornarOMovimentoEmLDeB8() {
		board.initializeWithInitialPosition()
		
		def LSquares = board.getLShape("B8")
		assertEquals  "D7", LSquares[0].cordinate
		assertEquals  "C6", LSquares[1].cordinate
		assertEquals  "A6", LSquares[2].cordinate
		assertEquals 3, LSquares.size()
	}
	
	@Test
	void deveRetornarOMovimentoEmLDeC6() {
		board.initializeWithInitialPosition()
		
		def LSquares = board.getLShape("C6")
		assertEquals  "E5", LSquares[0].cordinate
		assertEquals  "A5", LSquares[1].cordinate
		assertEquals  "D4", LSquares[2].cordinate
		assertEquals  "B4", LSquares[3].cordinate
		assertEquals  "D8", LSquares[4].cordinate
		assertEquals  "B8", LSquares[5].cordinate
		assertEquals  "E7", LSquares[6].cordinate
		assertEquals  "A7", LSquares[7].cordinate
		assertEquals 8, LSquares.size()
	}
	
	@Test
	void deveRetornarOMovimentoEmLDeA8() {
		board.initializeWithInitialPosition()
		
		def LSquares = board.getLShape("A8")
		assertEquals  "C7", LSquares[0].cordinate
		assertEquals  "B6", LSquares[1].cordinate
		assertEquals 2, LSquares.size()
	}
	
	@Test
	void deveRetornarOMovimentoEmLDeH5() {
		board.initializeWithInitialPosition()
		
		def LSquares = board.getLShape("H5")
		assertEquals  "F4", LSquares[0].cordinate
		assertEquals  "G3", LSquares[1].cordinate
		assertEquals  "G7", LSquares[2].cordinate
		assertEquals  "F6", LSquares[3].cordinate
		assertEquals 4, LSquares.size()
	}
}