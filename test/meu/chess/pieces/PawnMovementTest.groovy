package meu.chess.pieces;

import static meu.chess.Board.*
import static org.junit.Assert.*
import meu.chess.Board
import meu.chess.MovimentoInvalidoException

import org.junit.Test

class PawnMovementTest extends TestPieceMovement {

	def static WHITE_PAWN =  new Pawn(WHITE)
	def static BLACK_PAWN =  new Pawn(BLACK)
	def static WHITE_KNIGHT =  new Knight(WHITE)
	def static BLACK_KNIGHT =  new Knight(BLACK)
	
	@Test
	public void oPeaoDeveSeParaACasaE3() {
		
		board.initializeWithInitialPosition()
		
		def pawn = WHITE_PAWN
		 
		board.movePiece(pawn, "E2","E3")
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
		"|  *|   |  *|   |PW*|   |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PW |PW*|PW |PW*|   |PW*|PW |PW*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|RW*|NW |BW*|QW |KW*|BW |NW*|RW |\n"+
		"|[_]|___|___|___|___|___|___|___|",
		board.draw()
		
	}
	
	@Test(expected=MovimentoInvalidoException.class  )	
	
	public void movimentoDoPeaoDeF2Invalido() {
		
		board.initializeWithInitialPosition()
		
		def pawn = WHITE_PAWN
		 
		board.movePiece(pawn, "F2","E6")
		
	}
	
	@Test
	public void movimentoDePeaoDeF2Valido() {
		board.initializeWithInitialPosition()
		
		def pawn = WHITE_PAWN
		 
		
		try {
			board.movePiece(pawn, "F2","F3")
			assertTrue("Passou", true)
		} catch (Exception e) {
			fail("Falhou")
		}
		
	}
	
	@Test
	public void movimentoDePeaoDeF2InvalidoParaF5() {
		board.initializeWithInitialPosition()
		try {
			def pawn = WHITE_PAWN
			board.movePiece(pawn, "F2","F5")
			fail("Falhou")
		} catch (Exception e) {
			assertTrue("Passou", true)
		}
		
	}


	@Test(expected=MovimentoInvalidoException.class)
	void deveLancarUmErroSeOPeaoMoverAPartirDeLinhasMenoresQue2() {
		
		board.initializeWithInitialPosition()
		board.movePiece(WHITE_PAWN, "H1", "H2")
		
	}
	
	@Test(expected=MovimentoInvalidoException.class)
	void deveLancarUmErroSeOPeaoMoverParaUmaColunaDiferenteDaAtual() {
		
		board.initializeWithInitialPosition()
		board.movePiece(WHITE_PAWN, "H2", "G2")
		
	}
	
	@Test(expected=MovimentoInvalidoException.class)
	void deveLancarUmErroSeOPeaoNaoExistirNaPosicaoAtualInformada() {
		
		board.initializeWithInitialPosition()
		//Nao existe peao em h3 ao iniciar o jogo
		board.movePiece(WHITE_PAWN, "H3", "H4")
		
	}
	
	@Test(expected=MovimentoInvalidoException.class)
	void naoDevePermitirUmPeaoBrancoEmLugarDoPeaoNegroSeNaoForCaptura() {
		
		board.initializeWithInitialPosition()
		//Existe um peao em H7 e o movimento nao deve ser possivel
		board.movePiece(WHITE_PAWN, "H6", "H7")
	}
	
	@Test(expected=MovimentoInvalidoException.class)
	void naoDevePermitirMoverOPeaoParaTras() {
		 
		board.initializeWithInitialPosition()
		board.movePiece(WHITE_PAWN, "A2", "A1")
	}
	
	@Test
	void deveCapturaOPeaoEmD5() {
		board.initializeWithInitialPosition()
		
		board.movePiece(WHITE_PAWN, "E2", "E4")
		board.movePiece(BLACK_PAWN, "D7", "D5")
		board.movePiece(WHITE_PAWN , "E4", "D5")
		
		assertEquals " ___ ___ ___ ___ ___ ___ ___ ___ \n"+
		"|RB |NB*|BB |QB*|KB |BB*|NB |RB*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PB*|PB |PB*|   |PB*|PB |PB*|PB |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|   |  *|   |  *|   |  *|   |  *|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|  *|   |  *|PW |  *|   |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|   |  *|   |  *|   |  *|   |  *|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|  *|   |  *|   |  *|   |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PW |PW*|PW |PW*|   |PW*|PW |PW*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|RW*|NW |BW*|QW |KW*|BW |NW*|RW |\n"+
		"|[_]|___|___|___|___|___|___|___|",
		board.draw()
	}
	
