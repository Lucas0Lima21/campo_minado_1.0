package br.com.empresaficticia.cm.vision;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.empresaficticia.cm.exception.Explosion_Exception;
import br.com.empresaficticia.cm.exception.Go_Out_Expecption;
import br.com.empresaficticia.cm.model.Game_Board;

public class Game_Console {

		private Game_Board gameBoard;
		private Scanner entrada = new Scanner(System.in);
		
		public Game_Console(Game_Board gameBoard) {
			this.gameBoard = gameBoard;
			
			runGame();
		}

		private void runGame() {
			try {
				boolean continuee = true;
				
				while(continuee) {
					gameCycle();
					
					System.out.println("Another game? (S/n) ");
					String answer = entrada.nextLine();
					
					if("n".equalsIgnoreCase(answer)) {
						continuee = false;
					} else {
						gameBoard.restart();
					}
				}
			} catch (Go_Out_Expecption e) {
				System.out.println("Tchau!!!");
			} finally {
				entrada.close();
			}
		}

		private void gameCycle() {
			try {
				
				while(!gameBoard.objFinal()) {
					System.out.println(gameBoard);
					
					String typed = valuesTyping("Type (x, y): ");
					
					Iterator<Integer> xy = Arrays.stream(typed.split(","))
						.map(e -> Integer.parseInt(e.trim())).iterator();
					
					typed = valuesTyping("1 - Open or 2 - (Des)Marcar: ");
					
					if("1".equals(typed)) {
						gameBoard.open(xy.next(), xy.next());
					} else if("2".equals(typed)) {
						gameBoard.changeMarked(xy.next(), xy.next());
					}
				}
				
				System.out.println(gameBoard);
				System.out.println("you won!!!");
			} catch(Explosion_Exception e) {
				System.out.println(gameBoard);
				System.out.println("you lost");
			}
		}
		
		private String valuesTyping(String text) {
			System.out.print(text);
			String typing = entrada.nextLine();
			
			if("sair".equalsIgnoreCase(typing)) {
				throw new Go_Out_Expecption();
			}
			
			return typing;
		}

}
