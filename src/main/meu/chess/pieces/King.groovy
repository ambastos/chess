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
		
		def castle = new Castle(square, newSquare)
				def kingValidMovement = {
			((numberOfMovedColumns == 1 || numberOfMovedColumns ==0) 
				&& (numberOfMovedLine == 1 || numberOfMovedLine == 0))
		}	
		
		if (!kingValidMovement() && !castle.isCastleValid())
			throw new MovimentoInvalidoException("Movimento do rei para casa $newCordinate é inválido.")
	}

	def getCastle(square, newCordinate) {
		def castle = new Castle(square, newCordinate)
		castle
	}
	
	private class Castle {
		
		Board board
		def square
		def numberOfMovedColumns
		def numberOfMovedLine
		def newSquare
		
		Castle(square, newSquare) {
			
			def oldColumnNumber =  square.columnNumber
			def newColumnNumber =  getNumberOfColumnFromCordinate(newSquare.cordinate)
			this.square = square
			this.board = square.board
			this.numberOfMovedColumns = Math.abs(oldColumnNumber - newColumnNumber)
			this.numberOfMovedLine = Math.abs(square.line - getLineFromCordinate(newSquare.cordinate))
			this.newSquare = newSquare
		}
		
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
					squares = board.getSquaresBetweenCordinates(["E1", "H1"])
				}else {
					squares = board.getSquaresBetweenCordinates(["E8", "H8"])
				}
			}else if (isCastleQueenSide()) {
				if (this.isWhite()) {
					squares = board.getSquaresBetweenCordinates(["E1", "A1"])
				}else {
					squares = board.getSquaresBetweenCordinates(["E8", "A8"])
				}
			}
			squares
		}

		def pieceBetweenKingAndKingRock = {
			def squares = getSquaresBetweenKingAndRock()
			for (currentSquare in squares) {
				if (!currentSquare?.content?.isRock() && !currentSquare?.content?.isKing()) {
					if (currentSquare.isFilledWithPiece()) {
						return currentSquare.content
					}
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
		
		def getNewSquareForRock = { 
			def squares = getSquaresBetweenKingAndRock()
			def rockSquare
			for(def i = 0; i < squares.size(); i++ ) {
				def square = squares.get(i)
				if (square.content.isKing()) {
					rockSquare = squares.get(i+1)
					break
				}
			}
			rockSquare
		}
		
		def getRock = {
			def squares = getSquaresBetweenKingAndRock()
			for (currentSquare in squares) {
				if (currentSquare.content.isRock()) {
					return currentSquare.content
				}
			}
		}
		
		def isRockAlreadyMoved = {
			def rock = getRock()
			
			def moves = board.moves.entrySet()
			for (moveEach in moves) {
				for (move in moveEach.value) {
					if (move.content.equals(rock)) {
						return true
					}
				}
			}
			return false
		}

		def isCastleValid = {
			
			if  (isCastleKingSide() || isCastleQueenSide()) {
				def pieceBetweenKingAndKingRock = pieceBetweenKingAndKingRock()
				if (pieceBetweenKingAndKingRock !=null ) {
					def description = pieceBetweenKingAndKingRock?.description+" em $pieceBetweenKingAndKingRock.currentSquare.cordinate"
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
	}
	
	@Override
	def getDescription() {
		def description = !currentSquare ? currentSquare : simbol  
		"Rei de $description "
	}
	
}
