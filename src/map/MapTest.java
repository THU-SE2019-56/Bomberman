package src.map;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.GameConstants;
import items.Item;
import player.Player;

class MapTest implements GameConstants{
	private Map m1;
	private Map m2;
	private Map m3;

	@BeforeEach
	void setUp() throws Exception {
		m1 = new Map();
		m2 = new Map(10, 6);
		m3 = new Map(new MapMatrix(16, 16));
	}

	@Test
	void testIsInMap() {
		assertTrue(m1.isInMap(0, 5));
		assertFalse(m1.isInMap(-1, 5));
		assertFalse(m1.isInMap(0, 16));
		assertFalse(m1.isInMap(-1, 16));

		assertTrue(m2.isInMap(0, 5));
		assertFalse(m2.isInMap(-1, 5));
		assertFalse(m2.isInMap(0, 6));
		assertFalse(m2.isInMap(-1, 6));

		assertTrue(m3.isInMap(0, 5));
		assertFalse(m3.isInMap(-1, 5));
		assertFalse(m3.isInMap(0, 16));
		assertFalse(m3.isInMap(-1, 16));
	}

	@Test
	void testIsAvailable() {
		assertTrue(m1.isAvailable(1, 10));
		assertFalse(m1.isAvailable(-1, 9));
		assertFalse(m1.isAvailable(0, 16));
		assertFalse(m1.isAvailable(-1, 18));

		m1.setWall(2, 5, false);
		m1.setWall(3, 8, true);
		assertFalse(m1.isAvailable(2, 5));
		assertFalse(m1.isAvailable(3, 8));
		m1.removeWall(2, 5);
		m1.removeWall(3, 8);
		assertTrue(m1.isAvailable(2, 5));
		assertTrue(m1.isAvailable(3, 8));

		m1.setBomb(4, 7, 5, new Player(m1, 1));
		assertFalse(m1.isAvailable(4, 7));
		m1.removeBomb(4, 7);
		assertTrue(m1.isAvailable(4, 7));
	}

	@Test
	void testGetSizeX() {
		assertEquals(m1.getSizeX(), 16);
		assertEquals(m2.getSizeX(), 10);
		assertEquals(m3.getSizeX(), 16);
	}

	@Test
	void testGetSizeY() {
		assertEquals(m1.getSizeY(), 16);
		assertEquals(m2.getSizeY(), 6);
		assertEquals(m3.getSizeY(), 16);
	}

	@Test
	void testSetBomb() {
		assertTrue(m1.setBomb(6, 3, 3, new Player(m1, 1)));
		assertTrue(m1.isWithBomb(6, 3));
		assertFalse(m1.setBomb(6, 3, 3, new Player(m1, 1)));

		m1.setWall(15, 0, false);
		m1.setWall(2, 3, true);
		assertFalse(m1.setBomb(2, 3, 3, new Player(m1, 1)));
		assertFalse(m1.setBomb(15, 0, 3, new Player(m1, 1)));
		assertFalse(m1.isWithBomb(2, 3));
		assertFalse(m1.isWithBomb(15, 0));
	}

	@Test
	void testRemoveBomb() {
		assertFalse(m1.removeBomb(8, 13));
		m1.setBomb(8, 13, 2, new Player(m1, 1));
		assertTrue(m1.removeBomb(8, 13));
		assertFalse(m1.isWithBomb(8, 13));
	}

	@Test
	void testIsWithBomb() {
		assertFalse(m1.isWithBomb(0, 17));
		assertFalse(m1.isWithBomb(3, 13));
		m1.setBomb(3, 13, 2, new Player(m1, 1));
		assertTrue(m1.isWithBomb(3, 13));
		m1.removeBomb(3, 13);
		assertFalse(m1.isWithBomb(3, 13));
	}

