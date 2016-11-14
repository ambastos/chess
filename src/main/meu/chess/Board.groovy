package meu.chess

import static meu.chess.builder.BoardBuilder.*
import static meu.chess.factory.BoardFactory.BOARD_COLUMNS
import static meu.chess.utils.ConvertUtil.*
import groovy.transform.CompileStatic;

import java.util.regex.Pattern

import meu.chess.builder.MoveLineReader;
import meu.chess.factory.BoardFactory
import meu.chess.factory.PieceFactory
import meu.chess.movement.BoardMovement
import meu.chess.movement.Move
import meu.chess.movement.MoveLine
import meu.chess.pieces.Bishop
import meu.chess.pieces.King
import meu.chess.pieces.Knight
import meu.chess.pieces.Piece
import meu.chess.pieces.Queen
import meu.chess.pieces.Rock

class Board {

	def static final WHITE = "W"
	def static final BLACK = "B"
	def static final COLOR_NAMES = ["W":"branca", "B":"negra"]
	final static int LINE_HEIGHT = 3
	final static int MAX_NUMBER_OF_LINES = 8
	final static int MAX_NUMBER_OF_COLUMNS = 8
	
	def squares = [:]
	def board = ""
	def application = false
	def color = WHITE
	
	def kingInCheck
	
	def cursorCordinate = "A1"
	def selectPieceOnCursor = false
	def squareWithGrabedPiece
	def lastPressedKey
	def moveLines = []
	
	BoardMovement boardMovement = new BoardMovement(this)
	BoardFactory boardFactory = new BoardFactory(this)
	
	//Print the chessBoard
	public String draw() {
		draw(null)
	}
	
