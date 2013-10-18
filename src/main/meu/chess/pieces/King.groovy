package meu.chess.pieces

import static meu.chess.utils.ConvertUtil.*
import meu.chess.Board
import meu.chess.MovimentoInvalidoException

class King extends Piece implements ValidPiece {

	def IDENTIT = "K"
	
	King(color) {
		this.simbol = IDENTIT+color
		this.color = color
	}
	
	def IT = this
	
	@Override
	def validMovement(square, newCordinate) {
		
		Board board = square.board
		def oldColumnNumber =  square.columnNumber
		def oldLine = square.line
		def newColumnNumber =  getNumberOfColumnFromCordinate(newCordinate)
		def newLine = getLineFromCordinate(newCordinate)
		def numberOfMovedColumns = Math.abs(oldColumnNumber - newColumnNumber)
		def numberOfMovedLine = Math.abs(oldLine - newLine)
		def newSquare = board.getSquareBy(newCordinate)
		
		def isCastle = {
			(square.isFirstLineFromPieces() && numberOfMovedColumns == 2 && numberOfMovedLine == 0 )
		}
		def isCastleKingSide = {
			isCastle() && (newSquare.getColumnNumber() - square.getColumnNumber() == 2)
		}
		def isCastleQueenSide = {
			isCastle() && (newSquare.getColumnNumber() - square.getColumnNumber() == -2)
		}
		
		def getSquaresBetweenKingAndRock = {
			def squares
			if (isCastleKingSide()) {
				if (this.isWhite()) {
					squares = board.getSquaresBetweenCordinates(["F1", "G1"])
				}else {
					squares = board.getSquaresBetweenCordinates(["F8", "G8"])
				}
			}else if (isCastleQueenSide()) {
				if (this.isWhite()) {
					squares = board.getSquaresBetweenCordinates(["D1", "C1"])
				}else {
					squares = board.getSquaresBetweenCordinates(["D8", "C8"])
				}
			}
			squares
		}

		def pieceBetweenKingAndKingRock = { 
			def squares = getSquaresBetweenKingAndRock()
			for (currentSquare in squares) {
				if (currentSquare.isFilledWithPiece()) {
					return currentSquare.content
				}
			}
		}
		
		def isKingAlreadyMoved = {
			def moves = board.moves.entrySet()
			for (moveEach in moves) {
				for (move in moveEach.value) {
					if (move.content.isKing() && IT.simbol == move.content.simbol) {
						return true
					}				
				}
			}
			return false
		}
		
		def isRockAlreadyMoved = {
			def moves = board.moves.entrySet()
			for (moveEach in moves) {
				for (move in moveEach.value) {
				}
			}
			return false
		}
		
		def isCastleValid = {
			
			if  (isCastleKingSide() || isCastleQueenSide()) {
				if (pieceBetweenKingAndKingRock() !=null ) {
					def description = pieceBetweenKingAndKingRock.description+" em $pieceBetweenKingAndKingRock.currentSquare.cordinate"
					throw new MovimentoInvalidoException(
					"Não é possível realizar o roque "+
					"das brancas devido a presença da peça $description."
					)
				}else if (isKingAlreadyMoved() || isRockAlreadyMoved()) {
					throw new MovimentoInvalidoException(
					"Não é possível realizar o roque "+
					"porque o rei ou a torre já se moveram."
					)
				}
			}
			isCastleKingSide() || isCastleQueenSide()
		}
	
		def kingValidMovement = {
			((numberOfMovedColumns == 1 || numberOfMovedColumns ==0) 
				&& (numberOfMovedLine == 1 || numberOfMovedLine == 0))
		}	
		
		if (!kingValidMovement() && !isCastleValid())
			throw new MovimentoInvalidoException("Movimento do rei para casa $newCordinate é inválido.")
	}


	private isCastleValid(square, int numberOfMovedColumns, int numberOfMovedLine,newSquare) {
		
		def isCastle = (square.isFirstLineFromPieces() && numberOfMovedColumns == 2 && numberOfMovedLine == 0 )
		def isCastleKingSide = isCastle && (newSquare.getColumnNumber() - square.getColumnNumber() == 2)
		def isCastleQueenSide = isCastle && (newSquare.getColumnNumber() - square.getColumnNumber() == -2)
		
		def squares
		if (isCastleKingSide) {
			if (this.isWhite()) {
				squares = square.board.getSquaresBetweenCordinates(["F1", "G1"])
			}else {
				squares = square.board.getSquaresBetweenCordinates(["F8", "G8"])
			}
		}else if (isCastleQueenSide) {
			if (this.isWhite()) {
				squares = square.board.getSquaresBetweenCordinates(["D1", "C1"])
			}else {
				squares = square.board.getSquaresBetweenCordinates(["D8", "C8"])
			}
		}
		
		if  (isCastleKingSide || isCastleQueenSide) {	
			def pieceBetweenKingAndKingRock = null
			for (currentSquare in squares) {
				if (currentSquare.isFilledWithPiece()) {
					pieceBetweenKingAndKingRock = currentSquare.content
					break
				}
			}

			if (pieceBetweenKingAndKingRock !=null) {
				def description = pieceBetweenKingAndKingRock.description+" em $pieceBetweenKingAndKingRock.currentSquare.cordinate"
				throw new MovimentoInvalidoException(
				"Não é possível realizar o roque "+
				"das brancas devido a presença da peça $description."
				)
			}
		}
		isCastleKingSide || isCastleQueenSide
	}
	
	
	@Override
	def getDescription() {
		def description = !currentSquare ? currentSquare : simbol  
		"Rei de $description "
	}
	
}
