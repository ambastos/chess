package meu.chess.pieces

import static meu.chess.utils.ConvertUtil.*
import meu.chess.MovimentoInvalidoException

class King extends Piece implements ValidPiece {

	def IDENTIT = "K"
	
	King(color) {
		this.simbol = IDENTIT+color
		this.color = color
	}
	
	@Override
	def validMovement(square, newCordinate) {
		
		if ( !isAValidMovement(square, newCordinate) ) {
			throw new MovimentoInvalidoException("Movimento do rei para casa $newCordinate é inválido.")
		}
	
	}
	
	def isAValidMovement(square, newCordinate) {
		def oldColumnNumber =  square.columnNumber
		def oldLine = square.line
		def newColumnNumber =  getNumberOfColumnFromCordinate(newCordinate)
		def newLine = getLineFromCordinate(newCordinate)
		def numberOfMovedColumns = Math.abs(oldColumnNumber - newColumnNumber)
		def numberOfMovedLine = Math.abs(oldLine - newLine)
		
		((numberOfMovedColumns == 1 || numberOfMovedColumns ==0) && (numberOfMovedLine == 1 || numberOfMovedLine == 0))  
	}
	
	@Override
	def getDescription() {
		def description = !currentSquare ? currentSquare : simbol  
		"Rei de $description "
	}
	
}
