package meu.chess

import static meu.chess.utils.ConvertUtil.*

import java.awt.event.KeyEvent;
import java.util.HashMap;

import org.hamcrest.core.IsAnything;

import static meu.chess.builder.BoardBuilder.*
import static meu.chess.factory.BoardFactory.BOARD_COLUMNS
import meu.chess.factory.BoardFactory
import meu.chess.factory.PieceFactory;
import meu.chess.movement.BoardMovement;
import meu.chess.pieces.Bishop;
import meu.chess.pieces.King;
import meu.chess.pieces.Knight;
import meu.chess.pieces.Pawn;
import meu.chess.pieces.Piece;
import meu.chess.pieces.Queen;
import meu.chess.pieces.Rock;

class Board {

	def static final WHITE = "W"
	def static final BLACK = "B"
	final static int LINE_HEIGHT = 3
	final static int MAX_NUMBER_OF_LINES = 8
	final static int MAX_NUMBER_OF_COLUMNS = 8
	
	def squares = [:]
	def board = ""
	def application = false
	
	def kingInCheck
	
	def cursorCordinate = "A1"
	def selectPieceOnCursor = false
	def squareWithGrabedPiece
	def lastPressedKey
	
	BoardMovement boardMovement = new BoardMovement(this)
	BoardFactory boardFactory =  new BoardFactory(this)
	
	//Print the chessBoard
	public String draw() {
		draw(null)
	}
	
	public String draw(def pressedKey) {
		
		if (pressedKey !=null) {
			defineCursorCordinate(pressedKey)
		}	
		
		clearBoardIfNecessary()
		board = buildTheLinesToPrint(this)
		return board
	}
	
	//Inicialize the ChessBoard
	public void initialize(HashMap<String, Piece> pieces) {
		
		for (line in 1..MAX_NUMBER_OF_LINES) {
			for (colum in "A".."H") {
				def cordinate = colum+line
				createSquares(pieces, cordinate)
			}			
		}				
	}
	
	def initializeWithInitialPosition() {
		
		def pieces = PieceFactory.createInitialPieces()
		initialize(pieces)
	}
	
	public void movePiece(Piece piece, def currentPosition, def finalPosition) {
		boardFactory.movePiece(piece, currentPosition, finalPosition)
	}

	private defineCursorCordinate(pressedKey) {
		
		boardMovement.defineCursorCordinate(pressedKey)		
	}

	def clearBoardIfNecessary() {
		if (squares.size() > 0) {
			board = ""
		}
	}
	
	def getSquareBy(cordinate) {
		squares.get(cordinate)
	}
	
	def getKingEnemyCordinate(kingEnemyColor) {
		def kingEnemyPosition = getKingFromColor(kingEnemyColor).cordinate		
	}
	
	def getKingFromColor(kingColor) {
		def king
		for (square in this.squares.values()) {
			def pieceOnSquare =  square.content
			if (pieceOnSquare instanceof King && pieceOnSquare.color == kingColor) {
				king = pieceOnSquare
				break
			}
		}
		king
	}

	private void createSquares(HashMap pieces, String cordinate) {
		def isContainsPieces = pieces !=null ? pieces.containsKey(cordinate) : false
		def square
		if (isContainsPieces  ) {
			def piece = pieces.get(cordinate)
			square = new Square(cordinate,piece)
		}else {
			square = new Square(cordinate)
		}
		square.board = this
		squares.put(cordinate, square)
	}

	def hasThisSquare(cordinate) {
		squares.containsKey(cordinate)
	}
	
	def isThereKingInCheck(color) {
 		def king =  getKingFromColor(color)
		def square = king.currentSquare
		
		isSquareAtacked(square, color)
	}

	def isSquareAtacked(square, color) {

		if  (isSquareVerticallyAttacked(square, color)) {
			return true
		}
			
		if (isSquareHorizontallyAtacked(square, color)) {
			return true
		}
		
		if (isSquareDiagonallyAtacked(square, color)) {
			return true
		}	 
		
		if (isLShapedAtacked(square, color)) {
			return true
		}	
		return false
	}
	
	def isSquareDiagonallyAtacked(square,color) {
		def check = false
		
		def diagonals = getDiagonals(square.cordinate)
		for (objDiagonal in diagonals) {
			for (currentSquare in objDiagonal.value.diagonal) {
				
				def pieceOnSquare = currentSquare.content
				if (isThereAPieceOfSameColor(pieceOnSquare, square, color)) 
					break
				if (isThisSquareAttackedByQueenOrBishop(pieceOnSquare, color) ) 
					return true
			}	
		}
		
		return false
	}
	
