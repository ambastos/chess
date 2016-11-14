package meu.chess.builder

import java.util.regex.Pattern;

import meu.chess.Board
import meu.chess.movement.Move;

class MoveLineReader {

	
	def isValidMove = false
	def white
	def black
	
	MoveLineReader(Board board, String pgnLine) {
		
		
		def pattern1 = Pattern.compile("\\d\\.(.*)-(.*)[\\s](.*)-(.*)")
		def m =  pattern1.matcher(pgnLine.toUpperCase())
		
		if (m.find()) {
			isValidMove = true
		}else {
			throw new Exception("Arquivo PGN inválido.")		
		}
			
		def cordinate1 = m.group(1)
		cordinate1  = cordinate1.length() == 3 ? cordinate1.substring(1) : cordinate1
		
		def cordinate2 = m.group(2)
		cordinate2  = cordinate2.length() == 3 ? cordinate2.substring(1) : cordinate2
		
		def square = board.getSquareBy(cordinate1)
		def piece = square.content
		this.white = new Move(piece,cordinate1,cordinate2, false)
		
		def cordinate3 = m.group(3)
		cordinate3  = cordinate3.length() == 3 ? cordinate3.substring(1) : cordinate3
		
		def cordinate4 = m.group(4)
		cordinate4  = cordinate4.length() == 3 ? cordinate4.substring(1) : cordinate4
		
		def square1 = board.getSquareBy(cordinate3)
		def piece1 = square1.content
		this.black = new Move(piece1,cordinate3,cordinate4, false)
		
		
	} 
}
