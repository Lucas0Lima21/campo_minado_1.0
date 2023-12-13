package br.com.empresaficticia.cm.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import br.com.empresaficticia.cm.exception.Explosion_Exception;

public class Game_Board {
	
	private int lines;
	private int columns;
	private int mines;
	
	private final List<Field> fields = new ArrayList<>();

	public Game_Board(int lines, int columns, int mine) {
		super();
		this.lines = lines;
		this.columns = columns;
		this.mines = mine;
		
		generateField();
		associateNeighbor();
		giveMine();
	}
	
	public void open(int line, int column) {
		try {
			fields.parallelStream()
			.filter( c -> c.getLine() == line && c.getColumn() == column)
			.findFirst()
			.ifPresent(c -> c.open());
		} catch (Explosion_Exception e) {
			fields.forEach(c -> c.setOpen(true));
			throw e;
		}
		
	}
	public void changeMarked(int line, int column) {
		fields.parallelStream()
		.filter( c -> c.getLine() == line && c.getColumn() == column)
		.findFirst()
		.ifPresent(c -> c.changeMarked());
	}

	private void generateField() {
		for (int l = 0; l < lines; l++) {
			for (int c = 0; c < columns; c++) {
				fields.add(new Field(l, c));
			}
		}
	}

	private void associateNeighbor() {
		for(Field f1: fields) {
			for(Field f2: fields) {
				f1.addNeighbor(f2);                 
			}
		}
		
	}

	private void giveMine() {
		long mineField = 0;
		Predicate<Field> minee = c ->c.isMine();
		
		do {
			int random = (int) (Math.random() * fields.size());
			fields.get(random).mine();;
			mineField = fields.stream().filter(minee).count();
		}while(mineField < mines);
	}
	
	public boolean objFinal() {
		return fields.stream().allMatch(c -> c.objAccomplished());
	}
	
	public void restart() {
		fields.stream().forEach(c -> c.restart());
		giveMine();
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("  ");
		for (int c = 0; c < columns; c++) {
			sb.append(" ");
			sb.append(c);
			sb.append(" ");
		}
		
		sb.append("\n");
		
		int i = 0;
		for (int l = 0; l < lines; l++) {
			sb.append(l);
			sb.append(" ");
			for (int c = 0; c < columns; c++) {
				sb.append(" ");				
				sb.append(fields.get(i));
				sb.append(" ");
				i++;
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
}
