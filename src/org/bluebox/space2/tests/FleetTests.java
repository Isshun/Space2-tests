package org.bluebox.space2.tests;
import static org.junit.Assert.*;

import java.util.Random;

import org.bluebox.space2.game.Game;
import org.bluebox.space2.game.GameData;
import org.bluebox.space2.game.GameDataFactory;
import org.bluebox.space2.game.model.FleetModel;
import org.bluebox.space2.game.model.ShipClassModel;
import org.bluebox.space2.game.model.ShipModel;
import org.bluebox.space2.game.model.SystemModel;
import org.bluebox.space2.path.PathResolver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class FleetTests {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFleet() {
		Game.sRandom = new Random(42);
		GameData data = new GameData();
		GameDataFactory.initGame(data);

		PathResolver.getInstance().init(data.systems, data.travelLines);
	}

}