	public String draw(def pressedKey) {
		if (pressedKey !=null) {
			boardMovement.defineCursorCordinate(pressedKey)
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
	
	def loadGame(arquivoPgn) {
		this.initializeWithInitialPosition()
		
		File f 
		if (arquivoPgn instanceof File)
			f = arquivoPgn
		else	
			f= new File(arquivoPgn)
			
		FileInputStream fip
		try {
			fip = new FileInputStream(f)
			
			fip.readLines().each { line->
				if (line.substring(0,1).isNumber()) {
					def number = line.substring(0,1)
					def p = Pattern.compile("\\d\\.(.*)-(.*)[\\s](.*)-(.*)")
					def m =  p.matcher(line.toUpperCase())
					//Se não achar é porque não é um pgn válido ou o padrão é outro 
					if (!m.find())
						return
					
					MoveLineReader move = new MoveLineReader(this, line)
					movePiece(move.white.piece, move.white.initialPosition, move.white.finalPosition)
					movePiece(move.black.piece, move.black.initialPosition, move.black.finalPosition)
				}
			}
		}finally {
			fip.close()
		}
	}
	
	def initializeWithInitialPosition() {
		
		def pieces = PieceFactory.createInitialPieces()
		initialize(pieces)
	}
	
	public void movePiece(Piece piece, def currentPosition, def finalPosition) {
		boardFactory.movePiece(piece, currentPosition, finalPosition)
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
		def kingEnemyPosition = getKing(kingEnemyColor).cordinate		
	}
	
	def getKing(kingColor) {
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
 		def king =  getKing(color)
		def square = king.currentSquare
		
		isSquareAtacked(square, color)
	}

	def isSquareAtacked(square, color) {

		if  (isSquareVerticallyAttacked(square, color)) {
			return true
		}
			
		if (isSquareHorizontallyAttacked(square, color)) {
			return true
		}
		
		if (isSquareDiagonallyAttacked(square, color)) {
			return true
		}	 
		
		if (isLShapedAttacked(square, color)) {
			return true
		}	
		return false
	}
	
	def isThereAPieceOfSameColor(pieceOnSquare, square, color) {
		pieceOnSquare != square.content && pieceOnSquare?.color == color
	}
	
	private boolean isThereQueenOrBishopInThisSquare(pieceOnSquare, colorOfOwnPiece) {
		def b = pieceOnSquare instanceof Queen
		if (pieceOnSquare?.color != colorOfOwnPiece) {
			if ( pieceOnSquare instanceof Queen || pieceOnSquare instanceof Bishop) {
				return true
			}
		}		
		return false
	}
	
	private boolean isThereQueenOrRockInThisSquare(pieceOnSquare, color) {
		if (pieceOnSquare?.color != color) {
			if (pieceOnSquare instanceof Queen || pieceOnSquare instanceof Rock) {
				return true
			}
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
	
	def getLine(line) {
		return boardMovement.getLine(line)
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
	
	public boolean getSelectPieceOnCursor() {
		return selectPieceOnCursor;
	}
	
	def getPiecesThatsAttackMe(Piece pieceAttacked) {
		
		def evilPieces = []
		 
		evilPieces.addAll( getPiecesThatsAttackMeInLShape(pieceAttacked.currentSquare, pieceAttacked.color) )
		evilPieces.addAll( getPiecesThatsAttackMeFromVerticals(pieceAttacked.currentSquare, pieceAttacked.color) )
		evilPieces.addAll( getPiecesThatsAttackMeDiagonally(pieceAttacked.currentSquare, pieceAttacked.color))
		evilPieces.addAll( getPiecesThatsAttackMeHorizontally(pieceAttacked.currentSquare, pieceAttacked.color))
		
		return evilPieces
	}

	private isLShapedAttacked(square, color) {
		def evils = getPiecesThatsAttackMeInLShape(square, color)
		return evils?.size() > 0
	}
	
	private isSquareHorizontallyAttacked(square, color) {
		return getPiecesThatsAttackMeHorizontally(square, color)?.size() > 0
	}
	
	private boolean isSquareVerticallyAttacked(square, color) {
		
		return getPiecesThatsAttackMeFromVerticals(square, color)?.size() > 0
	}
	
	private isSquareDiagonallyAttacked(square,color) {
		return getPiecesThatsAttackMeDiagonally(square, color)?.size() > 0
	}
	
	private List getPiecesThatsAttackMeDiagonally(square,color) {
		def check = false
		def evilPieces = []
		 
		def diagonals = getDiagonals(square.cordinate)
		for (objDiagonal in diagonals) {
			if (check)
				break
			for (currentSquare in objDiagonal.value.square) {
				
				def pieceOnSquare = currentSquare.content
				if (isThereAPieceOfSameColor(pieceOnSquare, square, color)) {
					break
				}
				if (isThereQueenOrBishopInThisSquare(pieceOnSquare, color) ) {
					evilPieces.add(pieceOnSquare)
					check = true
					break
				}
			}
		}
		 
		return evilPieces
	}
	
	private List getPiecesThatsAttackMeHorizontally(square, color) {
		def evilsPieces =[]
		
		def columnNumber = getNumberOfColumnFromCordinate(square.cordinate)
		def line = getLineFromCordinate(square.cordinate)

		def finalSquareCordinates = []
		finalSquareCordinates.add(getCordinateFromColumNumberAndLine(1,line))
		finalSquareCordinates.add(getCordinateFromColumNumberAndLine(MAX_NUMBER_OF_COLUMNS,line))

		for (finalSquareCordinate in finalSquareCordinates) {
			def horizontal = getHorizontal(square.cordinate, finalSquareCordinate)
			for (currentSquare in horizontal) {
				
				def isIgnoringSameSquare = square.cordinate.equals(currentSquare.cordinate)
				if (isIgnoringSameSquare) {
					continue
				}
				
				def pieceOnSquare = currentSquare?.content
				if (isThereAPieceOfSameColor(pieceOnSquare, square, color)) {
					break
				}	
				if (isThereQueenOrRockInThisSquare(pieceOnSquare, color)) {
					evilsPieces.add(pieceOnSquare) 
				}	
			}
		}
		return evilsPieces
	}
	
	
	private List getPiecesThatsAttackMeFromVerticals(square, color) {
		
		def evilPieces = []
		
		def columnNumber = getNumberOfColumnFromCordinate(square.cordinate)
		def line = getLineFromCordinate(square.cordinate)
		
		def finalSquareCordinates = []
		finalSquareCordinates.add(getCordinateFromColumNumberAndLine(columnNumber,1))
		finalSquareCordinates.add(getCordinateFromColumNumberAndLine(columnNumber,MAX_NUMBER_OF_LINES))

		for (finalSquareCordinate in finalSquareCordinates) {
			def vertical = getVertical(square.cordinate, finalSquareCordinate)
			for (currentSquare in vertical) {
				def isIgnoreSameCordinate = square.cordinate.equals(currentSquare.cordinate)
				if (isIgnoreSameCordinate) {
					continue
				}	
				def pieceOnSquare = currentSquare?.content
				if (isThereAPieceOfSameColor(pieceOnSquare, square, color)) {
					return evilPieces
				}	
					
				if (isThereQueenOrRockInThisSquare(pieceOnSquare, color)) {
					evilPieces.add(pieceOnSquare)
				}
			}
		}
		return evilPieces
	}
	
	private List getPiecesThatsAttackMeInLShape(square, color) {
		def LShape = getLShape(square.cordinate)
		def evilPieces = []

		for (currentSquare in LShape) {
			def pieceOnSquare = currentSquare?.content
			if (isThereAPieceOfSameColor(pieceOnSquare, square, color))
				continue

			if (isSquareAttackeByKnight(pieceOnSquare, color)) {
				evilPieces.add( pieceOnSquare )
			}
		}
		return evilPieces
	}
	
	def addMoveLine(moveLine) {
		moveLines.add(moveLine)
	}
	
	MoveLine getLastMoveLine() {
		if (!moveLines.isEmpty())
			moveLines.get(moveLines.size()-1)	
	}
	def registerMoveLine(piece, initialPosition, finalPosition, castle) {
		
		def lastMoveLine = getLastMoveLine()
		def moveNumber = 0
		
		if (lastMoveLine) {
			moveNumber = lastMoveLine.getMoveNumber()
		}
		def moveLine
		if (piece.color == Board.WHITE) {
			moveLine = new MoveLine()
			moveNumber+=1
			addMoveLine(moveLine)
		}else { 
			moveLine = lastMoveLine
		}	
				
		moveLine.moveNumber = moveNumber
		
		def enemyColor = piece.color == WHITE ? BLACK : WHITE
		def isEnemyKingInCheck = isThereKingInCheck(enemyColor )
		
		def move = new Move(piece, initialPosition, finalPosition, isEnemyKingInCheck) 	
		if (piece.color == Board.WHITE) 
			moveLine.white = move
		else
			moveLine.black = move
			
	}
	
	def isThisPieceMovedBefore(content) {
		for (line in moveLines) {
			def move
			if (content.color == WHITE) 
				move = line.white
			else
				move = line.black
			
			if (move !=null 
				&& move.piece.equals(content)) 	
					return true 
					
		}
		return false
	}
	
	void setCursorCordinate(cordinate) {
		def line = getLineFromCordinate(cordinate)
		def column = getNumberOfColumnFromCordinate(cordinate)
		
		if ((line < 1 || line > MAX_NUMBER_OF_LINES) || (column < 1 || column > MAX_NUMBER_OF_COLUMNS) )
			return
		this.cursorCordinate = cordinate
	}	
}