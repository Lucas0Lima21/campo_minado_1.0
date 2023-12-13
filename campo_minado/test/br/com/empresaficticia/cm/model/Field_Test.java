package br.com.empresaficticia.cm.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.empresaficticia.cm.exception.Explosion_Exception;

public class Field_Test {
	
	private Field field;
	
	@BeforeEach
	void onField() {
		field = new Field(3,3);
	}
	
	@Test
	void testNeighbor() {
		Field neighbor = new Field(3,2);
		boolean result = field.addNeighbor(neighbor);
		
		assertTrue(result);
	}
	@Test
	void testNeighbor2() {
		Field neighbor = new Field(2,2);
		boolean result = field.addNeighbor(neighbor);
		
		assertTrue(result);
	}
	@Test
	void testNeighbor3() {
		Field neighbor = new Field(3,4);
		boolean result = field.addNeighbor(neighbor);
		
		assertTrue(result);
	}
	@Test
	void testNeighborFalse() {
		Field neighbor = new Field(2,5);
		boolean result = field.addNeighbor(neighbor);
		
		assertFalse(result);
	}
	@Test
	void testmarkedFalse() {
		assertFalse(field.isMarked());
	}
	@Test
	void testmarked() {
		field.changeMarked();
		assertTrue(field.isMarked());
	}
	@Test
	void testmarkedFalsee() {
		field.changeMarked();
		field.changeMarked();
		assertFalse(field.isMarked());
	}
	@Test
	void testOpenField() {
		assertTrue(field.open());
	}
	@Test
	void testOpenField_marked() {
		field.changeMarked();
		assertFalse(field.open());
	}
	@Test
	void testOpenField_mine() {
		field.mine();
		assertThrows(Explosion_Exception.class, () -> { field.open(); });
	}
	@Test
	void testOpenField_neighbor() {
		Field neighbor1 = new Field(1, 1);
		Field neighbor2 = new Field(2, 2);
		
		neighbor1.addNeighbor(neighbor2);
		
		field.addNeighbor(neighbor1);
		
		field.open();
		
		assertTrue(neighbor2.open() && neighbor1.open());
	}
	@Test
	void testOpenField_neighbor2() {
		Field neighbor1 = new Field(1, 1);
		Field neighbor2 = new Field(2, 2);
		neighbor2.mine();
		neighbor1.addNeighbor(neighbor2);
		
		field.addNeighbor(neighbor1);
		
		field.open();
		
		assertTrue(neighbor2.isOff() && neighbor1.isOpen());}
	
}
