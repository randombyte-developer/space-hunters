package de.fragstyle.spacehunters.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
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

    int accelerationInput = MathUtils.clamp(inputPacket.getAcceleration(), -1, 1);
    int rotationInput = MathUtils.clamp(inputPacket.getRotation(), -1, 1);

    ship.setAcceleration(Constants.SHIP_ACCELERATION * accelerationInput);
    ship.setRotation(ship.getRotation() + Constants.SHIP_ROTATION * rotationInput);
  }

  /**
   * Call this in the app's render() method @60 fps.
   */
  public void act(float deltaTime) {
    fromGameSnapshot(getNextState(deltaTime));
  }

  /**
   * Doesn't have any side-effects.
   */
  public GameSnapshot getNextState(float deltaTime) {
    Map<UUID, ShipStatePacket> newShips = ships.values()
        .stream()
        .map(ship -> {

          float friction = ship.getSpeed() == 0 ? 0 : (ship.getSpeed() > 0 ? -Constants.SHIP_FRICTION : Constants.SHIP_FRICTION);
          float speed = ship.getSpeed() + (ship.getAcceleration() + friction) * deltaTime;

          // prevent strange small velocities
          double minSpeed = 0.05;
          if (-minSpeed < speed && speed < minSpeed) {
            speed = 0;
          }

          float x = ship.getX() + MathUtils.cosDeg(ship.getRotation()) * ship.getSpeed() * deltaTime;
          float y = ship.getY() + MathUtils.sinDeg(ship.getRotation()) * ship.getSpeed() * deltaTime;

          Gdx.app.log("", speed + ";" + friction);

          return new ShipStatePacket(ship.getUuid(), x, y, ship.getRotation(), speed, ship.getAcceleration());
        }).collect(Collectors.toMap(ShipStatePacket::getUuid, ship -> ship));

    return new GameSnapshot(newShips);
  }

  public void logAllShips() {
    for (Entry<UUID, ShipStatePacket> entry : ships.entrySet()) {
      ShipStatePacket ship = entry.getValue();
      //String output = "V: " + ((int) ship.getXSpeed()) + ";" + ((int) ship.getYSpeed());
      //String output = "A: " + ((int) ship.getXAcceleration()) + ";" + ((int) ship.getYAcceleration());
      //Gdx.app.log("", output);
    }
  }
}
