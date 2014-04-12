package org.bluebox.space2.tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.bluebox.space2.game.Game;
import org.bluebox.space2.game.GameData;
import org.bluebox.space2.game.GameDataFactory;
import org.bluebox.space2.game.model.PlanetModel;
import org.bluebox.space2.game.model.PlayerModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class PlayerTests {

	private GameData mData;

	@Before
	public void setUp() throws Exception {
		Game.sRandom = new Random(42);
		mData = new GameData();
		GameDataFactory.initGame(mData);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNbPlayer() {
		assertEquals(4, mData.players.size());		
	}

	@Test
	public void testDefaultFleet() {
		for (PlayerModel p: mData.players) {
			assertEquals(3, p.getFleets().size());		
		}
	}

	@Test
	public void testCapitalPlanetIsRicher() {
		for (PlayerModel p: mData.players) {
			int richer = 0;
			for (PlanetModel planet: p.getPlanets()) {
				richer = planet.getIndice() > richer ? planet.getIndice() : richer;
			}
			assertEquals(richer, p.getHome().getIndice());		
		}
	}

	@Test
	public void testHomeSystemAre3PlanetOrMore() {
		for (PlayerModel p: mData.players) {
			assertTrue(p.getHome().getSystem().getPlanets().size() >= 3);		
		}
	}

}
