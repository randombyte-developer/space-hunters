package de.fragstyle.spacehunters.server;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;

/**
 * Tracks the connected Players.
 */
public class PlayerList {

  private Map<UUID, ServerPlayer> players = new HashMap<>();

  public PlayerList() {

  }

  /**
   * @return false if UUID was already registered
   */
  public boolean add(ServerPlayer player) {
    if (players.containsKey(player.getUuid())) {
      return false;
    }
    players.put(player.getUuid(), player);
    return true;
  }

  public Map<UUID, ServerPlayer> getPlayers() {
    return Collections.unmodifiableMap(players);
  }

  /**
   * @return true if uuid was registered
   */
  public boolean remove(UUID uuid) {
    return players.remove(uuid) != null;
  }

  /**
   * @return true if a client ID matched and was removed
   */
  public boolean removeByKryoNetClientId(int id) {
    Optional<UUID> uuidOpt = getByKryoNetClientId(id);
    if (!uuidOpt.isPresent()) {
      return false;
    }
    remove(uuidOpt.get());
    return true;
  }

  public Optional<UUID> getByKryoNetClientId(int id) {
    return players.entrySet()
        .stream()
        .filter(entry -> entry.getValue().getConnection().getID() == id)
        .map(Entry::getKey)
        .findFirst();
  }

  public boolean isNameAlreadyInUse(String name) {
    return players.values()
        .stream()
        .anyMatch(player -> player.getName().equalsIgnoreCase(name));
  }
}