	def isThereAPieceOfSameColor(pieceOnSquare, square, color) {
		pieceOnSquare != square.content && pieceOnSquare?.color == color
	}
	
	private boolean isThisSquareAttackedByQueenOrBishop(pieceOnSquare, colorOfOwnPiece) {
		pieceOnSquare?.color != colorOfOwnPiece && ( pieceOnSquare instanceof Queen || pieceOnSquare instanceof Bishop)
	}
	
	private boolean isSquareAttackedByQueenOrRock(pieceOnSquare, color) {
		if (pieceOnSquare?.color != color) {
			if (pieceOnSquare instanceof Queen || pieceOnSquare instanceof Rock) {
				return true
			}
		}
		return false
	}

	private isLShapedAtacked(square, color) {
		
		def LShape = getLShape(square.cordinate)
		for (currentSquare in LShape) {
			def pieceOnSquare = currentSquare?.content
			if (isThereAPieceOfSameColor(pieceOnSquare, square, color)) 
				break
			
			if (isSquareAttackeByKnight(pieceOnSquare, color)) 
				return true
		}
		return false
	}
	
	private def isSquareAttackeByKnight(pieceOnSquare, color) {
		if (pieceOnSquare?.color != color) {
			if (pieceOnSquare instanceof Knight) {
				return true
			}
		}
		return false
	}
	
	private isSquareHorizontallyAtacked(square, color) {
		
		def columnNumber = getNumberOfColumnFromCordinate(square.cordinate)
		def line = getLineFromCordinate(square.cordinate)

		def finalSquareCordinates = []
		finalSquareCordinates.add(getCordinateFromColumNumberAndLine(1,line))
		finalSquareCordinates.add(getCordinateFromColumNumberAndLine(MAX_NUMBER_OF_COLUMNS,line))

		for (finalSquareCordinate in finalSquareCordinates) {
			def horizontal = getHorizontal(square.cordinate, finalSquareCordinate)
			for (currentSquare in horizontal) {
				def pieceOnSquare = currentSquare?.content
				if (isThereAPieceOfSameColor(pieceOnSquare, square, color)) 
					break
				
				if (isSquareAttackedByQueenOrRock(pieceOnSquare, color))
					return true
			}
		}
		return false
	}

	private boolean isSquareVerticallyAttacked(square, color) {
		
		def columnNumber = getNumberOfColumnFromCordinate(square.cordinate)
		def line = getLineFromCordinate(square.cordinate)
		
		def finalSquareCordinates = []
		finalSquareCordinates.add(getCordinateFromColumNumberAndLine(columnNumber,1))
		finalSquareCordinates.add(getCordinateFromColumNumberAndLine(columnNumber,MAX_NUMBER_OF_LINES))

		for (finalSquareCordinate in finalSquareCordinates) {
			def vertical = getVertical(square.cordinate, finalSquareCordinate)
			for (currentSquare in vertical) {
				def pieceOnSquare = currentSquare?.content
				if (isThereAPieceOfSameColor(pieceOnSquare, square, color)) 
					return false
					
				if (isSquareAttackedByQueenOrRock(pieceOnSquare, color)) 
					return true
			
			}
		}	
		return false
	}
	
	
	
	def isDiagonal(initialSquareCordinate, finalSquareCordinate ) {
		boardMovement.isDiagonal(initialSquareCordinate, finalSquareCordinate)
	}
	
	def isVertical(initialSquareCordinate, finalSquareCordinate) {
		boardMovement.isVertical(initialSquareCordinate, finalSquareCordinate)
	}
	
	def isHorizontal(initialSquareCordinate, finalSquareCordinate) {
		boardMovement.isHorizontal(initialSquareCordinate, finalSquareCordinate) 
	}
	
	def getDiagonals(squareCordinate) {
		boardMovement.getDiagonals(squareCordinate)
	}
	
	def getDiagonal(initialSquareCordinate, finalSquareCordinate) {
		boardMovement.getDiagonal(initialSquareCordinate, finalSquareCordinate)
	}
	
	def getHorizontal(initialSquareCordinate, finalSquareCordinate) {
		boardMovement.getHorizontal(initialSquareCordinate, finalSquareCordinate)
	}	
	
	def getVertical(initialSquareCordinate, finalSquareCordinate) {
		boardMovement.getVertical(initialSquareCordinate, finalSquareCordinate)
	}
	
	def isLShape(initialSquareCordinate, finalSquareCordinate) {
		boardMovement.isLShape(initialSquareCordinate, finalSquareCordinate)
	}
	
	def getLShape(initialSquareCordinate) {
		boardMovement.getLShape(initialSquareCordinate)
	}
	
	def isAValidCordinate(cordinate) {
		squares.containsKey(cordinate)
	}
}
