package meu.chess;

import static meu.chess.Board.*
import static meu.chess.utils.ConvertUtil.*
import static org.junit.Assert.*
import meu.chess.pieces.Bishop
import meu.chess.pieces.King
import meu.chess.pieces.Knight
import meu.chess.pieces.Pawn
import meu.chess.pieces.Queen

import org.junit.Before
import org.junit.Test

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
			print board.draw()
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
			board.movePiece(BLACK_PAWN, "E7", "E5")
			board.movePiece(WHITE_PAWN, "D2", "D4")
			board.movePiece(new Queen(WHITE), "D1", "E2")
			board.movePiece(new Queen(WHITE), "E2", "E5")
			
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
			assert "Não é possível mover o rei para a casa F7 pois ele está em xeque." == e.message
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
			assert "Não é possível mover a peça Peão (PB) de A7 para A5 enquanto o rei em E8 se encontra em xeque." == e.message
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
		board.initializeWithInitialPosition()
		try {
			board.movePiece(WHITE_PAWN, "E2", "E4")
			board.movePiece(BLACK_PAWN, "D7", "D5")
			board.movePiece(WHITE_PAWN, "E4", "D5")
			board.movePiece(new Queen(WHITE), "D1", "E2")
			board.movePiece(new Queen(WHITE), "E2", "E5")//Seria xeque se não fosse o peão em E7 
			board.movePiece(new Knight(BLACK), "B8", "C6")
			assert true == true
		} catch (Exception e) {
			fail("Não era para ser xeque")
		}
	}
	
	@Test
	void naoDeveConsiderarXequeSeOReiSeMoverDeE7ParaD6SeHouverUmPeaoEntreADamaAdversariaEORei() {
		board.initializeWithInitialPosition()
		try {
			board.movePiece(WHITE_PAWN, "E2", "E4")
			board.movePiece(BLACK_PAWN, "E7", "E5")
			board.movePiece(new King(BLACK), "E8", "E7")
			board.movePiece(new King(BLACK), "E7", "D6")
			assert true == true
		} catch (Exception e) {
			fail("Não era para ser xeque")
		}
	}
	
	@Test(expected=MovimentoInvalidoException.class)
	void deveLancarUmErroSeARainhaDeC5EstiverDandoXequeNoReiEmE7() {
		
		board.initializeWithInitialPosition()
		board.movePiece(WHITE_PAWN, "E2", "E4")
		board.movePiece(BLACK_PAWN, "E7", "E5")
		board.movePiece(new King(BLACK), "E8", "E7")
		board.movePiece(new Queen(WHITE), "D1", "E2")
		board.movePiece(new Queen(WHITE), "E2", "C4")
		board.movePiece(new Queen(WHITE), "C4", "C5")//Xeque
		board.movePiece(BLACK_PAWN, "A7", "A5")
	}
	
	
	@Test
	void naoDeveConsiderarXequeSeOReiMoverSeParaUmCasaEmXequeMasForCaptura() {
		
		board.initializeWithInitialPosition()
		try {
			board.movePiece(WHITE_PAWN, "E2", "E4")
			board.movePiece(BLACK_PAWN, "E7", "E5")
			board.movePiece(new Bishop(WHITE), "F1", "C4")
			board.movePiece(new Bishop(WHITE), "C4", "F7")
			board.movePiece(new King(BLACK), "E8", "F7")
		}catch(e) {
			fail("Nao e para considerar xeque pois é captura.")
		}
	}
	
	@Test
	void naoDevePermitirMoverAMesmaCorDePecasDuasVezesSeguidasEmPartidaOficial() {
		board.initializeWithInitialPosition()
		board.setApplication(true)
		try {
			board.movePiece(WHITE_PAWN, "E2", "E4")
			board.movePiece(WHITE_PAWN, "E4", "E5")
			fail()
		}catch(e) {
			assert "Não é a vez das brancas." == e.message
		}	
	}
	
	@Test
	void naoDevePermitirMoverOutraPecaSeOReiNegroEstiverEmXequeDoPeaoEmF7() {
		
		board.initializeWithInitialPosition()
		board.setApplication(true)
		try {
			board.movePiece(WHITE_PAWN, "E2", "E4")
			board.movePiece(BLACK_PAWN, "E7", "E5")
			board.movePiece(new Bishop(WHITE), "F1", "C4")
			board.movePiece(BLACK_PAWN, "A7", "A5")
			board.movePiece(new Bishop(WHITE), "C4", "F7")//Xeque
			board.movePiece(new Knight(BLACK), "B8", "C6")
		} catch (e) {
			assert "Não é possível mover a peça Cavalo NB de B8 para C6 enquanto o rei em E8 se encontra em xeque." == e.message
		}
	}
	
	@Test
	void naoDevePermitiroReiNegroMoverSeParaF7SeACasaForAtacadaPorUmPeao() {
		board.initializeWithInitialPosition()
		board.setApplication(true)
		try {
			board.movePiece(WHITE_PAWN, "F2", "F4")
			board.movePiece(BLACK_PAWN, "F7", "F6")
			board.movePiece(WHITE_PAWN, "F4", "F5")
			board.movePiece(BLACK_PAWN, "G7", "G6")
			board.movePiece(WHITE_PAWN, "F5", "G6")
			board.movePiece(new King(BLACK), "E8", "F7")//Xeque
			fail()
		} catch (e) {
			assert "Não é possível mover o rei para a casa F7 pois ele está em xeque." == e.message
		}
	}
	
	@Test
	void naoDeveConsiderarXequeSeUmaIntercederDiagonalmenteEntreOReiNegroEADamaBranca() {
		board.initializeWithInitialPosition()
		board.setApplication(true)
		try {
			board.movePiece(WHITE_PAWN, "C2", "C4")
			board.movePiece(BLACK_PAWN, "D7", "D5")
			board.movePiece(new Queen(WHITE), "D1", "A4")//Xeque
			board.movePiece(BLACK_PAWN, "B7", "B5")//Interceptou o xeque
			assert true == true
		} catch (e) {
			fail("Não pode ser xeque.")
		}
	}
	
}
