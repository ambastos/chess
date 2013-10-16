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
		updateTemporallySquaresContent(currentPosition, finalPosition, piece)
		verifyIfMovementIsValid(piece, currentPosition, finalPosition, square)
		updateFinalSquareIfEnPassant(piece, square, finalPosition)
		
		board.applyChangesOnSquares()
	}

	private verifyIfMovementIsValid(Piece piece, currentPosition, finalPosition, square) {
		validMovement(piece, currentPosition, finalPosition, square)
		piece.validMovement(square, finalPosition)
	}

	private updateFinalSquareIfEnPassant(Piece piece, square, finalPosition) {
		if (piece instanceof Pawn) { 
			Pawn pawn = piece
			def finalSquare = board.getSquareBy(finalPosition)
			if (pawn.isCatchableEnPassant(piece, square, finalSquare )) {
				def previousCordinate
				if (pawn.isBlack())
					previousCordinate = getNewCordinateFrom(finalSquare.cordinate, 0, 1)
				else
					previousCordinate = getNewCordinateFrom(finalSquare.cordinate, 0, -1)

				Square previousSquare = board.getSquareBy(previousCordinate)
				previousSquare.update(previousCordinate, new NullPiece())
			}
		}
	}
	
	private validMovement(piece, currentPosition, finalPosition, square) {
		def validator = new MovementValidator(board,piece,square,currentPosition, finalPosition)			
		validator.validate()		
	}
	

	private updateTemporallySquaresContent(initialPosition, finalPosition, Piece piece) {
		
		Square initialSquare = board.squares.get(initialPosition)
		if (initialSquare !=null) {
			initialSquare.updateTemporally(new NullPiece())
			board.squares.put(initialPosition, initialSquare)
		}
		
		Square finalSquare = board.squares.get(finalPosition)
		if (finalSquare !=null) {
			finalSquare.updateTemporally(piece)
			board.squares.put(finalPosition, finalSquare)
		}
		
	}

}
