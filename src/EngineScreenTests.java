import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.bluebox.space2.engine.MockArtManager;
import org.bluebox.space2.engine.screen.BaseScreenLayer;
import org.bluebox.space2.game.Constants;
import org.bluebox.space2.game.Game;
import org.bluebox.space2.game.model.SystemModel;
import org.bluebox.space2.game.screen.SpaceScreen;
import org.bluebox.space2.game.screen.SystemScreen;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;


public class EngineScreenTests {

	private Game mGame;

	@Before
	public void setUp() throws Exception {
		BaseScreenLayer.isDebug = true;
		mGame = new Game(new MockArtManager());
		new LwjglApplication(mGame, "Space2", Constants.GAME_WIDTH * Constants.SCREEN_SCALE, Constants.GAME_HEIGHT * Constants.SCREEN_SCALE);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testInit() {
		mGame.create();
		
		// init -> space screen
		assertTrue(mGame.getScreen() instanceof SpaceScreen);
		assertTrue(mGame.getScreen().isTop());
		assertEquals(0, mGame.getHistoryScreen().size());
		
		SystemModel sys = new SystemModel("testsystem", 0, 0);
		mGame.addScreen(new SystemScreen(sys));

		// add screen
		assertTrue(mGame.getScreen() instanceof SystemScreen);
		assertEquals(1, mGame.getHistoryScreen().size());
		assertTrue(mGame.getHistoryScreen().get(0) instanceof SpaceScreen);
		assertFalse(mGame.getHistoryScreen().get(0).isTop());
		
		// go back
		mGame.goBack();
		assertTrue(mGame.getScreen() instanceof SpaceScreen);
		assertTrue(mGame.getScreen().isTop());
		assertEquals(0, mGame.getHistoryScreen().size());
	}

}
