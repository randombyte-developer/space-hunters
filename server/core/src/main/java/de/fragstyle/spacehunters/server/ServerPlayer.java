package de.fragstyle.spacehunters.server;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import de.fragstyle.spacehunters.common.game.GameSnapshot;
import de.fragstyle.spacehunters.common.models.Player;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

public class ServerPlayer extends Player {

  private Connection connection;
  private Set<Long> toBeConfirmedGameSnapshotPackets = new HashSet<>();

  public ServerPlayer(UUID uuid, String name, Connection connection) {
    super(uuid, name);

    this.connection = connection;
  }

  public Connection getConnection() {
    return connection;
  }

  public void sendGameSnapshot(GameSnapshot gameSnapshot) {
    connection.sendUDP(gameSnapshot);
    toBeConfirmedGameSnapshotPackets.add(gameSnapshot.getTime());
  }

  /**
   * @param delayBeforeReattempt in ms
   */
  public void resendNotVerifiedGameSnapshots(int delayBeforeReattempt, Function<Long, GameSnapshot> getGameSnapshotFunction) {
    long currentTimeMillis = System.currentTimeMillis();

    for (Iterator<Long> iterator = toBeConfirmedGameSnapshotPackets.iterator(); iterator.hasNext();) {
      Long gameSnapshotTime = iterator.next();
      boolean reattemptPacket = gameSnapshotTime + delayBeforeReattempt < currentTimeMillis;
      if (reattemptPacket) {
        GameSnapshot gameSnapshot = getGameSnapshotFunction.apply(gameSnapshotTime);
        if (gameSnapshot != null) {
          connection.sendUDP(gameSnapshot);
          Gdx.app.log("", "Reattempt " + (currentTimeMillis - gameSnapshotTime));
        }
        iterator.remove();
      }
    }
  }

  public void confirmGameSnapshotPacket(long time) {
    toBeConfirmedGameSnapshotPackets.remove(time);
  }

  public static ServerPlayer fromPlayer(Player player, Connection connection) {
    return new ServerPlayer(player.getUuid(), player.getName(), connection);
  }
}
