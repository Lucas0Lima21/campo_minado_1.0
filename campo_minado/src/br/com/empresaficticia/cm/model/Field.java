package br.com.empresaficticia.cm.model;

import java.util.ArrayList;
import java.util.List;

import br.com.empresaficticia.cm.exception.Explosion_Exception;

public class Field {
	
	private final int line;
	private final int column;
	
	private boolean open = false;
	private boolean mine = false;
	private boolean marked = false;
	
	private List<Field> neighbors = new ArrayList<>();
	
	public Field(int line, int column) {
		this.line = line;
		this.column = column;
	}
	
	boolean addNeighbor(Field neighbor) {
		boolean lineDifferent = line != neighbor.line;
		boolean columnDifferent = column != neighbor.column;
		boolean diagonal = lineDifferent && columnDifferent;
		
		
		int  deltaLine = Math.abs(line - neighbor.line);
		int  deltaCollumn = Math.abs(column - neighbor.column);
		int  deltaGerenal = deltaLine + deltaCollumn;
		
		if(deltaGerenal == 1 && !diagonal) {
			neighbors.add(neighbor);
			return true;
		} else if(deltaGerenal == 2 && diagonal ) {
			neighbors.add(neighbor);
			return true;	
		}else {
			return false;
		}
	}
	 void changeMarked() {
		 if(!open) {
			 marked = !marked;
		 }
	 }
	 
	void setOpen(boolean open) {
		this.open = open;
	}

	boolean open() {
		 if(!open && !marked) {
			 open = true;
			 if(mine) {
				 throw new Explosion_Exception(); 
			 }
			 if(safeNeighbors()) {
				 neighbors.forEach(v -> v.open());
			 }
			 return true;
		 }else {
			 return false;			 
		 }
	 }
	 
	 boolean safeNeighbors() {
		 return neighbors.stream().noneMatch(v -> v.mine);
	 }
	 
	 void mine() {
		 mine = true;
	 }
	 
	 public boolean isMine() {
		 return mine;
	 }
	 public boolean isMarked() {
		 return marked;
	 }
	 
	 public boolean isOpen() {
		 return open;
	 }
	 
	 public boolean isOff() {
		 return !isOpen();
	 }
	 
	 boolean objAccomplished() {
		 boolean unveiled = !mine && open;
		 boolean protectedd = mine && marked;
		 return protectedd || unveiled;
	 }
	 
	 long mineNeighbor() {
		 return neighbors.stream().filter(v ->  v.mine).count();
	 }
	 
	 void restart(){
		 open = false;
		 mine = false;
		 marked = false;
	 }
	 
	 public String toString() {
		 if(marked) {
			 return "x";
		 }else if(open && mine) {
			 return "*";
		 }else if(open && mineNeighbor() > 0) {
			 return Long.toString(mineNeighbor());
		 }else if(open ) {
			 return " ";
		 }else {
			 return "?";
		 }
	 }
	 
	 public int getColumn() {

		 return column;

		 }



		 public int getLine() {

		 return line;

		 }


}
