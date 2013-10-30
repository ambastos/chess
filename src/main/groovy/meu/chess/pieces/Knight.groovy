package meu.chess.pieces

import static meu.chess.utils.ConvertUtil.*
import meu.chess.Board
import meu.chess.MovimentoInvalidoException

class Knight extends Piece implements ValidPiece {

	def IDENTIT = "N"
	
	Knight(def color) {
		this.simbol = IDENTIT +color
		this.color = color
	}
	
	/**
	 Movimento do cavalo possibilidades
	 Onde 
	    X=Movimento inválido
	     
	   (A10)X  _ _(C10)X 
	            |
	 		    |	 		    
	 (-1A9)X	^      (D9)X  
		  |_ _<(B8)>_ _|   
		  |	    ^  	   | 
	(-1A7)X     |      D7
	         A6_|_ C6  
	             
	 */
	def validMovement(square, finalPositionCordinate) {
		
		Board board = square.board
		if (!board.isLShape(square.cordinate, finalPositionCordinate)) {
			throw new MovimentoInvalidoException("Movimento do cavalo de $square.cordinate para $finalPositionCordinate é inválido.")
		}
		
	}
	
	@Override
	def getDescription() {
		"Cavalo ${simbol}"
	}
}
