package meu.chess

import org.junit.Before;
import org.junit.Test;

class MainTest {

	
	@Before
	void setUp() {
		
	}
	
	
	@Test
	void carregaOJogoInicialmente() {
		
		
		Main.main("partida.pgn")
		
	}
}
