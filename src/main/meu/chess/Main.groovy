package meu.chess

import groovy.swing.SwingBuilder
import groovy.transform.CompileStatic;

import java.awt.BorderLayout
import java.awt.Font
import java.awt.GraphicsEnvironment
import java.awt.event.*

import javax.swing.JFileChooser;
import javax.swing.JFrame
import javax.swing.event.*
import javax.swing.filechooser.FileNameExtensionFilter

import meu.chess.factory.BoardFactory
import org.junit.After;

class Main extends Thread {
	
	static main(args) {
		new Main(args?.length > 0  ? args[0] : null )
	}
	
	Main(def arquivoPgn) {
		
		def cp = GraphicsEnvironment.getLocalGraphicsEnvironment().centerPoint
		
		def size = [520,520]
		cp.setLocation( (cp.x - (size[0]/2) ), (cp.y - (size[1]/2)))
		
		Board board = new Board();
		board.setApplication(true);
		board.initializeWithInitialPosition()
		if (arquivoPgn)
			board.loadGame(arquivoPgn)
		
		def font = new Font(Font.MONOSPACED, Font.PLAIN, 15)
		
		def lateralBar, statusBar, frm	
		def ff= new FileNameExtensionFilter("Ecolha um arquivo PGN", "PGN")
		
		def area
		def window = new SwingBuilder().edt(){
			frm = frame(name: "Chess", defaultCloseOperation: JFrame.EXIT_ON_CLOSE,
				  visible:true, location: cp, size: size
				)
			{
				panel{
					borderLayout()
					menu(text:"CarregarPgn",
						constraints: BorderLayout.NORTH
					).addMouseListener(
							[mouseClicked: { e->
								frame(name: "Escolha um arquivo pgn", size:[600,400], visible: true)
								{
									fileChooser(
										fileFilter:ff
									).addActionListener(new ActionListener() {
										void actionPerformed(ActionEvent event) {
											if (event.actionCommand.equals(JFileChooser.APPROVE_SELECTION)) {
												if (event.source.selectedFile) {
													board.loadGame(event.source.selectedFile)
												}
											}
											event.source.rootPane.parent.visible = false
										}
									})
								}
							}
							,mousePressed:{ e->}
							,mouseReleased:{e->}
							,mouseEntered:{e->}
							,mouseExited:{e->},
							] 
							 as MouseListener
						)
					area = textArea(constraints: BorderLayout.CENTER,
							font: font ,
							editable: false, focusable: true
						) 
					lateralBar = textArea(constraints: BorderLayout.EAST, text: "",
									      font: font, columns:15, rows:20, lineWrap:true,
										  focusable: false, editable: false)
					
					statusBar =textArea(text:"BBBB", constraints:BorderLayout.SOUTH,
							editable: false, rows: 5, lineWrap: true, focusable: false	
						)
					area.addKeyListener(
						[
						 keyTyped: { ev->},
						 keyPressed: { ev-> 
							 try {
								 str = board.draw(ev.keyCode)
								 statusBar.text = ""
							 }catch (Exception e){
								 str = board.draw()
								 statusBar.text = e.getMessage()
								 throw e;
							 }finally {
								 area.text =  str
								 def line =""
								 for (moveLine in board.moveLines) {
									 line+=moveLine.toString()+"\n"
								 }
								 lateralBar.text = line 
							 }
						 },
						 keyReleased: { ev-> },
						]  as KeyListener
						)
				}
				
			}
		}
		
		area.text = board.draw()
		
	}
	

	def writeInstructions(def lateralBar, numberOfWhiteLines, ...message) {
		def whiteLine = ""
		(1..numberOfWhiteLines).each {
			(1..15).each {
				whiteLine+=" "
			}
		}	
		lateralBar.insert(whiteLine+message,0)
	}
}
