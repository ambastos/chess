package meu.chess.movement

import static meu.chess.utils.ConvertUtil.*
import meu.chess.Board;

protected class Diagonal {
	
	Board board
	Diagonal(board) {
		this.board = board
	}
	
	def isDiagonal(initialSquareCordinate, finalSquareCordinate ) {
		def oldSquareColumn = getNumberOfColumnFromCordinate(initialSquareCordinate)
		def oldSquareLine = getLineFromCordinate(initialSquareCordinate)
		
		def newSquareColumn = getNumberOfColumnFromCordinate(finalSquareCordinate)
		def newSquareLine = getLineFromCordinate(finalSquareCordinate)
		
		(Math.abs(oldSquareColumn - newSquareColumn) == Math.abs(oldSquareLine - newSquareLine))
	}
	
	private class DiagonalObject {
		
		def sourceSquareCordinate
		def square = []
		
		DiagonalObject(squareCordinate) {
			sourceSquareCordinate = squareCordinate
		}
		def add =  { cordinate ->
			square.add(cordinate)
		}
		
		def cordinatesFromSource = {
			
			def squaresFromDirection = []
			square.each {
				def squareColumnNumber =  getNumberOfColumnFromCordinate(sourceSquareCordinate)
				def columnNumber = getNumberOfColumnFromCordinate(it.cordinate)
				
				squaresFromDirection.add(it.cordinate)
				
			}
			squaresFromDirection
		}
	}
	
	
	def getDiagonals(squareCordinate) {
		
		def getDiagonalIndex = { cordinate, columnNumber, line ->
			
			def index = 1
			def currentLine = getLineFromCordinate(cordinate)
			def currentColumnNumber = getNumberOfColumnFromCordinate(cordinate)
			
			if ( (currentLine < line && currentColumnNumber < columnNumber)) {
				index= 1
			}else if ((currentLine > line && currentColumnNumber < columnNumber)) {
				index= 2
			}else if ((currentLine > line && currentColumnNumber > columnNumber)) {
				index= 3
			}else if ((currentLine < line && currentColumnNumber > columnNumber)){
				index= 4
			}	
			return index 
		}
		
		
		def diagonals = [:]
		
		def columnNumber = getNumberOfColumnFromCordinate(squareCordinate)
		def line = getLineFromCordinate(squareCordinate)
		
		for (square in board.squares) {
			def currentSquare = square.value
			def cordinate = currentSquare.cordinate
			if (cordinate.equals(squareCordinate) )
				continue
			
			def isDiagonalAndHasThisSquare = isDiagonal(squareCordinate, cordinate) && board.hasThisSquare(cordinate)
			
			if (isDiagonalAndHasThisSquare) {
				def index = getDiagonalIndex(cordinate, columnNumber, line)
				
				def diagonal = diagonals[index] ?: new DiagonalObject(squareCordinate)
				diagonal.add(currentSquare)
				diagonals.put(index, diagonal)
			}
		}
		
		//reordenar
		if (diagonals[1])
			diagonals[1].square= diagonals[1].square.reverse()
		if (diagonals[4])
			diagonals[4].square= diagonals[4].square.reverse()
			
		if (diagonals.size() == 2) {
			def newDiagonals = [:]
			def newIdx = 1
			diagonals.keySet().each { key->
				newDiagonals.put(newIdx, diagonals.get(key))
				newIdx++
			}
			diagonals = newDiagonals			 
		}
		
		if (diagonals.size() == 1) {
			diagonals = [1:diagonals.values().getAt(0)]
		}
		diagonals
	}
	

	def getDiagonal(initialSquareCordinate, finalSquareCordinate) {
		def squaresFromDiagonal
		if (isDiagonal(initialSquareCordinate, finalSquareCordinate)) {
			
			def initialSquareColumn = getNumberOfColumnFromCordinate(initialSquareCordinate)
			def initialSquareLine = getLineFromCordinate(initialSquareCordinate)
			
			def finalSquareColumn = getNumberOfColumnFromCordinate(finalSquareCordinate)
			def finalSquareLine = getLineFromCordinate(finalSquareCordinate)
			
			squaresFromDiagonal = []
			
			def currentLine = initialSquareLine
			for (columnNumber in initialSquareColumn..finalSquareColumn) {
				def cordinate = getCordinateFromColumNumberAndLine(columnNumber, currentLine)
				squaresFromDiagonal.add( board.getSquareBy(cordinate))
				
				if (finalSquareLine < initialSquareLine) {
					currentLine--
				}else {
					currentLine++
				}
				
			}
			squaresFromDiagonal
		}
	}
}