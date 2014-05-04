package org.bluebox.space2.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.bluebox.space2.game.Game;
import org.bluebox.space2.game.GameData;
import org.bluebox.space2.game.GameDataFactory;
import org.bluebox.space2.game.model.FleetModel;
import org.bluebox.space2.game.model.PlanetModel;
import org.bluebox.space2.game.model.PlayerModel;
import org.bluebox.space2.game.model.ShipClassModel;
import org.bluebox.space2.game.model.ShipModel;
import org.bluebox.space2.game.service.FightService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FightTests {

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

	private void fillFleet(FleetModel fleet, ShipClassModel classModel) {
		for (int i = 0; i < 9; i++) {
			ShipModel ship = new ShipModel(classModel);
			fleet.addShip(ship);
		}
	}

	@Test
	public void testFight() {
		ShipClassModel classModel = mData.shipClasses.get(0);
		System.out.println("testFight: " + classModel.getName());

		int awin = 0;
		int dwin = 0;
		for (int i = 0; i < 1000; i++) {
			FleetModel fleet1 = new FleetModel(mData.players.get(0));
			FleetModel fleet2 = new FleetModel(mData.players.get(1));
			fillFleet(fleet1, classModel);
			fillFleet(fleet2, classModel);
			if (FightService.getInstance().fight(fleet1, fleet2) == FightService.ATTACKER_WIN) {
				awin++;
			} else {
				dwin++;
			}
		}
		System.out.println("testFight finish: " + awin + " x " + dwin + " -> " + (awin / (float)dwin));
		
		assertEquals(4, mData.players.size());		
	}

}
