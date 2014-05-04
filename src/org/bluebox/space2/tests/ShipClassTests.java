package org.bluebox.space2.tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.bluebox.space2.game.model.DeviceModel;
import org.bluebox.space2.game.model.FleetModel;
import org.bluebox.space2.game.model.GameException;
import org.bluebox.space2.game.model.PlayerModel;
import org.bluebox.space2.game.model.ShipClassModel;
import org.bluebox.space2.game.model.ShipModel;
import org.bluebox.space2.game.model.SystemModel;
import org.bluebox.space2.game.model.TravelModel;
import org.bluebox.space2.game.model.DeviceModel.Device;
import org.bluebox.space2.game.model.FleetModel.Action;
import org.bluebox.space2.path.PathResolver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.graphics.Color;


public class ShipClassTests {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBase() {
		ShipClassModel sc = new ShipClassModel("testclass", 42);

		assertEquals(42, sc.getHull());
		assertTrue(sc.getDevices().isEmpty());
	}

	@Test
	public void testColonizer() {
		PlayerModel player = new PlayerModel("player", Color.RED, Color.BLUE, false);
		ShipClassModel sc = new ShipClassModel("testclass", 42);
		FleetModel f = new FleetModel(player);
		ShipModel s = new ShipModel(sc);
		SystemModel sys = new SystemModel("sys", 0, 0);
		sys.addFleet(f);
		f.addShip(s);
		
		assertFalse(s.hasDevice(Device.COLONIZER));
		assertFalse(f.hasDevice(Device.COLONIZER));

		sc.addDevice(DeviceModel.get(Device.COLONIZER));
		
		assertTrue(s.hasDevice(Device.COLONIZER));
		assertTrue(f.hasDevice(Device.COLONIZER));
	}

	@Test
	public void testWeapon() {
		ShipClassModel sc = new ShipClassModel("testclass", 42);
		ShipModel s = new ShipModel(sc);
		
		assertEquals(0, (int)s.getAttackIndice());

		sc.addDevice(Device.PHASER_1);

		s = new ShipModel(sc);
		assertTrue(s.getAttackIndice() > 0);
	}

	@Test
	public void testSystem() {
		PlayerModel player = new PlayerModel("player", Color.RED, Color.BLUE, false);
		ShipClassModel sc = new ShipClassModel("testclass", 42);
		ShipModel s = new ShipModel(sc);
		FleetModel f1 = new FleetModel(player);
		SystemModel sys = new SystemModel("testsystem", 12, 24);
		assertEquals(0, sys.getFleets().size());

		sys.addFleet(f1);

		f1.addShip(s);
		assertEquals(1, sys.getFleets().size());
		assertEquals(sys, f1.getLocation());
		assertEquals(sys.getName(), f1.getLocationName());
		
		FleetModel f2 = new FleetModel(player);
		f2.setLocation(sys);
		f2.addShip(s);
		
		assertEquals(0, f1.getNbShip());
		assertEquals(1, f2.getNbShip());
	}
	
//	@Test(expected=GameException.class)
//	public void testAddShipOnFleetWithDifferentLocation() {
//		SystemModel sys1 = new SystemModel("1", 0, 0);
//		SystemModel sys2 = new SystemModel("2", 1, 1);
//		ShipClassModel sc = new ShipClassModel("testclass", 42);
//		ShipModel s = new ShipModel(sc);
//		s.setLocation
//		FleetModel f = new FleetModel();
//		f.addShip(s);
//	}

	@Test
	public void testMove() {
		PlayerModel player = new PlayerModel("player", Color.RED, Color.BLUE, false);
		ShipClassModel sc = new ShipClassModel("testclass", 42);
		ShipModel s = new ShipModel(sc);
		FleetModel f = new FleetModel(player);
		SystemModel sys = new SystemModel("sys", 0, 0);
		sys.addFleet(f);
		f.addShip(s);
		
		assertEquals(Action.NONE, f.getAction());
		
		SystemModel sys1 = new SystemModel("sys1", 10, 10);
		SystemModel sys2 = new SystemModel("sys2", 20, 20);
		List<SystemModel> systems = new ArrayList<SystemModel>();
		systems.add(sys1);
		systems.add(sys2);
		
		List<TravelModel> travels = new ArrayList<TravelModel>();
		travels.add(new TravelModel(sys1, sys2));
		
		PathResolver.getInstance().init(systems, travels);
		
		f.setLocation(sys1);
		assertEquals(Action.NONE, f.getAction());

		f.setCourse(sys2);
		assertEquals(Action.MOVE, f.getAction());
		assertEquals(1, f.getPath().size());
		assertEquals(1, f.getPathStep());

		f.move();
		assertEquals(Action.MOVE, f.getAction());
		assertEquals(0, f.getPath().size());
		assertEquals(0, f.getPathStep());
		
		f.move();
		assertEquals(Action.NONE, f.getAction());
		assertEquals(sys2, f.getLocation());
	}

}
