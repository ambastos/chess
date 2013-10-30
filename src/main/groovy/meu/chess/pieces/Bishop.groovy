package meu.chess.pieces

import java.text.BreakDictionary;

import meu.chess.Board;
import meu.chess.MovimentoInvalidoException
import static meu.chess.utils.ConvertUtil.*;

import org.junit.Test;

class Bishop extends Piece implements ValidPiece {

	def IDENTIT = "B"
	
	
	Bishop(color) {
		this.simbol = IDENTIT + color
		this.color = color
	}
	
	@Override
	public validMovement(square, finalSquareCordinate) {
		Board board = square.board
		def initialSquareCordinate = square.cordinate
		
		def isDiagonal = board.isDiagonal(initialSquareCordinate, finalSquareCordinate)

		if (!isDiagonal) {
			throw new MovimentoInvalidoException("Movimento do bispo em $square.cordinate � inv�lido.")
		}else {
			if (isDiagonal) {
				def diagonal = board.getDiagonal(initialSquareCordinate, finalSquareCordinate)
				validMovementsOnSquares(square, diagonal)
			}
		}
	}
	
	private validMovementsOnSquares(square, squares) {
		for (currentSquare in squares) {
			if (currentSquare == square || currentSquare == null)
				continue
			if (currentSquare.content != square.content && currentSquare.hasPieceOfSameColor(square.content) ) {
				throw new MovimentoInvalidoException("Movimento do bispo � inv�lido devido a presen�a do $currentSquare.content.description em $currentSquare.cordinate.")
			}
		}
	}
	
	@Override
	def getDescription() {
		"Bispo ${simbol}"
	}
}

