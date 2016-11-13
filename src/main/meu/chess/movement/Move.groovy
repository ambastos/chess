package meu.chess.movement

class Move {
		
		def piece
		def initialPosition
		def finalPosition
		
		Move(piece, initialPosition, finalPosition) {
			this.piece = piece
			this.initialPosition = initialPosition
			this.finalPosition = finalPosition
		}
}
