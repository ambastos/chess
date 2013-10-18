package meu.chess.factory

import meu.chess.Board
import meu.chess.MovimentoInvalidoException
import meu.chess.Square
import meu.chess.pieces.Piece
import meu.chess.pieces.ValidPiece

class MovementValidator {

	Board board 
	Piece content
	Square square
	def currentPosition
	def finalPosition 
	
	MovementValidator(board, content, square, currentPosition, finalPosition) {
		this.board = board
		this.content = content
		this.square= square
		this.currentPosition = currentPosition
		this.finalPosition = finalPosition
	}
	
	def validate() {
		verifyIfThisSquareEmpty()
		verifyInitialPositionExist()
		verifyFinalPositionExist()
		verifyIfPieceOfSameColorMovedTwice()
		verifyIfPieceIsNotTheSameColorAnType()
		verifyIfThereIsAFinalSquare()
		verifyIfCurrentAndFinalPositionAreTheSame()
		verifyIfDestineSquareHavePieceOfSameColor()
		verifyCheck()
	}

	private verifyCheck() {
		verifyIfTheKingIsNotInCheckWhileThePieceIsMoved()
		verifyIfKingIsGoingToSquareInCheck()
	}
	
	private verifyIfKingIsGoingToSquareInCheck() {
		def finalSquare = board.squares.get(finalPosition)
		if (square.isAKing() && board.isSquareAtacked(finalSquare, square?.content?.color)) {
			throw new MovimentoInvalidoException("N�o � poss�vel mover o rei para a casa $finalSquare.cordinate pois ela est� em xeque.")
		}
	}

	private verifyIfTheKingIsNotInCheckWhileThePieceIsMoved() {
		if (!square.isAKing() && board.isThereKingInCheck(square.content.color)) {
			def king = board.getKingFromColor(square.content.color)
			def kingCordinate = king.currentSquare.cordinate
			throw new MovimentoInvalidoException("N�o � poss�vel mover a pe�a $square.content.description de $currentPosition para $finalPosition enquanto o rei em $kingCordinate se encontra em xeque.")
		}
	}

	private verifyIfCurrentAndFinalPositionAreTheSame() {
		if (currentPosition == finalPosition) {
			throw new MovimentoInvalidoException("A pr�pria pe�a(square.content.description) n�o pode se mover para a mesma casa.")
		}
	}

	private verifyIfDestineSquareHavePieceOfSameColor() {
		def finalSquare = board.squares.get(finalPosition)
		if (finalSquare.hasPieceOfSameColor(square.content) )
			throw new MovimentoInvalidoException("Movimento da pe�a $square.content.description n�o � valido devido a presen�a de $finalSquare.content.description na casa $finalPosition.")
		return finalSquare
	}

	private verifyIfThereIsAFinalSquare() {
		def finalSquare = board.squares.get(finalPosition)
		if (finalSquare == null)
			throw new MovimentoInvalidoException("A casa de destino $finalPosition da pe�a $square.content.description � inv�lida.")
		return finalSquare
	}

	private verifyIfPieceIsNotTheSameColorAnType() {
		if (!content.isTheSameColorAndType(square.content)) {
			throw new MovimentoInvalidoException("N�o h� a pe�a ($content) desse tipo na casa informada.")
		}
	}

	private verifyIfPieceOfSameColorMovedTwice() {
		if (board.isApplication() && board.getLastMovedPiece()?.color == content.color) {
			def strColor = content.isWhite() ? "brancas" : "pretas"
			throw new MovimentoInvalidoException("N�o � a vez das $strColor.")
		}
	}

	private verifyFinalPositionExist() {
		if (!board.hasThisSquare(finalPosition))
			throw new MovimentoInvalidoException("A casa de destino $finalPosition � inv�lida.")
	}

	private verifyInitialPositionExist() {
		if ( !board.hasThisSquare(currentPosition))
			throw new MovimentoInvalidoException("A casa de origem $currentPosition � inv�lida.")
	}
	
	private verifyIfThisSquareEmpty() {
		def pieceInSquare = square.content
		def isAValidPiece = pieceInSquare instanceof ValidPiece
		
		if (pieceInSquare == null || !isAValidPiece)		
			throw new MovimentoInvalidoException("Nao h� pe�a nessa casa ($square.cordinate)")
	}
	
}
