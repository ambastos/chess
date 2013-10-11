package meu.chess.pieces;
import static Board.*
import static org.junit.Assert.*;
import meu.chess.Board;
import meu.chess.MovimentoInvalidoException;

import org.junit.Test;

class PieceMovementTest extends TestPieceMovement {

	Rock WHITE_ROCK = new Rock(Board.WHITE)
	
	@Test(expected=MovimentoInvalidoException.class)
	public void naoDevePermitirMoverAPecaParaAMesmaCasa() {
		
		board.initializeWithInitialPosition()
		board.movePiece(WHITE_ROCK, "A1", "A1")
		
	}

	@Test
	public void naoDevePermitirUmMovimentoDePecaAlemDasCordendasDeLinha() {
		
		board.initializeWithInitialPosition()
		
		try {
			board.movePiece(WHITE_ROCK, "A1", "A9")
			fail()
		} catch (Exception e) {
			assertEquals "A casa de destino A9 é inválida.", e.message
		}
		
	}
	
	@Test
	public void naoDevePermitirUmMovimentoDePecaAlemDasCordendasDeColuna() {
		
		board.initializeWithInitialPosition()
		
		try {
			board.movePiece(WHITE_ROCK, "A1", "I1")
			fail()
		} catch (Exception e) {
			assertEquals "A casa de destino I1 é inválida.", e.message
		}
		
	}
	
	@Test(expected=MovimentoInvalidoException.class)
	void naoDevePermitirOMovimentoDeUmaPecaDeTipoDiferenteDaOrdenada() {
		 
		board.initializeWithInitialPosition()
		board.movePiece(WHITE_ROCK, "B1", "B2")	//A peça em B1 é um cavalo branco
	}
	
	@Test(expected=MovimentoInvalidoException.class)
	void naoDevePermitirOMovimentoDeUmaPecaDeCorDiferenteDaOrdenada() {
		 
		board.initializeWithInitialPosition()
		board.movePiece(new Pawn(WHITE), "A2", "A4") 
		board.movePiece(new Pawn(BLACK), "A7", "A5")
		board.movePiece(WHITE_ROCK, "A8", "A6")	//A peça em A81 é uma torre Negra
	}
	
}
