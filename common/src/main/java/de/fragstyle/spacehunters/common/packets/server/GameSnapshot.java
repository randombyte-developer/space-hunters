package de.fragstyle.spacehunters.common.packets.server;

import de.fragstyle.spacehunters.common.GameState;
import de.fragstyle.spacehunters.common.packets.client.InputPacket;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The immutable variant of {@link GameState}.
 */
public class GameSnapshot extends GameState {

  private static final String IMMUTABLE_ERROR = "This GameSnapshot is immutable!";

  private final long time;

  private GameSnapshot() {
    this(new HashMap<>());
  }

  public GameSnapshot(Map<UUID, ShipStatePacket> ships) {
    super(ships);
    this.time = System.currentTimeMillis(); // todo bad?
  }

  public long getTime() {
    return time;
  }

  public static GameSnapshot fromGameState(GameState gameState) {
    return new GameSnapshot(gameState.getShips());
  }

  @Override
  public void fromGameSnapshot(GameSnapshot gameSnapshot) {
    throw new UnsupportedOperationException(IMMUTABLE_ERROR);
  }

  @Override
  public void addShip(ShipStatePacket ship) {
    throw new UnsupportedOperationException(IMMUTABLE_ERROR);
  }

  @Override
  public void removeShip(UUID uuid) {
    throw new UnsupportedOperationException(IMMUTABLE_ERROR);
  }

  @Override
  public void handleInputPacket(UUID shipUuid, InputPacket inputPacket) {
    throw new UnsupportedOperationException(IMMUTABLE_ERROR);
  }

  @Override
  public void act(float deltaTime) {
    throw new UnsupportedOperationException(IMMUTABLE_ERROR);
  }
}
