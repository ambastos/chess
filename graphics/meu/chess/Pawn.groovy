package meu.chess
import groovy.swing.j2d.GraphicsOperation

class Pawn {

	static draw(def proportion) {
		
		def defaultPositionX = 50
		def defaultTopY = 40
		def defaultBotomY = 90
		def defaultLeftX = 20
		def defaultBodyEmlarge = 40
		
		def positionX = defaultPositionX * (proportion/100)
		def topY = defaultTopY* (proportion/100)
		def botomY = defaultBotomY* (proportion/100)
		def leftX = defaultLeftX* (proportion/100)
		def bodyEmlarge = defaultBodyEmlarge* (proportion/100)
		def headRadius = 10 * (proportion/100)
		def headY = 27 * (proportion/100)
		def bodyRadius = 40 * (proportion/100)
		
		def pawn = {
			antialias('on')
			
			circle(id:'head', radius: headRadius, cy:headY, cx:positionX)
			xpath( id:'top' ){
				xmoveTo( x: leftX, y: topY )
				xarcTo( x: botomY - (botomY /9), y: topY, rx: bodyRadius, ry: bodyRadius, angle: 0, sweep: true, largeArc: false )
				xhline(x:leftX)
			}
			
			path(id:'body'){
				moveTo( x: positionX, y: botomY )
				quadTo( x1: positionX - (bodyEmlarge/2), y1: botomY, x2: positionX, y2: positionX - (bodyEmlarge/2) )
				moveTo( x: positionX, y: botomY )
				quadTo( x1: positionX + (bodyEmlarge/2), y1: botomY, x2: positionX, y2: positionX - (bodyEmlarge/2) )
			}
			xpath( id:'botom' ){
				xmoveTo( x: leftX, y: botomY )
				xarcTo( x: botomY - (botomY /9), y: botomY, rx: bodyRadius, ry: bodyRadius, angle: 0, sweep: true, largeArc: false )
				xhline(x:leftX)
			}
			add(borderColor: 'white', fill: 'black', borderWidth: 2, asShape:false, name:'pawn') {
				shape(head)
				shape(body)
				shape(top)
				shape(botom)
			}
		}
		pawn
	}
}	 