	@Test
	void testSetItem() {
		assertTrue(m1.setItem(7, 3));
		assertTrue(m1.isWithItem(7, 3));

		m1.setBomb(14, 2, 3, new Player(m1, 2));
		assertFalse(m1.setItem(14, 2));

		m1.setWall(13, 0, false);
		m1.setWall(12, 3, true);
		assertFalse(m1.setItem(13, 0));
		assertFalse(m1.setItem(12, 3));
		assertFalse(m1.isWithItem(13, 0));
		assertFalse(m1.isWithItem(12, 3));
	}

	@Test
	void testCreateItem() {
		for (int i = 0; i < 10; i++) {
			if (m3.createItem(3, i + 3))
				assertTrue(m3.isWithItem(3, i + 3));
			else
				assertFalse(m3.isWithItem(3, i + 3));
		}
	}

	@Test
	void testRemoveItem() {
		assertFalse(m1.removeItem(11, 0));
		m1.setItem(11, 0);
		assertTrue(m1.removeItem(11, 0));
		assertFalse(m1.isWithItem(11, 0));
	}

	@Test
	void testIsWithItem() {
		assertFalse(m1.isWithItem(10, 2));
		m1.setItem(10, 2);
		assertTrue(m1.isWithItem(10, 2));
		m1.removeItem(10, 2);
		assertFalse(m1.isWithItem(10, 2));
	}

	@Test
	void testGetItemID() {

		fail("Not yet implemented");
	}

	@Test
	/**
	 * Change private Cell[][] _map to public Cell[][] _map before testing.
	 */
	void testGiveItem() {
		Map map = new Map();
		Player player = new Player(map, 1);
		for(int i = 0; i < ITEM_NUM; i++) {
			map._map[0][0].setItem(new Item(0, 0));
			map._map[0][0].item.setItemID(i);
			assertEquals(true, map.giveItem(0, 0, player));
			switch(i) {
			case VELOCITY_UP:
				assertEquals(9, player.getVelocity());
				break;
			case BOMB_UP:
				assertEquals(2, player.getBombMaxNumber());
				break;
			case POWER_UP:
				assertEquals(2, player.getBombPower());
				break;
			case HP_UP:
				assertEquals(100, player.getHP());
				break;
			}
				
		}
		player.setBombMaxNumber(PLAYER_MAX_BOMB);
		map._map[0][0].setItem(new Item(0, 0));
		map._map[0][0].item.setItemID(BOMB_UP);
		assertEquals(true, map.giveItem(0, 0, player));
		assertEquals(PLAYER_MAX_BOMB, player.getBombMaxNumber());
		
		player.SetBombPower(BOMB_MAX_POWER);
		map._map[0][0].setItem(new Item(0, 0));
		map._map[0][0].item.setItemID(POWER_UP);
		assertEquals(true, map.giveItem(0, 0, player));
		assertEquals(BOMB_MAX_POWER, player.getBombPower());
		
		player.setHP(50);
		map._map[0][0].setItem(new Item(0, 0));
		map._map[0][0].item.setItemID(HP_UP);
		assertEquals(true, map.giveItem(0, 0, player));
		assertEquals(50 + HP_ADDED, player.getHP());
		
		player.setHP(90);
		map._map[0][0].setItem(new Item(0, 0));
		map._map[0][0].item.setItemID(HP_UP);
		assertEquals(true, map.giveItem(0, 0, player));
		assertEquals(100, player.getHP());
		
		assertEquals(false, map.giveItem(0, 0, player));
	}

	@Test
	void testExplosionActivate() {
		m1.explosionActivate(1, 15);
		assertTrue(m1.isAtExplosion(1, 15));
		m1.setWall(4, 12, false);
		m1.setWall(4, 14, true);
		m1.explosionActivate(4, 12);
		m1.explosionActivate(4, 14);
		assertFalse(m1.isAtExplosion(4, 12));
		assertTrue(m1.isAtExplosion(4, 14));
	}

