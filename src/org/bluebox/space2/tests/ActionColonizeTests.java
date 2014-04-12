package org.bluebox.space2.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Random;

import org.bluebox.space2.game.Game;
import org.bluebox.space2.game.GameData;
import org.bluebox.space2.game.GameDataFactory;
import org.bluebox.space2.game.model.DeviceModel.Device;
import org.bluebox.space2.game.model.FleetModel;
import org.bluebox.space2.game.model.GameException;
import org.bluebox.space2.game.model.PlanetClassModel;
import org.bluebox.space2.game.model.PlanetModel;
import org.bluebox.space2.game.model.PlayerModel;
import org.bluebox.space2.game.model.ShipClassModel;
import org.bluebox.space2.game.model.ShipModel;
import org.bluebox.space2.game.model.SystemModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.graphics.Color;

public class ActionColonizeTests {

	private GameData mData;

	@Before
	public void setUp() throws Exception {
		Game.sRandom = new Random(42);
		mData = new GameData();
		GameDataFactory.initSystems(mData);
	}

	@After
	public void tearDown() throws Exception {
	}

	private FleetModel getBasicFleet() {
		ShipClassModel sc = new ShipClassModel("testclass", 42);
		FleetModel fleet = new FleetModel();
		fleet.addShip(new ShipModel(sc));
		return fleet;
	}

	private PlayerModel getPlayerWithNoPlanet() {
		PlayerModel player = new PlayerModel("player", Color.RED, Color.BLUE, false);
		return player;
	}

	private void addColonizer(PlayerModel player) {
		FleetModel fleet = getBasicFleet();
		fleet.getShips().get(0).getShipClass().addDevice(Device.COLONIZER);
		player.addFleet(fleet);
	}

	private void addBasicFleet(PlayerModel player) {
		player.addFleet(getBasicFleet());
	}

	@Test(expected=GameException.class)
	public void testColonizeSystemWithNoPlanet() {
		PlayerModel player = getPlayerWithNoPlanet();
		addColonizer(player);

		// Try to colonize system with no planet
		SystemModel sys = new SystemModel("sys", 0, 0);
		sys.addFleet(player.getFleets().get(0));
		sys.colonize(player);
	}

	@Test(expected=GameException.class)
	public void testColonizeSystemWithoutColonizer() {
		PlayerModel player = getPlayerWithNoPlanet();
		addBasicFleet(player);

		SystemModel sys = new SystemModel("sys", 0, 0);
		sys.addPlanet(new PlanetModel(PlanetClassModel.CLASS_T, 0));
		sys.addFleet(player.getFleets().get(0));
		sys.colonize(player);
	}

	@Test
	public void testColonizerIsDestroyAfterColonize() {
		PlayerModel player = getPlayerWithNoPlanet();
		addColonizer(player);

		SystemModel sys = new SystemModel("sys", 0, 0);
		sys.addPlanet(new PlanetModel(PlanetClassModel.CLASS_T, 0));
		sys.addFleet(player.getFleets().get(0));

		int nbShip = player.getFleets().get(0).getNbShip();
		sys.colonize(player);
		assertEquals(nbShip - 1, player.getFleets().get(0).getNbShip());
	}

	@Test
	public void testColonize() {
		PlayerModel player = getPlayerWithNoPlanet();
		addColonizer(player);

		SystemModel sys = new SystemModel("sys", 0, 0);
		sys.addPlanet(new PlanetModel(PlanetClassModel.CLASS_T, 0));
		sys.addFleet(player.getFleets().get(0));
		
		assertNull(sys.getOwner());
		sys.colonize(player);
		assertEquals(player, sys.getOwner());
	}

}
