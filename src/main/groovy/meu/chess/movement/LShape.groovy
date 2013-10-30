package meu.chess.movement;

import static meu.chess.utils.ConvertUtil.*;
import meu.chess.Board

protected class LShape {
	
	Board board
	LShape(board) {
		this.board = board
	}
	
	def isLShape(initialSquareCordinate, finalSquareCordinate) {
		
		def squareColumnNumber = getNumberOfColumn(getColumnFromCordinate(initialSquareCordinate))
		def squareLine = getLineFromCordinate(initialSquareCordinate)
		
		def newPositionColumNumber = getNumberOfColumn(getColumnFromCordinate(finalSquareCordinate))
		def newPositionLine = getLineFromCordinate(finalSquareCordinate)

		def first = Math.abs( squareColumnNumber - newPositionColumNumber )
		def second = Math.abs( squareLine - newPositionLine )
		
		def isValid = ((first == 1 && second ==2) || (first == 2 && second == 1))
		isValid
	}
	
	def getLShape(initialSquareCordinate) {
		def squareColumnNumber = getNumberOfColumn(getColumnFromCordinate(initialSquareCordinate))
		def squareLine = getLineFromCordinate(initialSquareCordinate)
		
		def squaresFromLShape = []
		
		def lines = buildLinesFromLShape(squareLine)
		
		for (currentLine in lines) {
			if (currentLine == squareLine)
				continue
				
			def numberOfLines = Math.abs(squareLine - currentLine)
			def numberOfColumns = (numberOfLines ==1 ? 2 : 1)
							 
			def columnNumber1 = squareColumnNumber + numberOfColumns
			def columnNumber2 = squareColumnNumber - numberOfColumns
			
			for (columnNumber in [columnNumber1, columnNumber2]) {
				if (isAValidColumn(columnNumber)) {
					def cordinate = getCordinateFromColumNumberAndLine(columnNumber, currentLine)
					def square = board.getSquareBy(cordinate)
					if (square !=null)
						squaresFromLShape.add(square)
				}
			}
		}
		squaresFromLShape
	}
	
	private def buildLinesFromLShape(squareLine) {
		def lines = new LinkedHashSet()
		
		def newPositionLine = squareLine - 2
		for (line in squareLine..newPositionLine) {
			lines.add(line)
		}
		
		newPositionLine = squareLine +2
		for (line in newPositionLine..squareLine) {
			lines.add(line)
		}
		lines
	}
}
