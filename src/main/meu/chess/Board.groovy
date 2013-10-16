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
	
	boolean application = false
	Piece lastMovedPiece = null
	
	def cursorCordinate = "A1"
	def selectPieceOnCursor = false
	def squareWithGrabedPiece
	def lastPressedKey
	
	BoardMovement boardMovement = new BoardMovement(this)
	BoardFactory boardFactory =  new BoardFactory(this)
	Atack atack = new Atack(this)
	
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
	
	Square getSquareBy(cordinate) {
		squares.get(cordinate)
	}
	
	def getSquaresByCordinates(cordinates) {
		def squaresBy =[]
		for (cordinate in cordinates) {
			def square = this.getSquareBy(cordinate)
			if (square !=null)
				squaresBy.add(this.getSquareBy(cordinate))
		}
		squaresBy
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
	
	//ATACK
	def isThereKingInCheck(color) {
 		atack.isThereKingInCheck(color)
	}

	def isSquareAtacked(square, color) {
		atack.isSquareAtacked(square, color)
	}
	
	def isAtackedByPawn(square, color) {
		atack.isAtackedByPawn(square, color)
	}
	
	def isSquareDiagonallyAtacked(square,color) {
		atack.isAtackedByPawn(square, color)
	}
	
	private isLShapedAtacked(square, color) {
		atack.isLShapedAtacked(square, color)
	}
	
	private isSquareHorizontallyAtacked(square, color) {
		atack.isSquareHorizontallyAtacked(square, color)
	}

	private boolean isSquareVerticallyAttacked(square, color) {
		atack.isSquareVerticallyAttacked(square, color)
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
	
	def void applyChangesOnSquares() {
		for (Square square in squares.values()) {
			if (square.isChanged()) {
				square.update(square.cordinate, square.futureContent)
				lastMovedPiece = square.content
			}	
		}
	}
	
}
