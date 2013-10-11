package meu.chess.pieces

import static meu.chess.utils.ConvertUtil.*
import meu.chess.Board;
import meu.chess.MovimentoInvalidoException

class Queen extends Piece implements ValidPiece {

	def IDENTIT = "Q"
	
	Queen(color) {
		this.simbol = IDENTIT+color
		this.color = color
	}
	
	@Override
	public validMovement(square, finalSquareCordinate) {
		Board board = square.board
		def initialSquareCordinate = square.cordinate
		
		def isDiagonal = board.isDiagonal(initialSquareCordinate, finalSquareCordinate)
		def isHorizontal =  board.isHorizontal(initialSquareCordinate, finalSquareCordinate)
		def isVertical = board.isVertical(initialSquareCordinate, finalSquareCordinate)

		if (!isDiagonal && !isHorizontal && !isVertical ) {
			throw new MovimentoInvalidoException("Movimento da rainha em $square.cordinate é inválido.")
		}else {
			if (isDiagonal) {
				def diagonal = board.getDiagonal(initialSquareCordinate, finalSquareCordinate)
				validMovementsOnSquares(square, diagonal)
			}
			
			if (isHorizontal) {
				def horizontal = board.getHorizontal(initialSquareCordinate, finalSquareCordinate)
				validMovementsOnSquares(square, horizontal)
			}
			
			if (isVertical) {
				def vertical = board.getVertical(initialSquareCordinate, finalSquareCordinate)
				validMovementsOnSquares(square, vertical)
			}
		}		
	}

	private validMovementsOnSquares(square, squares) {
		for (currentSquare in squares) {
			if (currentSquare == square || currentSquare == null)
				continue
			if (currentSquare.content != square.content && currentSquare.hasPieceOfSameColor(square.content) ) {
				throw new MovimentoInvalidoException("Movimento da rainha inválido devido a presença do $currentSquare.content.description em $currentSquare.cordinate.")
			}
		}
	}
}
