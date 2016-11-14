package meu.chess.graphic



import groovy.swing.SwingBuilder

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D

import javax.swing.JFrame;
import javax.swing.JPanel

import org.junit.Test;

class GraphicDrawingTest {

	
	GraphicDrawing gd = new GraphicDrawing()
	
	@Test
	void desenharUmQuadrado() {

				
		def frm, pn 
		
		def w = new SwingBuilder().edt() {
			frm = frame(visible:false, defaultCloseOperation: JFrame.EXIT_ON_CLOSE,
						size: [500,500], locationRelativeTo: null, 
						)	
			{
				
			}
		}

		pn = new JPanel() {
			 void paintComponent(Graphics gd) {
				def g2d = (Graphics2D) gd
				g2d.draw3DRect(10, 10, 200, 20, true)
				
				def dBounds = new Rectangle(50, 50)
				def uBounds = new Rectangle2D() {
					void setRect(x, y, h, width) {
						
					};
					Rectangle2D createUnion(Rectangle2D r2d) {
						
					}
				}
				
				def xform = new AffineTransform()
				def hints = new RenderingHints() 
				g2d.paint.createContext(null, dBounds, uBounds, xform, hints)
			 }
		} 
		
		//pn.graphics.color = Color.BLACK
		//pn.graphics.draw3DRect(10, 10, 200, 20, true)
		frm.add(pn)
		
		frm.visible = true
		sleep(1000)
	}	
}
