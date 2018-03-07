package de.fragstyle.spacehunters.common.game;

import de.fragstyle.spacehunters.common.models.entities.EntityState;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;

public class GameSnapshot {

  private final long time;
  private final Map<UUID, EntityState> entities;

  private GameSnapshot() {
    this(new HashMap<>());
  }

  public GameSnapshot(Map<UUID, EntityState> entities) {
    this.time = System.currentTimeMillis();
    this.entities = entities;
  }

  public long getTime() {
    return time;
  }

  public Map<UUID, EntityState> getEntityStates() {
    return entities;
  }

  public static GameSnapshot fromGameState(GameState gameState) {
    Map<UUID, EntityState> entityStates = gameState.getEntities().entrySet()
            .stream()
            .collect(Collectors.toMap(Entry::getKey, entry -> entry.getValue().getState()));

    return new GameSnapshot(entityStates);
  }
}
