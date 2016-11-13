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
			board.movePiece(BLACK_PAWN, "E7", "E6")
			board.movePiece(new Queen(WHITE), "D1", "E2")
			board.movePiece(BLACK_PAWN, "E6", "D5")
			fail()
		} catch (Exception e) {
			assertEquals "Não é possível mover a peça Peão (PB) de E6 para D5 enquanto o rei em E8 se encontra em xeque.", e.message
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
			assert "Não é possível mover a peça Rei de E8 de E8 para F7 enquanto o rei em E8 se encontra em xeque." == e.message
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
		board.application = true
		
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
	void naoDeveConsideraXequeSeHouverUmaPecaDeMesmaCorEntreOAtacanteEOReiNegro() {
		try {
			board.initializeWithInitialPosition()
			board.application = true
			
			
			board.movePiece(WHITE_PAWN, "E2", "E4")
			board.movePiece(BLACK_PAWN, "E7", "E5")
			board.movePiece(new Bishop(WHITE), "F1", "B5")
			board.movePiece(BLACK_PAWN, "F7", "F6")
		}catch(e) {
			fail()
		}
	}
	
	
	@Test
	void naoDeveConsiderarXequeVerticalSeHouverUmaPecaDaPropriaCorEntreAPecaAtacanteEORei() {
		
		fail("Não implementado")
	}
	
	@Test
	void naoDeveConsiderarSeOReiSeMoverDeE7ParaD6SeHouverUmPeaoEntreADamaAdversariaEORei() {
		fail("Não implementado")
	}
	
	@Test
	void devePermitirTomarAPecaQueAtacaORei() {
		
		def pieces = ["G6": new Knight(WHITE), 
					  "E7": new King(BLACK),
					  "H7": new Pawn(BLACK)
					 ]
		board.application = true
		board.initialize(pieces)
		board.color = BLACK
		try {
			board.movePiece(new Pawn(BLACK), "H7", "G6")
		}catch(e) {
			fail("Falhou ao tentar tomar a peça")
		}
		
	}
	
	@Test 
	void naoDevePermitirJogarQuandoOReiForAtacadoPorUmCavalo() {

		def pieces = ["E5": new Knight(WHITE), "E8": new King(BLACK)]		
		board.application = true
		board.initialize(pieces)
		board.color = BLACK
		try {
			board.movePiece(new King(BLACK), "E8", "F7")
		}catch(e) {
			assertEquals  "Não é possível mover a peça Rei de E8 de E8 para F7 enquanto o rei em E8 se encontra em xeque." , e.message
		}	
		
	}
	
	@Test ()
	void naoDeveLancarUmErroSeARainhaDeF7EstiverDandoXequeNoReiEmE8() {
		def pieces = ["F7": new Queen(WHITE), "E8": new King(BLACK)]
		board.color = BLACK 
		board.setApplication(true)
		board.initialize(pieces)
		try {
			board.movePiece(new King(BLACK), "E8", "F7")
		}catch(e) {
			fail()
		}	
	}
	
	@Test
	void naoDevePermitirUmaPecaSaltarOutraAdversaria() {
		fail("Não implementado")
	}
	
	@Test
	void deveMudarAVezDoJogadorMesmoQuandoHouverXeque_ERRO() {
		board.application = true
		board.initializeWithInitialPosition()
		
		board.movePiece(WHITE_PAWN, "E2", "E4")
		board.movePiece(BLACK_PAWN, "E7", "E5")
		board.movePiece(new Bishop(WHITE), "F1", "B5")
		try {
			//REI Ficaria em xeque
			board.movePiece(BLACK_PAWN, "D7", "D5")
		}catch(e) {
			//Jogada valida
			try {
				assert board.color == BLACK
				board.movePiece(BLACK_PAWN, "F7", "F5")
			}catch(ee) {
				fail()
			}
		}	
		
	}
	
	@Test
	void naoDevePermitirMoverAMesmaCorDePecasDuasVezesSeguidasEmPartidaOficial() {
		board.application = true
		board.initializeWithInitialPosition()
		
		try {
			board.movePiece(WHITE_PAWN, "E2", "E4")
			board.movePiece(WHITE_PAWN, "D2", "D4")
			fail()
		}catch(e) {
			assert "Não é a vez das branca(s)." == e.message
		}
		
	}
	
	@Test
	void naoDevePermitirMoverAMesmaCorDePecasDuasVezesSeguidasEmPartidaOficialNegras() {
		board.initializeWithInitialPosition()
		board.application = true
		
		try {
			board.movePiece(WHITE_PAWN, "E2", "E4")
			board.movePiece(BLACK_PAWN, "D7", "D5")
			board.movePiece(BLACK_PAWN, "D5", "D4")
			fail()
		}catch(e) {
			assert "Não é a vez das negra(s)." == e.message
		}
	}
	
	@Test
	void naoDevePermitirAsNegrasIniciaremAPartida() {
		
		board.initializeWithInitialPosition()
		board.application = true
		
		try {
			board.movePiece(BLACK_PAWN, "D7", "D5")
			fail()
		}catch(e) {
			assert "Não é a vez das negra(s)." == e.message
		}
	}
}
