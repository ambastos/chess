package meu.chess.factory

import static meu.chess.Board.*
import static meu.chess.utils.ConvertUtil.*
import meu.chess.Board
import meu.chess.MovimentoInvalidoException
import meu.chess.Square
import meu.chess.pieces.King
import meu.chess.pieces.Knight;
import meu.chess.pieces.NullPiece
import meu.chess.pieces.ValidPiece
import meu.chess.pieces.Pawn;
import meu.chess.pieces.Piece;

public class BoardFactory {
	
	
	def static final BOARD_COLUMNS = ["A","B","C","D","E", "F", "G","H"]
	Board board
	
	BoardFactory(board) {
		this.board = board
	}
	public void movePiece(Piece piece, def currentPosition, def finalPosition) {

		def square = board.squares.get(currentPosition)
		
		verifyIfMovementIsValid(piece, currentPosition, finalPosition, square)
		
		updateSquaresContent(currentPosition, finalPosition, piece)
		
	}

	private verifyIfMovementIsValid(Piece piece, currentPosition, finalPosition, square) {
		validMovement(piece, currentPosition, finalPosition, square)
		piece.validMovement(square, finalPosition)
	}
	
	private validMovement(piece, currentPosition, finalPosition, square) {
		if (isThisSquareEmpty(square))
			throw new MovimentoInvalidoException("Nao há peça nessa casa ($square.cordinate)")

		def finalSquare = board.squares.get(finalPosition)
		
		if (!board.hasThisSquare(currentPosition)) 
			throw new MovimentoInvalidoException("A casa de origem $currentPosition é inválida.")
			
		if (!board.hasThisSquare(finalPosition))
			throw new MovimentoInvalidoException("A casa de destino $finalPosition é inválida.")
		
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
			throw new MovimentoInvalidoException("Não é possível mover o rei para a casa $finalSquare.cordinate pois ela está em xeque.")
		}			
				
	}

	private isThisSquareEmpty(square) {
		def pieceInSquare = square.content
		def isAValidPiece = pieceInSquare instanceof ValidPiece
		(pieceInSquare == null || !isAValidPiece)
	}

	private updateSquaresContent(initialPosition, finalPosition, Piece piece) {
		
		def initialSquare = board.squares.get(initialPosition)
		initialSquare.update(initialPosition, new NullPiece())		
		board.squares.put(initialPosition, initialSquare)
		
		def finalSquare = board.squares.get(finalPosition)
		finalSquare.update(finalPosition, piece)
		board.squares.put(finalPosition, finalSquare)
	}

}
