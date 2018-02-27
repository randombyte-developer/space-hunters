package de.fragstyle.spacehunters.common.packets.server;

import de.fragstyle.spacehunters.common.GameState;
import de.fragstyle.spacehunters.common.models.EntityState;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;

public class GameSnapshot {

  private final long time;
  private final Map<UUID, EntityState> ships;

  private GameSnapshot() {
    this(new HashMap<>());
  }

  public GameSnapshot(Map<UUID, EntityState> ships) {
    this.time = System.currentTimeMillis(); // todo bad?
    this.ships = ships;
  }

  public long getTime() {
    return time;
  }

  public Map<UUID, EntityState> getEntityStates() {
    return ships;
  }

  public static GameSnapshot fromGameState(GameState gameState) {
    Map<UUID, EntityState> entityStates = gameState.getEntities().entrySet()
            .stream()
            .collect(Collectors.toMap(Entry::getKey, entry -> entry.getValue().getState()));

    return new GameSnapshot(entityStates);
  }
}
