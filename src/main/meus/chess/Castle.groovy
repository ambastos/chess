package meus.chess

import meu.chess.Board
import meu.chess.MovimentoInvalidoException
import meu.chess.pieces.NullPiece

class Castle {
	
	def rock
	def king
	def rockInitialSquare
	def kingInitialSquare
	
	def doIt(Board board, piece, initialPosition, finalPosition) {
		
		def columns = piece.getNumberOfMovedColumns(piece.currentSquare, finalPosition)
		
		def currentLineSquare = board.getLine(piece.currentSquare.line)
		
		def castleType = "O-O"
		def rockCordinate
		if (columns >= 2) {
			rockCordinate = currentLineSquare.get(currentLineSquare.size()-1).cordinate
		}else {
			rockCordinate = currentLineSquare.get(0).cordinate
			castleType = "O-O-O"
		}	
		def horizontal = board.getHorizontal(initialPosition, rockCordinate)
		
		def isEmpty = true
		
		def squareAtacked
			for (sq in horizontal) {
				if (sq.cordinate == rockCordinate || sq.content.isAKing() )
					continue
					
				if (!sq.isEmpty()) {
					isEmpty =  false
					break
				}
				if (board.isSquareAtacked(sq, piece.color)) {
					squareAtacked = sq
					break
				}
			}
		
			
		def rockSquare = horizontal.get(horizontal.size()-1)
		rock = rockSquare.content
		rockInitialSquare = rockSquare
		king = piece
		kingInitialSquare = piece.currentSquare
			
		def description = piece.color == Board.WHITE ? "brancas" : "negras" 			
		if (board.isThisPieceMovedBefore(king)) {
			throw new MovimentoInvalidoException("Não é possível realizar o $castleType das $description pois o rei de $kingInitialSquare.cordinate já se moveu.")
		}
		
		if (!isEmpty)
			throw new MovimentoInvalidoException("Não é possível realizar o $castleType das $description pois há peças entre o rei a torre.")
			
		
		if (!rockSquare.content.isARock()) {
			throw new MovimentoInvalidoException("Não é possível realizar o $castleType das $description pois a torre de $rockSquare.cordinate não está presente.")
		}
		if (board.isThisPieceMovedBefore(rock)) {
			throw new MovimentoInvalidoException("Não é possível realizar o $castleType das $description pois a torre de $rockSquare.cordinate já se moveu.")
		}
		
		if (squareAtacked!=null) {
			throw new MovimentoInvalidoException("Não é possível realizar o $castleType das $description pois a casa $squareAtacked.cordinate está sendo atacada.")
		}
		
		def kingCurrentSquare = king.currentSquare
		kingCurrentSquare.update(kingCurrentSquare.cordinate, new NullPiece())
		board.squares.put(kingCurrentSquare.cordinate, kingCurrentSquare)
		
		def kingNewSquare = horizontal.get(2)
		kingNewSquare.update(kingNewSquare.cordinate, king)
		board.squares.put(kingNewSquare.cordinate, kingNewSquare)
		
		//Casa inicio da torre
		rockSquare.update(rockSquare.cordinate, new NullPiece())
		board.squares.put(rockSquare.cordinate, rockSquare)
		
		def destRockSquare =  horizontal.get(1)
		destRockSquare.update(destRockSquare.cordinate, rock)
		board.squares.put(destRockSquare.cordinate, destRockSquare)
	}
}
