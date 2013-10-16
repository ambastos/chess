package meu.chess.movement

import static meu.chess.utils.ConvertUtil.*

import java.awt.event.KeyEvent;

import meu.chess.Board;
class BoardMovement {

	Board board
	def lShape
	Diagonal diagonal 
	
	BoardMovement(Board board) {
		this.board = board
		this.diagonal = new Diagonal(board)
		this.lShape = new LShape(board)
	}
	
	private defineCursorCordinate(pressedKey) {
		
		def newCursorCordinate
		
		switch (pressedKey) {
			case KeyEvent.VK_SPACE:
				board.selectPieceOnCursor = !board.selectPieceOnCursor
				if (board.lastPressedKey == pressedKey) {
					defineSquareWithGrabedPiece()
				}else {
					if (board.selectPieceOnCursor) {
						defineSquareWithGrabedPiece()
					}else {
						moveGrabedPiece()
					}
				}
				
				break
			case KeyEvent.VK_UP:
				newCursorCordinate = getNewCordinateFrom(board.cursorCordinate, 0, 1)
				board.cursorCordinate = newCursorCordinate
				break
			case KeyEvent.VK_DOWN:
				newCursorCordinate = getNewCordinateFrom(board.cursorCordinate, 0, -1)
				board.cursorCordinate = newCursorCordinate
				break
			case KeyEvent.VK_LEFT:
				newCursorCordinate = getNewCordinateFrom(board.cursorCordinate, -1, 0)
				board.cursorCordinate = newCursorCordinate
				break
			case KeyEvent.VK_RIGHT:
				newCursorCordinate = getNewCordinateFrom(board.cursorCordinate, 1, 0)
				board.cursorCordinate = newCursorCordinate
				break;
		}
		
		board.lastPressedKey = pressedKey
		
	}

	private moveGrabedPiece() {
		def oldCordinate = board.squareWithGrabedPiece.cordinate
		def newCordinate = board.cursorCordinate
		board.movePiece(board.squareWithGrabedPiece.content, oldCordinate, newCordinate)
		board.squareWithGrabedPiece = null
	}

	private defineSquareWithGrabedPiece() {
		def currentSquare = board.squares.get(board.cursorCordinate)
		if (currentSquare?.content) {
			board.squareWithGrabedPiece = currentSquare
		}
	}
	
	def isVertical(initialSquareCordinate, finalSquareCordinate) {
		def oldSquareColumn = getNumberOfColumnFromCordinate(initialSquareCordinate)
		def newSquareColumn = getNumberOfColumnFromCordinate(finalSquareCordinate)
		oldSquareColumn == newSquareColumn
	}
	
	def isHorizontal(initialSquareCordinate, finalSquareCordinate) {
		def oldSquareLine = getLineFromCordinate(initialSquareCordinate)
		def newSquareLine = getLineFromCordinate(finalSquareCordinate)
		oldSquareLine == newSquareLine
	}
	
	def getHorizontal(initialSquareCordinate, finalSquareCordinate) {
		def squaresFromHorizontal
		if (isHorizontal(initialSquareCordinate, finalSquareCordinate)) {
			
			def initialColumnNumber = getNumberOfColumnFromCordinate(initialSquareCordinate)
			def line = getLineFromCordinate(initialSquareCordinate)
			def finalColumnNumber = getNumberOfColumnFromCordinate(finalSquareCordinate)
			
			squaresFromHorizontal = []
			
			for (columnNumber in initialColumnNumber..finalColumnNumber) {
				def cordinate =  getCordinateFromColumNumberAndLine(columnNumber, line)
				def square = board.getSquareBy(cordinate)
				squaresFromHorizontal.add(square)
			}
		}
		squaresFromHorizontal
	}
	
	def getVertical(initialSquareCordinate, finalSquareCordinate) {
		def squaresFromVertical
		if (isVertical(initialSquareCordinate, finalSquareCordinate)) {
			def initialLine = getLineFromCordinate(initialSquareCordinate)
			def finalLine = getLineFromCordinate(finalSquareCordinate)
			def columnNumber = getNumberOfColumnFromCordinate(initialSquareCordinate)
			
			squaresFromVertical = []
			for (line in initialLine..finalLine) {
				def cordinate = getCordinateFromColumNumberAndLine(columnNumber, line)
				def square = board.getSquareBy(cordinate)
				squaresFromVertical.add(square)
			}
		}
		squaresFromVertical
	}
	
	def isDiagonal(initialSquareCordinate, finalSquareCordinate ) {
		diagonal.isDiagonal(initialSquareCordinate, finalSquareCordinate)
	}
	
	def getDiagonals(squareCordinate) {
		diagonal.getDiagonals(squareCordinate)
	}

	def getDiagonal(initialSquareCordinate, finalSquareCordinate) {
		diagonal.getDiagonal(initialSquareCordinate, finalSquareCordinate)
	}
	
	def isLShape(initialSquareCordinate, finalSquareCordinate) {
		lShape.isLShape(initialSquareCordinate, finalSquareCordinate)
	}
	
	def getLShape(initialSquareCordinate) {
		lShape.getLShape(initialSquareCordinate)
	}
}
