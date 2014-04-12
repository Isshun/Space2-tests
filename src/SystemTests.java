import static org.junit.Assert.*;

import java.util.Random;

import org.bluebox.space2.game.Game;
import org.bluebox.space2.game.GameData;
import org.bluebox.space2.game.GameDataFactory;
import org.bluebox.space2.game.model.DeviceModel;
import org.bluebox.space2.game.model.FleetModel;
import org.bluebox.space2.game.model.GameException;
import org.bluebox.space2.game.model.PlanetClassModel;
import org.bluebox.space2.game.model.PlanetModel;
import org.bluebox.space2.game.model.PlayerModel;
import org.bluebox.space2.game.model.ShipClassModel;
import org.bluebox.space2.game.model.ShipModel;
import org.bluebox.space2.game.model.SystemModel;
import org.bluebox.space2.game.model.DeviceModel.Device;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.graphics.Color;


public class SystemTests {

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

	@Test
	public void testBase() {
		for (SystemModel s: mData.systems) {
			assertNotNull(s.getCapital());
			assertEquals(s.getRicherPlanet(), s.getCapital());
		}
	}

	@Test
	public void testCapital() {
		SystemModel sys = new SystemModel("sys", 0, 0);

		assertNull(sys.getCapital());
		sys.addPlanet(new PlanetModel(PlanetClassModel.CLASS_D, 0));
		assertNotNull(sys.getCapital());
	}


}
