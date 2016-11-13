package meu.chess

import static meu.chess.Board.*
import static org.junit.Assert.*
import meu.chess.pieces.Bishop
import meu.chess.pieces.King
import meu.chess.pieces.Knight
import meu.chess.pieces.NullPiece;
import meu.chess.pieces.Pawn
import meu.chess.pieces.Queen
import meu.chess.pieces.Rock

import org.junit.Before
import org.junit.Test

class BoardTest {

	Board board
	def king
	def knight
	def torre
	def bispo
	def WHITE_PAWN
	def BLACK_PAWN
	def pieces = [:]
	@Before
	void setUp() {
			king = new King(BLACK)
			king.currentSquare =  new Square("F7")
		    knight = new Knight(WHITE)
			knight.currentSquare = new Square("E5")
			
			WHITE_PAWN = new Pawn(WHITE)
			BLACK_PAWN = new Pawn(BLACK)
			torre = new Rock(WHITE)
			torre.currentSquare =  new Square("A7")
			bispo = new Bishop(WHITE)
			bispo.currentSquare = new Square("H5")
			
			pieces = ["F7":king, "E5":knight]
			
			board = new Board()
			board.initialize(pieces)
	}
	
	@Test
	void verificaAPecaInimigaQueAtacaAPecaAdversaria() {
		
		def pieceAttacked = king
		
		def atacantes = board.getPiecesThatsAttackMe(pieceAttacked)
		
		assert atacantes[0]?.simbol == "NW" 
		assert atacantes[0]?.color == WHITE 
		
	}
	
	@Test
	void obtemAPecaInimigaRainhaECavaloQueAtacaAPecaAdversaria() {
		
		def queen = new Queen(WHITE)
		queen.currentSquare = new Square("F3")
		pieces.put("F3", queen)
		
		board.initialize(pieces)
		
		def pieceAttacked = king
		def atacantes = board.getPiecesThatsAttackMe(pieceAttacked)
		
		assert atacantes.size() == 2				
		assert atacantes[0]?.simbol == "NW"
		assert atacantes[1]?.simbol == "QW"
		
	}
	
	@Test
	void obtemAPecaInimigaTorreEBispoQueAtacaAPecaAdversaria() {
		
		def pieces = [:]
		pieces.put("F7", king)
		pieces.put("A7", torre)
		pieces.put("H5", bispo)
		
		board.initialize(pieces)
		
		def pieceAttacked = king
		def atacantes = board.getPiecesThatsAttackMe(pieceAttacked)
		
		assert atacantes.size() == 2
		assert atacantes[0]?.simbol == "BW"
		assert atacantes[1]?.simbol == "RW"
		
	}
	
	@Test
	void carregaOJogoInicialmente() {
		def partidaPgn =  "partida.pgn"
		board.loadGame(partidaPgn)
		
		assertEquals " ___ ___ ___ ___ ___ ___ ___ ___ \n"+
		"|RB |NB*|BB |QB*|KB |BB*|NB |RB*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PB*|PB |  *|   |PB*|PB |PB*|PB |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|   |  *|PB |  *|   |  *|   |  *|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|  *|BW |  *|PB |  *|   |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|   |  *|   |  *|PW |  *|   |  *|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|  *|   |  *|   |  *|   |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PW |PW*|PW |PW*|   |PW*|PW |PW*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|RW*|NW |BW*|QW |KW*|   |NW*|RW |\n"+
		"|___|___|___|___|___|___|___|___|",
		board.draw()
	}
	
	@Test
	void deveMoverOPequenoRockDasBrancas() {
		
		board.initializeWithInitialPosition()
		king = board.getKing(WHITE)
		bispo = board.getSquareBy("F1").content
		
		
		board.movePiece(WHITE_PAWN, "E2", "E4")
		board.movePiece(BLACK_PAWN, "E7", "E5")
		board.movePiece(bispo, "F1", "B5")
		board.movePiece(BLACK_PAWN, "F7", "F5")
		board.movePiece(new Knight(WHITE), "G1", "F3")
		board.movePiece(BLACK_PAWN, "F5", "F4")
		board.movePiece(king, "E1", "G1")
		
		assertEquals("G1",  board.getSquareBy("G1").cordinate)
		def kingCastle = board.getSquareBy("G1").content
		assertTrue(kingCastle.isAKing())
		//Tem que ser a casa da torre
		def rockCastle = board.getSquareBy("F1").content
		assertTrue(rockCastle.isARock())
		assertEquals("F1", rockCastle.currentSquare.cordinate)
		assert board.getSquareBy("H1").content instanceof NullPiece 
	}

