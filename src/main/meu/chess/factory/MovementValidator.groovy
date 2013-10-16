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
		throw new MovimentoInvalidoException("Nao h� pe�a nessa casa ($square.cordinate)")

		def finalSquare = board.squares.get(finalPosition)
		if (!board.hasThisSquare(currentPosition))
			throw new MovimentoInvalidoException("A casa de origem $currentPosition � inv�lida.")
			
		if (!board.hasThisSquare(finalPosition))
			throw new MovimentoInvalidoException("A casa de destino $finalPosition � inv�lida.")
		
		if (board.isApplication() && board.getLastMovedPiece()?.color == piece.color) {
			def strColor = piece.isWhite() ? "brancas" : "pretas"
			throw new MovimentoInvalidoException("N�o � a vez das $strColor.")
		}
		
		if (!piece.isTheSameColorAndType(square.content)) {
			throw new MovimentoInvalidoException("N�o h� a pe�a ($piece) desse tipo na casa informada.")
		}
			
		if (finalSquare == null)
			throw new MovimentoInvalidoException("A casa de destino $finalPosition da pe�a $square.content.description � inv�lida.")
	
		if (currentPosition == finalPosition) {
			def d = square.content.description
			throw new MovimentoInvalidoException("A pr�pria pe�a($d) n�o pode se mover para a mesma casa.")
		}
	
		def pieceInSquare = square.content
		if (finalSquare.hasPieceOfSameColor(pieceInSquare) )
			throw new MovimentoInvalidoException("Movimento da pe�a $square.content.description n�o � valido devido a presen�a de $finalSquare.content.description na casa $finalPosition.")
				
		if (!square.isAKing() && board.isThereKingInCheck(square.content.color)) {
			def king = board.getKingFromColor(square.content.color)
			def kingCordinate = king.currentSquare.cordinate
			throw new MovimentoInvalidoException("N�o � poss�vel mover a pe�a $square.content.description de $currentPosition para $finalPosition enquanto o rei em $kingCordinate se encontra em xeque.")
		}
	
		if (square.isAKing() && board.isSquareAtacked(finalSquare, square?.content?.color)) {
			throw new MovimentoInvalidoException("N�o � poss�vel mover o rei para a casa $finalSquare.cordinate pois ele est� em xeque.")
		}
	}
	
	private isThisSquareEmpty(square) {
		
		def pieceInSquare = square.content
		def isAValidPiece = pieceInSquare instanceof ValidPiece
		(pieceInSquare == null || !isAValidPiece)
	}
	
}
