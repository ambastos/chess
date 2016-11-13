package meu.chess.pieces


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
			throw new MovimentoInvalidoException("Movimento do bispo em $square.cordinate é inválido.")
		}else {
			if (isDiagonal) {
				def diagonal = board.getDiagonal(initialSquareCordinate, finalSquareCordinate)
				validMovementsOnSquares(square, diagonal)
			}
		}
	}
	
	@Override
	def getDescription() {
		"Bispo ${simbol}"
	}
}

