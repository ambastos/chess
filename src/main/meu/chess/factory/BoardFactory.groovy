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
	public void movePiece(Piece content, def currentPosition, def finalPosition) {

		def square = board.squares.get(currentPosition)
		def finalSquare = board.squares.get(finalPosition)
		updateTemporallySquaresContent(currentPosition, finalPosition, content)
		verifyIfMovementIsValid(content, currentPosition, finalPosition, square)
		
		updateFinalSquareIfEnPassant(content, square, finalPosition)
		
		
		board.applyChangesOnSquares()
		board.updateListOfMovements(finalSquare.content, currentPosition, finalPosition)
		
	}

	private verifyIfMovementIsValid(Piece content, currentPosition, finalPosition, square) {
		try {
			validMovement(content, currentPosition, finalPosition, square)
			content.validMovement(square, finalPosition)
		} catch (e) {
			cancelUpdates(finalPosition, square)
			throw e	
		}
		
	}

	private cancelUpdates(finalPosition, square) {
		board.getSquareBy(finalPosition).cancelUpdate()
		square.cancelUpdate()
	}

	private updateFinalSquareIfEnPassant(Piece content, square, finalPosition) {
		if (content instanceof Pawn) { 
			Pawn pawn = content
			def finalSquare = board.getSquareBy(finalPosition)
			if (pawn.isCatchableEnPassant(content, square, finalSquare )) {
				def previousCordinate
				if (pawn.isBlack())
					previousCordinate = getNewCordinateFrom(finalSquare.cordinate, 0, 1)
				else
					previousCordinate = getNewCordinateFrom(finalSquare.cordinate, 0, -1)

				Square previousSquare = board.getSquareBy(previousCordinate)
				previousSquare.update(previousCordinate, Piece.NULL_PIECE)
			}
		}
	}
	
	private validMovement(content, currentPosition, finalPosition, square) {
		def validator = new MovementValidator(board,content,square,currentPosition, finalPosition)			
		validator.validate()		
	}
	

	private updateTemporallySquaresContent(initialPosition, finalPosition, Piece content) {
		
		Square initialSquare = board.squares.get(initialPosition)
		if (initialSquare !=null) {
			initialSquare.updateTemporally(Piece.NULL_PIECE)
			board.squares.put(initialPosition, initialSquare)
		}
		
		Square finalSquare = board.squares.get(finalPosition)
		if (finalSquare !=null) {
			finalSquare.updateTemporally(content)
			board.squares.put(finalPosition, finalSquare)
		}
		
	}

}
