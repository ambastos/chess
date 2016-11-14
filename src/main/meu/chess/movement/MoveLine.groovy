package meu.chess.movement

class MoveLine {

	def moveNumber
	def Move white
	def Move black
	
	String toString() {
		def moves = (white.piece.isPawn() ? "" : white.piece.IDENTIT)+white.finalPosition+(white.isEnemyKingInCheck ? "+": "")
		if (black)
			moves += " "+(black.piece.isPawn() ? "" : black.piece.IDENTIT)+black.finalPosition+(black.isEnemyKingInCheck ? "+": "")
		moveNumber+": "+moves
	}
}
