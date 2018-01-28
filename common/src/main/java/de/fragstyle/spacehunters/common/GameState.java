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
    ship.setXAcceleration(Constants.SHIP_ACCELERATION * inputPacket.getX()); // todo validate the input!!!
    ship.setYAcceleration(Constants.SHIP_ACCELERATION * inputPacket.getY()); // todo validate the input!!!
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
          float x = ship.getX() + (ship.getXSpeed() * deltaTime);
          float y = ship.getY() + (ship.getYSpeed() * deltaTime);

          float xFriction = ship.getXSpeed() == 0 ? 0 : (ship.getXSpeed() > 0 ? -Constants.SHIP_FRICTION : Constants.SHIP_FRICTION);
          float yFriction = ship.getYSpeed() == 0 ? 0 : (ship.getYSpeed() > 0 ? -Constants.SHIP_FRICTION : Constants.SHIP_FRICTION);

          float xSpeed = ship.getXSpeed() + ship.getXAcceleration() * deltaTime + xFriction * deltaTime;
          float ySpeed = ship.getYSpeed() + ship.getYAcceleration() * deltaTime + yFriction * deltaTime;

          // prevent strange small velocities
          double a = 0.05;
          if (-a < xSpeed && xSpeed < a) {
            xSpeed = 0;
          }
          if (-a < ySpeed && ySpeed < a) {
            ySpeed = 0;
          }

          Gdx.app.log("", xSpeed + ";" + ySpeed + "\t\t" + xFriction + ";" + yFriction);

          return new ShipStatePacket(ship.getUuid(), x, y, ship.getRotation(), xSpeed, ySpeed,
              ship.getXAcceleration(), ship.getYAcceleration());
        }).collect(Collectors.toMap(ShipStatePacket::getUuid, ship -> ship));

    return new GameSnapshot(newShips);
  }

  public void logAllShips() {
    for (Entry<UUID, ShipStatePacket> entry : ships.entrySet()) {
      ShipStatePacket ship = entry.getValue();
      //String output = "V: " + ((int) ship.getXSpeed()) + ";" + ((int) ship.getYSpeed());
      String output = "A: " + ((int) ship.getXAcceleration()) + ";" + ((int) ship.getYAcceleration());
      //Gdx.app.log("", output);
    }
  }
}
