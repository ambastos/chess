package meu.chess.factory

import meu.chess.Board
import meu.chess.MovimentoInvalidoException
import meu.chess.Square
import meu.chess.pieces.Piece
import meu.chess.pieces.ValidPiece

class MovementValidator {

	Board board 
	Piece originPiece
	Square square
	def currentPosition
	def finalPosition 
	def movedPiece
	
	MovementValidator(board, movedPiece, originPiece, square, currentPosition, finalPosition) {
		this.board = board
		this.movedPiece = movedPiece
		this.originPiece = originPiece
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
			throw new MovimentoInvalidoException("Não é possível mover o rei para a casa $finalSquare.cordinate pois ela está em xeque.")
		}
	}

	private verifyIfTheKingIsNotInCheckWhileThePieceIsMoved() {
		if (!square.isAKing() && board.isThereKingInCheck(square.content.color)) {
			def king = board.getKingFromColor(square.content.color)
			def kingCordinate = king.currentSquare.cordinate
			throw new MovimentoInvalidoException("Não é possível mover a peça $square.content.description de $currentPosition para $finalPosition enquanto o rei em $kingCordinate se encontra em xeque.")
		}
	}

	private verifyIfCurrentAndFinalPositionAreTheSame() {
		if (currentPosition == finalPosition) {
			throw new MovimentoInvalidoException("A própria peça(square.content.description) não pode se mover para a mesma casa.")
		}
	}

	private verifyIfDestineSquareHavePieceOfSameColor() {
		def finalSquare = board.squares.get(finalPosition)
		if (finalSquare.hasPieceOfSameColor(square.content) )
			throw new MovimentoInvalidoException("Movimento da peça $square.content.description não é valido devido a presença de $finalSquare.content.description na casa $finalPosition.")
		return finalSquare
	}

	private verifyIfThereIsAFinalSquare() {
		def finalSquare = board.squares.get(finalPosition)
		if (finalSquare == null)
			throw new MovimentoInvalidoException("A casa de destino $finalPosition da peça $square.content.description é inválida.")
		return finalSquare
	}

	private verifyIfPieceIsNotTheSameColorAnType() {
		if (!movedPiece.isTheSameColorAndType(square.content)) {
			throw new MovimentoInvalidoException("Não há a peça ($originPiece) desse tipo na casa informada.")
		}
	}

	private verifyIfPieceOfSameColorMovedTwice() {
		if (board.isApplication() && board.getLastMovedPiece()?.color == originPiece.color) {
			def strColor = originPiece.isWhite() ? "brancas" : "pretas"
			throw new MovimentoInvalidoException("Não é a vez das $strColor.")
		}
	}

	private verifyFinalPositionExist() {
		if (!board.hasThisSquare(finalPosition))
			throw new MovimentoInvalidoException("A casa de destino $finalPosition é inválida.")
	}

	private verifyInitialPositionExist() {
		if ( !board.hasThisSquare(currentPosition))
			throw new MovimentoInvalidoException("A casa de origem $currentPosition é inválida.")
	}
	
	private verifyIfThisSquareEmpty() {
		def pieceInSquare = square.content
		def isAValidPiece = pieceInSquare instanceof ValidPiece
		
		if (pieceInSquare == null || !isAValidPiece)		
			throw new MovimentoInvalidoException("Nao há peça nessa casa ($square.cordinate)")
	}
	
}