	@Test
	void deveCapturaOPeaoBrancoEmA4() {
		board.initializeWithInitialPosition()
		
		board.movePiece(WHITE_PAWN, "A2", "A4")
		board.movePiece(BLACK_PAWN, "B7", "B5")		
		board.movePiece(WHITE_KNIGHT , "G1", "H3")
		board.movePiece(BLACK_PAWN, "B5", "A4")
		
		assertEquals " ___ ___ ___ ___ ___ ___ ___ ___ \n"+
		"|RB |NB*|BB |QB*|KB |BB*|NB |RB*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PB*|   |PB*|PB |PB*|PB |PB*|PB |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|   |  *|   |  *|   |  *|   |  *|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|  *|   |  *|   |  *|   |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PB |  *|   |  *|   |  *|   |  *|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|  *|   |  *|   |  *|   |  *|NW |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|   |PW*|PW |PW*|PW |PW*|PW |PW*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|RW*|NW |BW*|QW |KW*|BW |  *|RW |\n"+
		"|[_]|___|___|___|___|___|___|___|",
		board.draw()
	}
	
	@Test
	void deveCapturaOPeaoBrancoEmH4() {
		board.initializeWithInitialPosition()
		
		board.movePiece(WHITE_PAWN, "G2", "G3")
		board.movePiece(BLACK_PAWN, "H7", "H5")
		board.movePiece(WHITE_PAWN, "A2", "A4")
		board.movePiece(BLACK_PAWN, "H5", "H4")
		board.movePiece(WHITE_PAWN, "G3", "H4")
		
		assertEquals " ___ ___ ___ ___ ___ ___ ___ ___ \n"+
		"|RB |NB*|BB |QB*|KB |BB*|NB |RB*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PB*|PB |PB*|PB |PB*|PB |PB*|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|   |  *|   |  *|   |  *|   |  *|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|  *|   |  *|   |  *|   |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|PW |  *|   |  *|   |  *|   |PW*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|  *|   |  *|   |  *|   |  *|   |\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|   |PW*|PW |PW*|PW |PW*|   |PW*|\n"+
		"|___|___|___|___|___|___|___|___|\n"+
		"|RW*|NW |BW*|QW |KW*|BW |NW*|RW |\n"+
		"|[_]|___|___|___|___|___|___|___|",
		board.draw()
	}
	
	@Test(expected=MovimentoInvalidoException.class)
	void naoDevePermitirCapturaOPeaoEmA3() {
		
		board.initializeWithInitialPosition()
		
		board.movePiece(WHITE_PAWN, "A2", "A4")		
		board.movePiece(BLACK_PAWN , "A7", "A5")
		board.movePiece(WHITE_PAWN , "A4", "A5")
	}
	
	@Test(expected=MovimentoInvalidoException.class)
	void arrumandoOPeaoEstaAndandoNaDiagonal() {
		
		board.initializeWithInitialPosition()
		board.movePiece(WHITE_PAWN, "E2", "F3")
	}

	@Test
	void exibirTelaDePromocaoQuandoPeaoNaUltimaDasBrancas() {
		
		fail("Nao implementado")
	}
	@Test
	void exibirTelaDePromocaoQuandoPeaoNaUltimaDasNegras() {
		
		fail("Nao implementado")
	}
	
	@Test
	void deveMudarParaAPecaPromovidaRainhaDasBrancas() {
		
		fail("Nao implementado")
	}
	@Test
	void deveMudarParaAPecaPromovidaRainhaDasNegras() {
		
		fail("Nao implementado")
	}
	
	@Test
	void deveMudarParaAPecaPromovidaTorreDasBrancas() {
		
		fail("Nao implementado")
	}
	@Test
	void deveMudarParaAPecaPromovidaTorreDasNegras() {
		
		fail("Nao implementado")
	}
	
	@Test
	void deveMudarParaAPecaPromovidaCavaloDasBrancas() {
		
		fail("Nao implementado")
	}
	@Test
	void deveMudarParaAPecaPromovidaCavaloDasNegras() {
		
		fail("Nao implementado")
	}
}
