package de.fragstyle.spacehunters.common;

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

    ship.setAcceleration(Constants.ACCELERATION * accelerationInput);

    if (rotationInput != 0) {
      ship.setRotationSpeed(Constants.MAXIMAL_ABSOLUTE_ROTATION_SPEED * rotationInput);
    }
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

          // == ROTATION ==

          float rotSpeed = ship.getRotationSpeed();

          // prevent strange small rotation speeds
          if (-Constants.MINIMAL_ABSOLUTE_ROTATION_SPEED < rotSpeed && rotSpeed< Constants.MINIMAL_ABSOLUTE_ROTATION_SPEED) {
            rotSpeed = 0;
          }

          float rotation = ship.getRotation() + rotSpeed * deltaTime;

          float rotationFriction = rotSpeed == 0 ? 0 : (rotSpeed > 0 ? -Constants.ROTATION_FRICTION : Constants.ROTATION_FRICTION);
          rotSpeed = rotSpeed + rotationFriction * deltaTime;

          // == MOVEMENT ==

          float xAcceleration = MathUtils.cosDeg(rotation) * ship.getAcceleration();
          float yAcceleration = MathUtils.sinDeg(rotation) * ship.getAcceleration();

          float xSpeed = ship.getXSpeed() + xAcceleration * deltaTime;
          float ySpeed = ship.getYSpeed() + yAcceleration * deltaTime;

          if (xAcceleration == 0) {
            float xFriction = xSpeed == 0 ? 0 : (xSpeed > 0 ? -Constants.FRICTION : Constants.FRICTION);
            xSpeed = xSpeed + xFriction * deltaTime;
          }
          if (yAcceleration == 0) {
            float yFriction = ySpeed == 0 ? 0 : (ySpeed > 0 ? -Constants.FRICTION : Constants.FRICTION);
            ySpeed = ySpeed + yFriction * deltaTime;
          }

          xSpeed = MathUtils.clamp(xSpeed, -Constants.MAXIMAL_ABSOLUTE_SPEED, Constants.MAXIMAL_ABSOLUTE_SPEED);
          ySpeed = MathUtils.clamp(ySpeed, -Constants.MAXIMAL_ABSOLUTE_SPEED, Constants.MAXIMAL_ABSOLUTE_SPEED);

          // prevent strange small velocities
          if (-Constants.MINIMAL_ABSOLUTE_SPEED < xSpeed && xSpeed< Constants.MINIMAL_ABSOLUTE_SPEED) {
            xSpeed = 0;
          }
          if (-Constants.MINIMAL_ABSOLUTE_SPEED < ySpeed && ySpeed< Constants.MINIMAL_ABSOLUTE_SPEED) {
            ySpeed = 0;
          }

          float x = ship.getX() + xSpeed * deltaTime;
          float y = ship.getY() + ySpeed * deltaTime;

          return new ShipStatePacket(ship.getUuid(), x, y, rotation, rotSpeed, xSpeed, ySpeed, ship.getAcceleration());
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
