package meu.chess

import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import groovy.swing.SwingBuilder;
import groovy.swing.factory.FrameFactory;
import groovy.swing.j2d.GraphicsBuilder
import groovy.swing.j2d.GraphicsPanel
import groovy.swing.j2d.event.GraphicsInputEvent;
import groovy.swing.j2d.factory.ColorFactory;
import groovy.swing.j2d.operations.Transformation
import groovy.swing.j2d.operations.TransformationGroup
import groovy.swing.j2d.operations.misc.GroupGraphicsOperation;
import groovy.swing.j2d.operations.misc.ShapeGraphicsOperation;
import groovy.swing.j2d.operations.shapes.AreaGraphicsOperation;
import groovy.swing.j2d.operations.shapes.RectGraphicsOperation;
import groovy.swing.j2d.operations.shapes.path.HLinePathOperation;
import groovy.swing.j2d.operations.shapes.path.ShapePathOperation;
import groovy.swing.j2d.operations.transformations.TranslateTransformation;

class MainClass {

	/**
	 * @param args
	 */
	/**
	 * @param args
	 */
	static void main(String[] args) {
		
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
			
			(0..7).each { line ->
				y = (line * squareHeight)+defaultY
				(0..7).each {column ->
					x = (column * squareWidth) + defaultX  
					rect( x: x, y: y, width: squareWidth, height: squareHeight, 
						fill: color ? 'white' : 'magenta',
						mouseEntered: focus, mouseExited: exit
					)
					color = !color	 
				}
				color = !color
			}
				
		}
		
		Board board = new Board();
		board.setApplication(true);
		board.initializeWithInitialPosition();
		
		def bg = new BoardGraphic(board)
		GroupGraphicsOperation group= bg.draw()
		
		def swing = SwingBuilder.build {
			frame( title: "Chess Board", size: [800,800],
				locationRelativeTo: null, show: true, defaultCloseOperation: JFrame.EXIT_ON_CLOSE ){
				panel( new GraphicsPanel(), graphicsOperation: group)
				desktopPane()
				
			}
				
		}
	}
}
