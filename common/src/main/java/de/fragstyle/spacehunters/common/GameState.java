package de.fragstyle.spacehunters.common;

import com.badlogic.gdx.Gdx;
import de.fragstyle.spacehunters.common.packets.client.InputPacket;
import de.fragstyle.spacehunters.common.packets.server.GameSnapshot;
import de.fragstyle.spacehunters.common.packets.server.ShipStatePacket;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The mutable game state which contains all movable objects. It can be updated at any time(like when
 * an {@link InputPacket} is received). The state is periodically sent to the clients, but as
 * an immutable {@link de.fragstyle.spacehunters.common.packets.server.GameSnapshot}.
 */
public class GameState {

  private Map<UUID, ShipStatePacket> ships;

  public GameState() {
    this(new HashMap<>());
  }

  public GameState(Map<UUID, ShipStatePacket> ships) {
    this.ships = ships;
  }

  public void fromGameSnapshot(GameSnapshot gameSnapshot) {
    ships = gameSnapshot.getShips();
  }

  public void addShip(ShipStatePacket ship) {
    ships.put(ship.getUuid(), ship);
  }

  public Map<UUID, ShipStatePacket> getShips() {
    return ships;
  }

  public void removeShip(UUID uuid) {
    ships.remove(uuid);
  }

  public void handleInputPacket(UUID shipUuid, InputPacket inputPacket) {
    if (!ships.containsKey(shipUuid)) {
      throw new IllegalArgumentException("Ship with UUID '" + shipUuid.toString() + "' not found!");
    }

    ShipStatePacket ship = ships.get(shipUuid);
    ship.setXSpeed(ship.getXSpeed() + (Constants.SHIP_ACCELERATION * inputPacket.getX())); // todo validate the input!!!
    ship.setYSpeed(ship.getYSpeed() + (Constants.SHIP_ACCELERATION * inputPacket.getY()));
  }

  /**
   * Call this in the app's render() method @60 fps.
   */
  // todo anti-cheat checks
  public void act() {
    fromGameSnapshot(getNextState());
  }

  /**
   * Called @60fps. Doesn't have any side-effects.
   */
  public GameSnapshot getNextState() {
    Map<UUID, ShipStatePacket> newShips = ships.values()
        .stream()
        .map(ship -> {
          float x = ship.getX() + ship.getXSpeed();
          float y = ship.getY() + ship.getYSpeed();

          float xSpeed = ship.getXSpeed() - ship.getXSpeed() * Constants.SHIP_FRICTION;
          float ySpeed = ship.getYSpeed() - ship.getYSpeed() * Constants.SHIP_FRICTION;

          return new ShipStatePacket(ship.getUuid(), x, y, ship.getRotation(), xSpeed, ySpeed);
        }).collect(Collectors.toMap(ShipStatePacket::getUuid, ship -> ship));

    return new GameSnapshot(newShips);
  }

  public void logAllShips() {
    for (Entry<UUID, ShipStatePacket> entry : ships.entrySet()) {
      ShipStatePacket ship = entry.getValue();
      String output = "UUID: " + entry.getKey().toString() + "\n\tPos: " + ((int) ship.getX()) + ";" + ((int) ship.getY());
      Gdx.app.log("", output);
    }
  }
}
