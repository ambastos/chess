package meu.chess.pieces

import static meu.chess.utils.ConvertUtil.*
import meu.chess.Board
import meu.chess.MovimentoInvalidoException
class Rock extends Piece implements ValidPiece {
	
	def IDENTIT = "R"
	
	Rock(color) {
		this.simbol = IDENTIT+color
		this.color = color
	}

	@Override
	def validMovement(square, finalSquareCordinate) {
		Board board = square.board
		def initialSquareCordinate = square.cordinate
		
		def isHorizontal =  board.isHorizontal(initialSquareCordinate, finalSquareCordinate)
		def isVertical = board.isVertical(initialSquareCordinate, finalSquareCordinate)

		if (!isHorizontal && !isVertical ) {
			throw new MovimentoInvalidoException("Movimento da torre em $square.cordinate é inválido.")
		}else {
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
				throw new MovimentoInvalidoException("Movimento da torre inválido devido a presença do $currentSquare.content.description em $currentSquare.cordinate.")
			}
		}
	}

	@Override
	def getDescription() {
		"Torre $simbol"
	}
}
