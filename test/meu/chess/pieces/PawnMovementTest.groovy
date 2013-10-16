package meu.chess.pieces;

import static meu.chess.Board.*;
import static org.junit.Assert.*;

import java.beans.Transient;

import meu.chess.Board;
import meu.chess.MovimentoInvalidoException;
import meu.chess.pieces.Knight;
import meu.chess.pieces.Pawn
import meu.chess.pieces.Piece;

import org.junit.Before;
import org.junit.Test;

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
		"|___|___|___|___|___|___|___|___|",
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
		board.movePiece(WHITE_PAWN, "H2", "G3")
		
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
		"|___|___|___|___|___|___|___|___|",
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
		"|___|___|___|___|___|___|___|___|",
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
		"|___|___|___|___|___|___|___|___|",
		board.draw()
	}
	
	
	@Test(expected=MovimentoInvalidoException.class)
	void naoDevePermitirOPeaoSaltarOutraPecaAdversaria() {
		
		board.initializeWithInitialPosition()
		board.movePiece(WHITE_PAWN, "E2", "E4")
		board.movePiece(BLACK_PAWN, "E7", "E5")
		board.movePiece(WHITE_PAWN, "E4", "E5")
		
	}
	
	@Test
	void deveCapturarOPeaoNegroEmEnPassant() {
		
		board.initializeWithInitialPosition()
		board.movePiece(WHITE_PAWN, "D2", "D3")
		board.movePiece(BLACK_PAWN, "D7", "D5")
		board.movePiece(WHITE_PAWN, "A2", "A3")
		board.movePiece(BLACK_PAWN, "D5", "D4")
		board.movePiece(WHITE_PAWN, "E2", "E4")
		board.movePiece(BLACK_PAWN, "D4", "E3")
		
		//Não deve haver peça na casa E4
		assert true == (board.getSquareBy("E4").content instanceof NullPiece)
	}
	
	@Test(expected=MovimentoInvalidoException.class)
	void naoDeveCapturarOPeaoNegroEmEnPassantSeOutraPecaJaFoiMovida() {
		
		board.initializeWithInitialPosition()
		board.movePiece(WHITE_PAWN, "D2", "D3")
		board.movePiece(BLACK_PAWN, "D7", "D5")
		board.movePiece(WHITE_PAWN, "A2", "A3")
		board.movePiece(BLACK_PAWN, "D5", "D4")
		board.movePiece(WHITE_PAWN, "E2", "E4")
		board.movePiece(new Knight(WHITE), "G1", "F3")
		board.movePiece(BLACK_PAWN, "D4", "E3")//Seria EnPassant se não fosse o cavalo movido em F3
		 
	}
	
	@Test
	void deveExibirAOpcaoDePromocaoDoPeao() {
		fail("Esse teste esta falhando")	
	}
	
	@Test(expected=MovimentoInvalidoException.class)
	void naoDeveClonarOPeaoAoTentaloMoverParaTrasEParaFrenteNovamente() {
		 
		fail("Esse teste esta falhando")	
	}	
	
}
