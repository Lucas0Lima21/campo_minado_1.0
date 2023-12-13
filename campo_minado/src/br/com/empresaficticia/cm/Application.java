package br.com.empresaficticia.cm;

import br.com.empresaficticia.cm.model.Game_Board;
import br.com.empresaficticia.cm.vision.Game_Console;

public class Application {
	public static void main(String[] args) {
		
		Game_Board gameBoard = new Game_Board(4, 4, 4);
		new Game_Console(gameBoard);
	}

}
