package meu.chess;

import static meu.chess.utils.ConvertUtil.*
import static Board.*
import static org.junit.Assert.*;

import meu.chess.pieces.Bishop
import meu.chess.pieces.King
import meu.chess.pieces.Knight
import meu.chess.pieces.Pawn
import meu.chess.pieces.Queen
import org.junit.Before;
import org.junit.Test;

class GeneralChessRulesTest {

	def static BLACK_PAWN = new Pawn(BLACK)
	def static WHITE_PAWN = new Pawn(WHITE)
	Board board
	
	@Before
	void before() {
		board = new Board()
	}

	
	@Test
	public void naoDevePermitirMovimentarOPeaoEmF2SeOReiEstiverEmXeque() {
			  
		board.initializeWithInitialPosition()
		try {
			 
			board.movePiece(WHITE_PAWN, "E2", "E4")
			board.movePiece(BLACK_PAWN, "D7", "D5")
			board.movePiece(WHITE_PAWN, "E4", "D5")
			board.movePiece(new Queen(BLACK), "D8", "D5")
			board.movePiece(WHITE_PAWN, "A2", "A4")
			board.movePiece(new Queen(BLACK), "D5", "E5")
			
			board.movePiece(WHITE_PAWN, "F2", "F4")
			fail()
		} catch (Exception e) {
			assertEquals "Não é possível mover a peça Peão (PW) de F2 para F4 enquanto o rei em E1 se encontra em xeque.", e.message
		}
		
	}

	@Test
	public void naoDevePermitirMovimentarOPeaoEmF6SeOReiEstiverEmXeque() {
			  
		board.initializeWithInitialPosition()
		try {
			
			board.movePiece(WHITE_PAWN, "E2", "E4")
			board.movePiece(BLACK_PAWN, "D7", "D5")
			board.movePiece(WHITE_PAWN, "E4", "D5")
			board.movePiece(BLACK_PAWN, "H7", "H5")
			board.movePiece(new Queen(WHITE), "D1", "E2")
			
			board.movePiece(BLACK_PAWN, "F7", "F5")
			fail()
		} catch (Exception e) {
			assertEquals "Não é possível mover a peça Peão (PB) de F7 para F5 enquanto o rei em E8 se encontra em xeque.", e.message
		}
	}
	
	@Test
	public void naoDeveLancarUmErroSeOReiSeMoverParaForaDoXeque() {
			  
		board.initializeWithInitialPosition()
		try {
			
			board.movePiece(WHITE_PAWN, "E2", "E4")
			board.movePiece(BLACK_PAWN, "D7", "D5")
			board.movePiece(WHITE_PAWN, "E4", "D5")
			board.movePiece(BLACK_PAWN, "F7", "F5")
			board.movePiece(new Queen(WHITE), "D1", "E2")
			
			board.movePiece(new King(BLACK), "E8", "F7")
			assertTrue("funcionou", true)
		} catch (Exception e) {
			fail()
		}
	}
	
	@Test
	public void naoDevePermitirOReiSeMoverParaUmaCasaEmXeque() {
			  
		board.initializeWithInitialPosition()
		try {
			
			board.movePiece(WHITE_PAWN, "E2", "E4")
			board.movePiece(BLACK_PAWN, "D7", "D5")
			board.movePiece(WHITE_PAWN, "E4", "D5")
			board.movePiece(BLACK_PAWN, "F7", "F5")
			board.movePiece(new Queen(WHITE), "D1", "E2")
			board.movePiece(new Knight(WHITE), "G1", "F3")
			board.movePiece(new Knight(WHITE), "F3", "G5")
			
			board.movePiece(new King(BLACK), "E8", "F7")
			fail()
		} catch (Exception e) {
			assert "Não é possível mover o rei para a casa F7 pois ela está em xeque." == e.message
		}
	}
	
	@Test
	void naoDevePermitirUmaPecaNegraSeMoverSeOBispoBrancoEstiverAtacandoOReiNegro() {
		
		board.initializeWithInitialPosition()
		try {
			board.movePiece(WHITE_PAWN, "E2", "E4")
			board.movePiece(BLACK_PAWN, "E7", "E5")
			board.movePiece(new Bishop(WHITE), "F1", "C4")
			board.movePiece(new Bishop(WHITE), "C4", "F7")
			board.movePiece(BLACK_PAWN, "A7", "A5")
			fail()
		} catch (Exception e) {
			assert "Rei em xeque", e.message
		}
	}
	
	@Test
	void naoDevePermitirMoverOCavaloDeB8SeOReiEstiverEmXeque() {
		
		board.initializeWithInitialPosition()
		
		
		try {
			
			board.movePiece(WHITE_PAWN, "E2", "E4")
			board.movePiece(BLACK_PAWN, "E7", "E5")
			board.movePiece(new Queen(WHITE), "D1", "H5")
			board.movePiece(BLACK_PAWN, "A7", "A6")
			board.movePiece(new Queen(WHITE), "H5", "F7")//Xeque
			board.movePiece(new Knight(BLACK), "B8", "C6")
			
			fail()
		} catch (Exception e) {
			assert "Não é possível mover a peça Cavalo NB de B8 para C6 enquanto o rei em E8 se encontra em xeque." == e.message
		}
	}
	
	@Test
	void naoDeveConsiderarXequeVerticalSeHouverUmaPecaDaPropriaCorEntreAPecaAtacanteEORei() {
		
		board.movePiece(WHITE_PAWN, "E2", "E4")
	}
	
	@Test
	void naoDeveConsiderarSeOReiSeMoverDeE7ParaD6SeHouverUmPeaoEntreADamaAdversariaEORei() {
		fail("Não implementado")
	}
	
	@Test
	void deveLancarUmErroSeARainhaDeC5EstiverDandoXequeNoReiEmE7() {
		fail("Não implementado")
	}
	
	@Test
	void naoDevePermitirUmaPecaSaltarOutraAdversaria() {
		fail("Não implementado")
	}
	
	@Test
	void naoDeveConsiderarXequeSeOReiMoverSeParaUmCasaEmXequeMasForCaptura() {
		fail("Não implementado")
	}
	
	@Test
	void naoDevePermitirMoverAMesmaCorDePecasDuasVezesSeguidasEmPartidaOficial() {
		fail("Não implementado")
	}
}