	@Test
	void testIsAtExplosion() {
		assertFalse(m1.isAtExplosion(9, 14));
		assertFalse(m1.isAtExplosion(9, 13));
		assertFalse(m1.isAtExplosion(9, 12));
		m1.setWall(9, 14, false);
		m1.setWall(9, 13, true);
		m1.explosionActivate(9, 14);
		m1.explosionActivate(9, 13);
		m1.explosionActivate(9, 12);
		assertFalse(m1.isAtExplosion(9, 14));
		assertTrue(m1.isAtExplosion(9, 13));
		assertTrue(m1.isAtExplosion(9, 12));
		assertFalse(m1.isAtExplosion(9, 17));
	}

	@Test
	void testSetWall() {
		m2.setWall(3, 5, true);
		m2.setWall(2, 4, false);
		assertTrue(m2.isWithWall(3, 5));
		assertTrue(m2.isWithWall(2, 4));
		assertTrue(m2.isWithDestructibleWall(3, 5));
		assertTrue(m2.isWithIndestructibleWall(2, 4));
	}

	@Test
	void testRemoveWall() {
		m2.setWall(5, 5, true);
		m2.setWall(6, 4, false);
		assertTrue(m2.isWithWall(5, 5));
		assertTrue(m2.isWithWall(6, 4));
		m2.removeWall(5, 5);
		m2.removeWall(6, 4);
		assertFalse(m2.isWithWall(5, 5));
		assertFalse(m2.isWithWall(6, 4));
	}

	@Test
	void testIsWithWall() {
		assertFalse(m2.isWithWall(9, 5));
		assertFalse(m2.isWithWall(9, 3));
		m2.setWall(9, 5, true);
		m2.setWall(9, 3, false);
		assertTrue(m2.isWithWall(9, 5));
		assertTrue(m2.isWithWall(9, 3));
		m2.removeWall(9, 5);
		m2.removeWall(9, 3);
		assertFalse(m2.isWithWall(9, 5));
		assertFalse(m2.isWithWall(9, 3));
	}

	@Test
	void testIsWithDestructibleWall() {
		assertFalse(m2.isWithDestructibleWall(2, 4));
		assertFalse(m2.isWithDestructibleWall(2, 5));
		m2.setWall(2, 4, false);
		m2.setWall(2, 5, true);
		assertFalse(m2.isWithDestructibleWall(2, 4));
		assertTrue(m2.isWithDestructibleWall(2, 5));
		m2.removeWall(2, 4);
		m2.removeWall(2, 5);
		assertFalse(m2.isWithDestructibleWall(2, 4));
		assertFalse(m2.isWithDestructibleWall(2, 5));
	}

	@Test
	void testIsWithIndestructibleWall() {
		assertFalse(m2.isWithIndestructibleWall(0, 4));
		assertFalse(m2.isWithIndestructibleWall(0, 5));
		m2.setWall(0, 4, true);
		m2.setWall(0, 5, false);
		assertFalse(m2.isWithIndestructibleWall(0, 4));
		assertTrue(m2.isWithIndestructibleWall(0, 5));
		m2.removeWall(0, 4);
		m2.removeWall(0, 5);
		assertFalse(m2.isWithIndestructibleWall(0, 4));
		assertFalse(m2.isWithIndestructibleWall(0, 5));
	}

	@Test
	void testIsInExplosionRange() {
		m1 = new Map();
		m1.setBomb(7, 7, 3, new Player(m1, 2));
		assertTrue(m1.isInExplosionRange(4, 7, 3));
		assertTrue(m1.isInExplosionRange(7, 10, 3));
		assertFalse(m1.isInExplosionRange(2, 7, 3));
		assertFalse(m1.isInExplosionRange(7, 12, 3));
		assertTrue(m1.isInExplosionRange(2, 7, 5));
		assertTrue(m1.isInExplosionRange(7, 12, 5));
		
		m1.setBomb(5,7,3,new Player(m1,2));
		assertTrue(m1.isInExplosionRange(2,7,3));
		m1.setWall(7,10,false);
		assertFalse(m1.isInExplosionRange(7,12,5));
		m1.setWall(7,10,true);
		assertFalse(m1.isInExplosionRange(7,12,5));
		m1.removeWall(7,10);
		assertTrue(m1.isInExplosionRange(7,12,5));
	}

}
