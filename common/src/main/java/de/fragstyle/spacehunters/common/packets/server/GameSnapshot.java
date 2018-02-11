package de.fragstyle.spacehunters.common.packets.server;

import de.fragstyle.spacehunters.common.GameState;
import de.fragstyle.spacehunters.common.models.ship.ShipState;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;

public class GameSnapshot {

  private final long time;
  private final Map<UUID, ShipState> ships;

  private GameSnapshot() {
    this(new HashMap<>());
  }

  public GameSnapshot(Map<UUID, ShipState> ships) {
    this.time = System.currentTimeMillis(); // todo bad?
    this.ships = ships;
  }

  public long getTime() {
    return time;
  }

  public Map<UUID, ShipState> getShips() {
    return ships;
  }

  public static GameSnapshot fromGameState(GameState gameState) {
    Map<UUID, ShipState> shipStates = gameState.getShips().entrySet()
            .stream()
            .collect(Collectors.toMap(Entry::getKey, entry -> entry.getValue().getState()));

    return new GameSnapshot(shipStates);
  }
}
