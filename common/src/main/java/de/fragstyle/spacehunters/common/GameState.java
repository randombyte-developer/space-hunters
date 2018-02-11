package de.fragstyle.spacehunters.common;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import de.fragstyle.spacehunters.common.models.ship.ShipEntity;
import de.fragstyle.spacehunters.common.packets.client.InputPacket;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

/**
 * The mutable game state which contains all movable objects. It can be updated at any time(like when
 * an {@link InputPacket} is received). The state is periodically sent to the clients, but as
 * an immutable {@link de.fragstyle.spacehunters.common.packets.server.GameSnapshot}.
 */
public class GameState {

  private World world;
  private Map<UUID, InputPacket> lastInputs;
  private Map<UUID, ShipEntity> ships;
  private int accumulator = 0;

  public GameState() {
    this(new World(Vector2.Zero, false), new HashMap<>());
  }

  public GameState(World world, Map<UUID, ShipEntity> ships) {
    this.world = world;
    this.ships = ships;
  }

  public void addShip(ShipEntity shipEntity) {
    ships.put(shipEntity.getUuid(), shipEntity);
  }

  public Map<UUID, ShipEntity> getShips() {
    return ships;
  }

  public void removeShip(UUID uuid) {
    ships.remove(uuid);
  }

  public void handleInputPacket(UUID shipUuid, InputPacket inputPacket) {
    if (!ships.containsKey(shipUuid)) {
      throw new IllegalArgumentException("Ship with UUID '" + shipUuid.toString() + "' not found!");
    }

    lastInputs.put(shipUuid, inputPacket.clamp());
  }

  /**
   * Call this in the app's render() method @60 fps.
   */
  public void act(float deltaTime) {
    float frameTime = Math.min(deltaTime, 0.25f);
    accumulator += frameTime;
    while (accumulator >= Constants.STEP_TIME) {
      actInputs(Constants.STEP_TIME);
      world.step(Constants.STEP_TIME, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);
      accumulator -= Constants.STEP_TIME;
    }
  }

  private void actInputs(float deltaTime) {
    for (Entry<UUID, ShipEntity> entry : ships.entrySet()) {

        UUID uuid = entry.getKey();
        ShipEntity shipEntity = entry.getValue();

        // == SHIP ==

        shipEntity.getBody().applyForce(new Vector2(1, 0), shipEntity.getOrigin(), true);

        // == ROTATION ==

          /*float rotSpeed = shipEntity.getRotationSpeed();

          // prevent strange small rotation speeds
          if (-Constants.MINIMAL_ABSOLUTE_ROTATION_SPEED < rotSpeed && rotSpeed< Constants.MINIMAL_ABSOLUTE_ROTATION_SPEED) {
            rotSpeed = 0;
          }

          float rotation = shipEntity.getRotation() + rotSpeed * deltaTime;

          float rotationFriction = rotSpeed == 0 ? 0 : (rotSpeed > 0 ? -Constants.ROTATION_FRICTION : Constants.ROTATION_FRICTION);
          rotSpeed = rotSpeed + rotationFriction * deltaTime;

          // == MOVEMENT ==

          float xAcceleration = MathUtils.cosDeg(rotation) * shipEntity.getAcceleration();
          float yAcceleration = MathUtils.sinDeg(rotation) * shipEntity.getAcceleration();

          float xSpeed = shipEntity.getXSpeed() + xAcceleration * deltaTime;
          float ySpeed = shipEntity.getYSpeed() + yAcceleration * deltaTime;

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

          float x = shipEntity.getX() + xSpeed * deltaTime;
          float y = shipEntity.getY() + ySpeed * deltaTime;*/

        //return new ShipStatePacket(shipEntity.getUuid(), x, y, rotation, rotSpeed, xSpeed, ySpeed, shipEntity.getAcceleration());
    }
  }

  public void logAllShips() {
    for (Entry<UUID, ShipEntity> entry : ships.entrySet()) {
      entry.getValue();
      //String output = "V: " + ((int) ship.getXSpeed()) + ";" + ((int) ship.getYSpeed());
      //String output = "A: " + ((int) ship.getXAcceleration()) + ";" + ((int) ship.getYAcceleration());
      //Gdx.app.log("", output);
    }
  }

  public World getWorld() {
    return world;
  }
}