	@Test
	void naoDevePermitirORockPequenoDasBrancasEnquantoHouverPecasEntreEles() {
		
		board.initializeWithInitialPosition()
		king = board.getKing(WHITE)
		bispo = board.getSquareBy("F1").content
		
		board.movePiece(WHITE_PAWN, "E2", "E4")
		board.movePiece(BLACK_PAWN, "E7", "E5")
		board.movePiece(new Knight(WHITE), "G1", "F3")
		board.movePiece(BLACK_PAWN, "F7", "F5")
		//HA um bispo em F1 entao nao deve permitir mover
		try {
			board.movePiece(king, "E1", "G1")
			fail()
		}catch(e) {
			assertEquals "Movimento da peça Rei de E1 inválido devido a presença do Bispo BW em F1.",e.message
		}
	}
	
	@Test
	void naoDevePermitirORockPequenoDasBrancasSeATorreNaoEstiverEmH1() {
		
		board.initializeWithInitialPosition()
		king = board.getKing(WHITE)
		bispo = board.getSquareBy("F1").content
		
		board.movePiece(WHITE_PAWN, "E2", "E4")
		board.movePiece(BLACK_PAWN, "G7", "G5")
		board.movePiece(WHITE_PAWN, "H2", "H4")
		board.movePiece(BLACK_PAWN, "E7", "E5")
		board.movePiece(new Knight(WHITE), "G1", "F3")
		board.movePiece(BLACK_PAWN, "F7", "F5")
		//Moveu a torre rock invalido
		board.movePiece(new Rock(WHITE), "H1", "H3")
		board.movePiece(BLACK_PAWN, "F5", "F4")
		board.movePiece(bispo, "F1", "B5")
		board.movePiece(BLACK_PAWN, "A7", "A5")
		//HA um bispo em F1 entao nao deve permitir mover
		try {
			board.movePiece(king, "E1", "G1")
			fail()
		}catch(e) {
			assertEquals "Não é possível realizar o O-O das brancas pois a torre de H1 não está presente.",e.message
		}
	}
	
	@Test
	void naoDevePermitirORockPequenoDasBrancasSeATorreJaEstiverMovido() {
		board.initializeWithInitialPosition()
		king = board.getKing(WHITE)
		bispo = board.getSquareBy("F1").content
		
		board.movePiece(WHITE_PAWN, "E2", "E4")
		board.movePiece(BLACK_PAWN, "G7", "G5")
		board.movePiece(WHITE_PAWN, "H2", "H4")
		board.movePiece(BLACK_PAWN, "E7", "E5")
		board.movePiece(new Knight(WHITE), "G1", "F3")
		board.movePiece(BLACK_PAWN, "F7", "F5")
		//Moveu a torre rock invalido
		board.movePiece(new Rock(WHITE), "H1", "H3")
		board.movePiece(BLACK_PAWN, "F5", "F4")
		board.movePiece(bispo, "F1", "B5")
		board.movePiece(BLACK_PAWN, "A7", "A5")
		board.movePiece(new Rock(WHITE), "H3", "H1")
		board.movePiece(BLACK_PAWN, "A5", "A4")
		try {
			board.movePiece(king, "E1", "G1")
			fail()
		}catch(e) {
			assertEquals "Não é possível realizar o O-O das brancas pois a torre de H1 já realizou um movimento.",e.message
		}
	}
	
