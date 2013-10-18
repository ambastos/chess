package meu.chess.pieces

import static meu.chess.Board.*
import meu.chess.MovimentoInvalidoException


class Piece {
	
	public final static EMPTY_PIECE = "  "
	static final NULL_PIECE = new NullPiece()
	
	def IDENTIT = ""
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
	
	def isValid () {
		!(this instanceof NullPiece)
	}
	
	def isBlack() {
		color == BLACK
	}
	
	def isWhite() { 
		color == WHITE
	}
	
	def validMovement(square, finalSquareCordinate) {
		throw new MovimentoInvalidoException("Deve-se implementar o método 'validMovement' para a peça se mover")
		//Precisa ser implementado para funcionar
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
	
	def isPawn(color) {
		(this instanceof Pawn) && this?.color == color
	}
	def isNotAPawn() {
		!(this instanceof Pawn)
	}
	
	def isKing() {
		(this instanceof King)
	}
	
	def isRock() {
		this instanceof Rock
	}
}
