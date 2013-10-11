package meu.chess;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

	static Board board = buildAChessBoard();
	
	public static void main(String[] args) {
         new TestKeyStrokes();
    }

    static class TestKeyStrokes {
    	
    	TestKeyStrokes() {
	        EventQueue.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                try {
	                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
	                }
	
	                JFrame frame = new JFrame("Testing");
	                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	                frame.setLayout(new BorderLayout());
	                frame.add(new TestPane());
	                frame.pack();
	                frame.setLocationRelativeTo(null);
	                frame.setVisible(true);
	            }
	        });
    	}    
    }

    static class TestPane extends JPanel {

		private static final long serialVersionUID = 1L;
		private JList<?> keyList;

		public TestPane() {
            setLayout(new BorderLayout());
            
            Board board = buildAChessBoard();
            System.out.print(board.draw());
            
            keyList = new JList<>();
             add(new JScrollPane(keyList));
            
            keyList.addKeyListener(new KeyListener() {

                @Override
                public void keyTyped(KeyEvent e) {
                }

                @Override
                public void keyPressed(KeyEvent e) {
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_SHIFT:
                        case KeyEvent.VK_ALT:
                        case KeyEvent.VK_CONTROL:
                        case KeyEvent.VK_ALT_GRAPH:
                        case KeyEvent.VK_META:
                            break;
                        case KeyEvent.VK_SPACE:
                        case KeyEvent.VK_UP:
                        case KeyEvent.VK_DOWN:
                        case KeyEvent.VK_LEFT:
                        case KeyEvent.VK_RIGHT:
                        	printAChessBoard(e.getKeyCode());
                            break;
                    }
                }
            });
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(100, 100);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.dispose();
        }
    }

	private static Board buildAChessBoard() {
		Board board = new Board();
		board.setApplication(true);
		board.initializeWithInitialPosition();
		return board;
	}
	
	
	private static void printAChessBoard(int teclaPressionada) {
		System.out.println(board.draw(teclaPressionada));
	}
}
