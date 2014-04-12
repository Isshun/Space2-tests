package org.bluebox.space2.tests;
import java.util.Random;

import org.bluebox.space2.game.Game;
import org.bluebox.space2.game.GameData;
import org.bluebox.space2.game.GameDataFactory;
import org.bluebox.space2.path.PathResolver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class GameTests {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testInit() {
		Game.sRandom = new Random(42);
		GameData data = new GameData();
		GameDataFactory.initGame(data);

		PathResolver.getInstance().init(data.systems, data.travelLines);
	}

}
