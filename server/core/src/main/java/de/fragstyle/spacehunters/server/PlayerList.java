package de.fragstyle.spacehunters.server;

import de.fragstyle.spacehunters.common.game.GameSnapshot;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

/**
 * Tracks the connected Players and the sent packets.
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

  public void sendGameSnapshot(GameSnapshot gameSnapshot) {
    for (ServerPlayer serverPlayer : players.values()) {
      serverPlayer.sendGameSnapshot(gameSnapshot);
    }
  }

  public void resendNotVerifiedGameSnapshots(int delayBeforeReattempt, Function<Long, GameSnapshot> getGameSnapshotFunction) {
    for (ServerPlayer serverPlayer : players.values()) {
      serverPlayer.resendNotVerifiedGameSnapshots(delayBeforeReattempt, getGameSnapshotFunction);
    }
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
