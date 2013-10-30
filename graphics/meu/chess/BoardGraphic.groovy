package meu.chess

import groovy.swing.j2d.GraphicsBuilder
import static meu.chess.utils.ConvertUtil.*

class BoardGraphic {

	Board board
	BoardGraphic(def board) {
		board = new Board();
		board.setApplication(true);
		board.initializeWithInitialPosition();
	}
	
	def draw() {
		def gb = new GraphicsBuilder()
		
		def squareWidth = 90
		def squareHeight = 90
		
		def defaultX = 10
		def defaultY = 10
		
		def x
		def y
		def color = true
				
		def focus = { e ->
			  def target = e.target
			  target.borderColor = 'red'
			  target.borderWidth = 4
			  
		}
		def exit = { e -> e.target.borderColor = false  }
		
		
		def script ={
			
			(1..Board.MAX_NUMBER_OF_LINES).each { column ->
				y = ((column-1)  * squareHeight)+defaultY
				(1..Board.MAX_NUMBER_OF_COLUMNS).each {line ->
					x = ((line-1) * squareWidth) + defaultX
					
					def cordinate = getColumnSimbolFromNumber(column)+line
					
					rect( x: x, y: y, width: squareWidth, height: squareHeight,
						fill: color ? 'white' : 'magenta', name: cordinate,
						mouseEntered: focus, mouseExited: exit
					)
					color = !color
				}
				color = !color
			}
			
		}
		def group = gb.group(script)
		group
	}
}
