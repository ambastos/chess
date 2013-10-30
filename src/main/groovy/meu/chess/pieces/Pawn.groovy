package meu.chess.pieces

import static Math.*
import static Board.*
import static meu.chess.utils.ConvertUtil.*
import meu.chess.Board
import meu.chess.MovimentoInvalidoException
import meu.chess.Square

class Pawn extends Piece implements ValidPiece {

	def IDENTIT = "P"
	
	Pawn() {
		super()
	}
	
	Pawn(def color) {
		this.simbol = IDENTIT + color		
		this.color = color 
	}
	
	@Override
	def validMovement(square, newPosition) {
		movesValidator(this.color, square, newPosition)
	}
	
	def isAtackedBy(pieceOnSquare, square, newSquare) {
		isOneColumnBeside(square, newSquare) && isOneLineAhead(pieceOnSquare, square, newSquare)
	}
	
	private isOneColumnBeside = { square, newSquare ->
		( abs( newSquare.columnNumber - square.columnNumber) == 1 )
	}
	private isOneLineAhead = { pieceOnSquare, square, newSquare -> 
		if (pieceOnSquare.isBlack())
			( square.line - newSquare.line ) == 1
		else
			( newSquare.line - square.line ) == 1
	}
	
	def isCatchableEnPassant(pieceOnSquare, square, newSquare) { 
		Board board = square.board
		
		if (isAtackedBy(pieceOnSquare, square, newSquare)) {
		
			def previousCordinate
			if (pieceOnSquare.isBlack())
				previousCordinate = getNewCordinateFrom(newSquare.cordinate, 0, 1)
			else
				previousCordinate = getNewCordinateFrom(newSquare.cordinate, 0, -1)
				
			def previousPieceOnCordinate = board.getSquareBy(previousCordinate).content
			
			return previousPieceOnCordinate == board.lastMovedPiece &&
				(board.getLastMovedPiece() instanceof Pawn) &&
				board.getLastMovedPiece().color != pieceOnSquare.color
		}
		false
	}
	
	def movesValidator(color, Square square, newPosition) {
		
		Board board = square.board
		def newSquare = board.getSquareBy(newPosition)
		Piece pieceOnSquare = square.content
		Piece pieceOfNewSquare = newSquare.content
		
		def isFirstLineForWhitePawns = {
			square.line == 2
		}
		
		def isFirstLineForBlackPawns = {
			square.line == 7
		}
		
		def isAValidInitialLineForPawns = {
			if (pieceOnSquare.isWhite()) {
				getLineFromCordinate(newPosition) >= 2
			}else {
				getLineFromCordinate(newPosition) <= 7
			}
		}
		
		def isAValidSquareToMoveAhead = { cordinate ->
			if (newSquare.isFilledWithPiece()) 
				return false
			
			def distance = square.distanceBetweenThisCordinate(cordinate)
			if (pieceOnSquare.isWhite()) 
				if (isFirstLineForWhitePawns() ) {
					return distance == [0:2] || distance == [0:1]
				}else { 
					return distance == [0:1]
				}	
			else 
				if (isFirstLineForBlackPawns() ) { 
					return  distance == [0:-2] || distance == [0:-1]
				}else{ 
					return distance == [0:-1]
				}	
			return false
		}
		
		def isPieceOnNewSquareCatchable = {
			
			if (isCatchableEnPassant(pieceOnSquare, square, newSquare) ) 
				return true
			
			if (!(pieceOfNewSquare instanceof NullPiece) && pieceOfNewSquare.color != pieceOnSquare.color ) {
				
				if (isAtackedBy(pieceOnSquare, square, newSquare) ) 
					return true
			}else {
				return false
			}
			return false
		}
		
		def validMovement = {  
			if (!isAValidInitialLineForPawns()) 
				throw new MovimentoInvalidoException("Linha inicial para o peão é inválida.")
			
			if (isPieceOnNewSquareCatchable()) 
				return
			
			if (!isAValidSquareToMoveAhead(newPosition)) 
				throw new MovimentoInvalidoException("Movimento inválido para o peão em $square.cordinate.")
		}
		
		validMovement()
	}
	
	@Override
	def getDescription() {
		"Peão ($simbol)";
	}
}