	@Test
	void deveMoverORockGrandeDasBrancas() {
		
		board.initializeWithInitialPosition()
		king = board.getKing(WHITE)
		bispo = board.getSquareBy("F1").content
		board.movePiece(WHITE_PAWN, "D2", "D4")
		board.movePiece(BLACK_PAWN, "D7", "D5")
		board.movePiece(bispo, "C1", "G5")
		board.movePiece(BLACK_PAWN, "F7", "F5")
		board.movePiece(new Knight(WHITE), "B1", "C3")
		board.movePiece(BLACK_PAWN, "F5", "F4")
		board.movePiece(new Queen(WHITE), "D1", "D3")
		board.movePiece(BLACK_PAWN, "A7", "A5")
		board.movePiece(king, "E1", "C1")
		
		def rockCastle = board.getSquareBy("D1").content
		assertEquals("C1", board.getKing(WHITE).currentSquare.cordinate)
		assertTrue(rockCastle.isARock())//Tem que ser a casa da torre
		assertEquals("D1", rockCastle.currentSquare.cordinate)
		assertTrue( board.getSquareBy("A1").isEmpty())
	}
	
	@Test
	void deveMoverORockPequenoDasNegras() {
		
		board.initializeWithInitialPosition()
		king = board.getKing(BLACK)
		
		board.movePiece(WHITE_PAWN, "E2", "E4")
		board.movePiece(BLACK_PAWN, "E7", "E5")
		board.movePiece(WHITE_PAWN, "F2", "F4")
		board.movePiece(new Bishop(BLACK), "F8", "D6")
		board.movePiece(WHITE_PAWN, "F4", "F5")
		board.movePiece(new Knight(BLACK), "G8", "F6")
		board.movePiece(new Queen(WHITE), "D1", "E2")
		board.movePiece(king, "E8", "G8")
		
		def rockCastle = board.getSquareBy("F8").content
		assertEquals("G8", board.getKing(BLACK).currentSquare.cordinate)
		assertTrue(rockCastle.isARock())//Tem que ser a casa da torre
		assertEquals("F8", rockCastle.currentSquare.cordinate)
		assertTrue( board.getSquareBy("H8").isEmpty())
	}
	
	@Test
	void deveMoverORockGrandeDasNegras() {
		
		board.initializeWithInitialPosition()
		
		king = board.getKing(BLACK)
		board.movePiece(WHITE_PAWN, "D2", "D4")
		board.movePiece(BLACK_PAWN, "D7", "D5")
		board.movePiece(WHITE_PAWN, "F2", "F4")
		board.movePiece(new Bishop(BLACK), "C8", "E6")
		board.movePiece(WHITE_PAWN, "F4", "F5")
		board.movePiece(new Knight(BLACK), "B8", "C6")
		board.movePiece(WHITE_PAWN, "A2", "A4")
		board.movePiece(new Queen(BLACK), "D8", "D6")
		board.movePiece(WHITE_PAWN, "A4", "A5")
		board.movePiece(king, "E8", "B8")
		
		def rockCastle = board.getSquareBy("D8").content
		assertEquals("C8", board.getKing(BLACK).currentSquare.cordinate)
		assertTrue(rockCastle.isARock())//Tem que ser a casa da torre
		assertEquals("D8", rockCastle.currentSquare.cordinate)
		assertTrue( board.getSquareBy("A8").isEmpty())
	}
	
	@Test
	void erroAoMoverORockGrandeDasNegrasDevidoASquareC8EstarEmXeque() {
		
		board.initializeWithInitialPosition()
		
		king = board.getKing(BLACK)
		board.movePiece(WHITE_PAWN, "D2", "D4")
		board.movePiece(BLACK_PAWN, "D7", "D5")
		board.movePiece(WHITE_PAWN, "E2", "E4")
		board.movePiece(new Queen(BLACK), "D8", "D6")
		board.movePiece(WHITE_PAWN, "G2", "G3")
		board.movePiece(new Bishop(BLACK), "C8", "G4")
		board.movePiece(new Queen(WHITE), "D1", "G4")
		board.movePiece(new Knight(BLACK), "B8", "C6")
		board.movePiece(WHITE_PAWN, "A2", "A3")
		
		try {
			board.movePiece(king, "E8", "B8")
			fail("falhou")
		}catch(e) {
			assertEquals("O-O-O", e.message) 	
		}
		
	}
}
