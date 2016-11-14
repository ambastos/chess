package meu.chess.movement

class Move {
		
		def piece
		def initialPosition
		def finalPosition
		def isEnemyKingInCheck
		Move(piece, initialPosition, finalPosition, isEnemyKingInCheck) {
			this.piece = piece
			this.initialPosition = initialPosition
			this.finalPosition = finalPosition
			this.isEnemyKingInCheck = isEnemyKingInCheck
		}
}
