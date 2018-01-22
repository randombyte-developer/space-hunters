package de.fragstyle.spacehunters.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;
import de.fragstyle.spacehunters.client.listeners.Listeners;
import de.fragstyle.spacehunters.common.KryoUtils;
import de.fragstyle.spacehunters.common.Player;
import de.fragstyle.spacehunters.common.packets.login.LoginRequest;
import java.io.IOException;
import java.util.UUID;

public class SpaceHuntersClient extends ApplicationAdapter {
	SpriteBatch batch;

  private final Client client = new Client();

  public static final String TAG = "SpaceHuntersClient";

  @Override
	public void create () {
		batch = new SpriteBatch();

    KryoUtils.prepareKryo(client.getKryo());
    Listeners.registerListeners(client);

		client.start();
    try {
      client.connect(5000, "localhost", 9880, 9881);
    } catch (IOException e) {
      e.printStackTrace();
      Gdx.app.exit();
    }
    client.sendTCP(new LoginRequest(new Player(UUID.randomUUID(), "Player1")));
  }

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
    try {
      client.dispose();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
