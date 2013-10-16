package meu.chess.factory

import meu.chess.Board
import meu.chess.MovimentoInvalidoException
import meu.chess.Square
import meu.chess.pieces.Piece
import meu.chess.pieces.ValidPiece

class MovementValidator {

	Board board 
	Piece piece
	Square square
	def currentPosition
	def finalPosition 
	
	MovementValidator(board, piece, square, currentPosition, finalPosition) {
		this.board = board
		this.piece = piece
		this.square= square
		this.currentPosition = currentPosition
		this.finalPosition = finalPosition
	}
	
	def validate() {
		if (isThisSquareEmpty(square))
		throw new MovimentoInvalidoException("Nao há peça nessa casa ($square.cordinate)")

		def finalSquare = board.squares.get(finalPosition)
		if (!board.hasThisSquare(currentPosition))
			throw new MovimentoInvalidoException("A casa de origem $currentPosition é inválida.")
			
		if (!board.hasThisSquare(finalPosition))
			throw new MovimentoInvalidoException("A casa de destino $finalPosition é inválida.")
		
		if (board.isApplication() && board.getLastMovedPiece()?.color == piece.color) {
			def strColor = piece.isWhite() ? "brancas" : "pretas"
			throw new MovimentoInvalidoException("Não é a vez das $strColor.")
		}
		
		if (!piece.isTheSameColorAndType(square.content)) {
			throw new MovimentoInvalidoException("Não há a peça ($piece) desse tipo na casa informada.")
		}
			
		if (finalSquare == null)
			throw new MovimentoInvalidoException("A casa de destino $finalPosition da peça $square.content.description é inválida.")
	
		if (currentPosition == finalPosition) {
			def d = square.content.description
			throw new MovimentoInvalidoException("A própria peça($d) não pode se mover para a mesma casa.")
		}
	
		def pieceInSquare = square.content
		if (finalSquare.hasPieceOfSameColor(pieceInSquare) )
			throw new MovimentoInvalidoException("Movimento da peça $square.content.description não é valido devido a presença de $finalSquare.content.description na casa $finalPosition.")
				
		if (!square.isAKing() && board.isThereKingInCheck(square.content.color)) {
			def king = board.getKingFromColor(square.content.color)
			def kingCordinate = king.currentSquare.cordinate
			throw new MovimentoInvalidoException("Não é possível mover a peça $square.content.description de $currentPosition para $finalPosition enquanto o rei em $kingCordinate se encontra em xeque.")
		}
	
		if (square.isAKing() && board.isSquareAtacked(finalSquare, square?.content?.color)) {
			throw new MovimentoInvalidoException("Não é possível mover o rei para a casa $finalSquare.cordinate pois ele está em xeque.")
		}
	}
	
	private isThisSquareEmpty(square) {
		
		def pieceInSquare = square.content
		def isAValidPiece = pieceInSquare instanceof ValidPiece
		(pieceInSquare == null || !isAValidPiece)
	}
	
}
