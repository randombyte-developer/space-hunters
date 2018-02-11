package de.fragstyle.spacehunters.client.drawing;

import static de.fragstyle.spacehunters.client.SpaceHuntersClientGame.TAG;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.esotericsoftware.kryonet.Client;
import de.fragstyle.spacehunters.client.ConnectDialog;
import de.fragstyle.spacehunters.client.SpaceHuntersClientGame;
import de.fragstyle.spacehunters.common.Constants;
import de.fragstyle.spacehunters.common.drawing.GameAwareScreenAdapter;
import de.fragstyle.spacehunters.common.packets.client.LoginRequest;
import de.fragstyle.spacehunters.common.packets.server.Player;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class JoinServerScreen extends GameAwareScreenAdapter<SpaceHuntersClientGame> {

  private final Client client;

  private AsyncExecutor asyncExecutor = new AsyncExecutor(3);

  public JoinServerScreen(SpaceHuntersClientGame game) {
    super(game);

    this.client = game.getClient();

    ConnectDialog connectDialog = new ConnectDialog("Mit Server verbinden", game.getSkin(),
        "Player" + MathUtils.random(1, 100), "localhost") {
      @Override
      protected void gotInput(ConnectDialog dialog, String username, String hostname) {
        asyncExecutor.submit(() -> {
          dialog.setStatus("Verbinden..."); // todo add timeout if LoginAccepted never is received
          Optional<String> error = sendLoginRequest(username, hostname);
          if (error.isPresent()) {
            dialog.setStatus(error.get());
            Gdx.app.error(TAG, error.get());
          }

          return null;
        });
      }
    };

    connectDialog.show(game.getStage());
  }

  @Override
  public void dispose() {
    asyncExecutor.dispose();
  }

  /**
   * @return contains the error message or is absent if it connected successfully
   */
  private Optional<String> sendLoginRequest(String username, String host) {
    try {
      client.connect(5000, host, Constants.TCP_PORT, Constants.UDP_PORT);
    } catch (IOException e) {
      return Optional.of(e.getMessage());
    }

    client.sendTCP(new LoginRequest(new Player(UUID.randomUUID(), username)));
    return Optional.empty();
  }
}
