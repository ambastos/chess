package meu.chess.movement

import static meu.chess.utils.ConvertUtil.*

import com.google.common.collect.Ordering
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
		def diagonal = []
		
		DiagonalObject(squareCordinate) {
			sourceSquareCordinate = squareCordinate
		}
		def add =  { cordinate ->
			diagonal.add(cordinate)
		}
		
		def cordinatesFromSource = {
			
			def squaresFromDirection = [1:[], 2:[]]
			diagonal.each {
				def squareColumnNumber =  getNumberOfColumnFromCordinate(sourceSquareCordinate)
				def columnNumber = getNumberOfColumnFromCordinate(it.cordinate)
				if (columnNumber < squareColumnNumber) {
					squaresFromDirection[1].add(it)
				}else if (columnNumber > squareColumnNumber) {
					squaresFromDirection[2].add(it)
				}
			}
			Collections.sort(squaresFromDirection[1], byCordinate)
			Collections.reverse(squaresFromDirection[1])
			Collections.sort(squaresFromDirection[2], byCordinate)
			squaresFromDirection
		}
	}
	
	def getDiagonals(squareCordinate) {
		
		def getDiagonalIndex = { cordinate, columnNumber, line ->
			def index
			def currentColumnNumber = getNumberOfColumnFromCordinate(cordinate)
			def currentLine = getLineFromCordinate(cordinate)
			
			if (line == 1 || line == Board.MAX_NUMBER_OF_LINES) {
				index = 1
			}else {
			
				def difColumnAndLine = (currentColumnNumber - columnNumber) - (currentLine - line)
				if (difColumnAndLine == 0  ) {
					index = 1
				}else {
					index = 2
				}
			}
			index
		}
		
		def removeEmptyDiagonal = { diagonals ->
			if (!diagonals[1]) {
				diagonals.remove(1)
			}
			
			if (!diagonals[2]) {
				diagonals.remove(2)
			}
		}
		
		
		def columnNumber = getNumberOfColumnFromCordinate(squareCordinate)
		def line = getLineFromCordinate(squareCordinate)
				
		def diagonals = [1:null,2:null]
		def index = 1
		
		for (value in board.squares) {
			def currentSquare = value.value
			def cordinate = currentSquare.cordinate
			if (isDiagonal(squareCordinate, cordinate)
				 && board.hasThisSquare(cordinate)) {
				
				index = getDiagonalIndex(cordinate, columnNumber, line)
				if  (cordinate == squareCordinate) {
					def diagonal1 = diagonals[1] ?: new DiagonalObject(squareCordinate)
					diagonal1.add(currentSquare)
					diagonals[1] = diagonal1
					
					if (line != 1 && line != Board.MAX_NUMBER_OF_LINES) {
						def diagonal2 = diagonals[2] ?: new DiagonalObject(squareCordinate)
						diagonal2.add(currentSquare)
						diagonals[2] = diagonal2
					}
				} else {
					def diagonal = diagonals[index] ?: new DiagonalObject(squareCordinate)
					diagonal.add(currentSquare)
					diagonals[index] = diagonal
				}
			}
		}
		
		removeEmptyDiagonal(diagonals)
		
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
	
	
	def byCordinate = new Ordering<String>() {
		public int compare( squareA, squareB) {
			
			def columnA = getNumberOfColumnFromCordinate(squareA.cordinate)
			def lineA = getLineFromCordinate(squareA.cordinate)
			
			def columnB = getNumberOfColumnFromCordinate(squareB.cordinate)
			def lineB = getLineFromCordinate(squareB.cordinate)
			
			def partA = Integer.valueOf( String.valueOf(columnA)+String.valueOf(lineA))
			def partB = Integer.valueOf( String.valueOf(columnB)+String.valueOf(lineB))
			
			return partA - partB
		};
	}
}