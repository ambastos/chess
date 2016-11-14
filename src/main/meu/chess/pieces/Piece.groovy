package meu.chess.pieces

import static meu.chess.Board.*;
import meu.chess.MovimentoInvalidoException


class Piece {
	
	public final static EMPTY_PIECE = "  "
	
	def simbol = EMPTY_PIECE
	def currentSquare
	def color
	
	Piece() {
		
	}
	
	Piece(String simbol) {
		this.simbol = simbol	
	}
	
	
	public String print() {
		return simbol
	}
	
	def isWithPiece () {
		simbol.equals(EMPTY_PIECE)
	}
	
	def isBlack() {
		color == BLACK
	}
	
	def isWhite() { 
		color == WHITE
	}
	
	def validMovement(square, finalSquareCordinate) {
		throw new MovimentoInvalidoException("Deve-se implementar o m�todo 'validMovement' para a pe�a se mover")
		//Precisa ser implementado para funcionar
	} 
	
	def validMovementsOnSquares(square, squares) {
		for (currentSquare in squares) {
			if (currentSquare == square || currentSquare == null)
				continue
			if (currentSquare.content != square.content && currentSquare.hasPieceOfSameColor(square.content) ) {
				throw new MovimentoInvalidoException("Movimento da pe�a $description inv�lido devido a presen�a do $currentSquare.content.description em $currentSquare.cordinate.")
			}
		}
	}

	def isTheSameColorAndType(anotherPiece) {
		this.simbol.equals(anotherPiece.simbol)
	}	
	
	def getDescription() {
		def description = currentSquare !=null ? simbol+ ": "+ currentSquare  : simbol;
	}
	@Override
	public String toString() {
		return simbol;
	}
	
	def isAKing() {
		return (this instanceof King)
	}
	
	def isARock() {
		(this instanceof Rock)
	}
	
	def isPawn() {
		(this instanceof Pawn)
	}
}
