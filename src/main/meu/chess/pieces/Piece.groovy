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
}
