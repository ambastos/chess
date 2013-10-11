package meu.chess.pieces

import static Math.*
import static Board.*
import static meu.chess.utils.ConvertUtil.*
import meu.chess.Board
import meu.chess.MovimentoInvalidoException

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
	
	def movesValidator(color, square, newPosition) {
		def colorOfOlderSquare = color
		def columnOfOldeSquare = square.column
		def lineOfOlderSquare = square.line			
		def columnOfNewerSquare = getColumnFromCordinate(newPosition);
		def lineOfNewerSquare = getLineFromCordinate(newPosition);			
		def board = square.board
		def newSquare = board.squares.get(newPosition)
		def pieceOfNewSquare = newSquare.content
		def possibleLines = []
		
		def isWhite = {
			colorOfOlderSquare == WHITE
		}
		
		def isBlack = {
			colorOfOlderSquare == BLACK
		}
		
		def isInitialLineForPawns = {
			if (isWhite() ) {
				lineOfOlderSquare == 2
			}else {
				lineOfOlderSquare == 7
			}
		} 
		
		def isFirstLineForWhitePawns = {
			lineOfOlderSquare == 2
		}
		
		def isFirstLineForBlackPawns = {
			lineOfOlderSquare == 7
		}
		
		def fillPossibilities = {
			if (isWhite()) {
				if (isFirstLineForWhitePawns() ) {
					possibleLines.add(lineOfOlderSquare+1)
					possibleLines.add(lineOfOlderSquare+2)
				}else {
					possibleLines.add(lineOfOlderSquare+1)
					
				}
			}else {
				if (isFirstLineForBlackPawns() ) {
					possibleLines.add(lineOfOlderSquare-1)
					possibleLines.add(lineOfOlderSquare-2)
				}else {
					possibleLines.add(lineOfOlderSquare-1)
				}
			}
			possibleLines
		}
		
		def isCatchable = {
			 
			if (!(pieceOfNewSquare instanceof NullPiece) && pieceOfNewSquare.color != colorOfOlderSquare ) {
				def isOneColumnBeside = ( abs( newSquare.columnNumber - square.columnNumber) == 1 )
				def isOneLineAhead = ( newSquare.line - square.line ) == 1
				if (isBlack()) {
					isOneLineAhead = ( square.line - newSquare.line ) == 1
				}	
				
				if ( isOneColumnBeside && isOneLineAhead ) {
					return true
				}
					
			}else {
				return true
			}
			return false
		}
		
		def validMovement = {  
			if (!isInitialLineForPawns) {
				throw new MovimentoInvalidoException("Linha inicial para o peão é inválida.")
			}
				
			if (!isCatchable()) {
				throw new MovimentoInvalidoException("Movimento inválido para o peão em $square.cordinate.")				
				
			} else {
				
				possibleLines = fillPossibilities()
				
				if (!possibleLines.contains(lineOfNewerSquare) ) {
					throw new MovimentoInvalidoException("Movimento inválido para o peão.")
				}
			}
		}
		
		validMovement()
	}
	
	@Override
	def getDescription() {
		"Peão ($simbol)";
	}
}
