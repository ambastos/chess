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
		
		//Possivelmente é rock então valida
		
		
		if (isCastle(square,newCordinate))  {
			return true		
		}	
		
		return ((numberOfMovedColumns == 1 || numberOfMovedColumns ==0) && (numberOfMovedLine == 1 || numberOfMovedLine == 0))  
	}
	
	def isCastle(square, newCordinate) {
		
		def oldColumnNumber =  square.columnNumber
		def oldLine = square.line
		def newColumnNumber =  getNumberOfColumnFromCordinate(newCordinate)
		def newLine = getLineFromCordinate(newCordinate)
		def numberOfMovedColumns = Math.abs(oldColumnNumber - newColumnNumber)
		def numberOfMovedLine = Math.abs(oldLine - newLine)
		
		return Math.abs(getNumberOfMovedColumns(square,newCordinate)) >= 2 && numberOfMovedLine == 0
	}
	
	def getNumberOfMovedColumns(square, newCordinate) {

		def oldColumnNumber =  square.columnNumber
		def oldLine = square.line
		def newColumnNumber =  getNumberOfColumnFromCordinate(newCordinate)
		def newLine = getLineFromCordinate(newCordinate)
		def numberOfMovedColumns = newColumnNumber - oldColumnNumber
		
		return numberOfMovedColumns

	}
	@Override
	def getDescription() {
		def description = currentSquare!=null ? currentSquare : simbol  
		"Rei de $description"
	}
	
}
