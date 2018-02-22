package de.fragstyle.spacehunters.common;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import de.fragstyle.spacehunters.common.models.ship.ShipEntity;
import de.fragstyle.spacehunters.common.packets.client.InputPacket;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

/**
 * The mutable game state which contains all movable objects. It can update itself(physics).
 */
public class GameState {

  private World world;
  private Map<UUID, ShipEntity> ships;

  private Map<UUID, InputPacket> lastInputs = new HashMap<>();
  private float accumulator = 0;

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
   * Call this in the servers' render() method @60 fps.
   */
  public void act(float deltaTime) {
    float frameTime = Math.min(deltaTime, 0.25f);

    // This helps with simulating the world; https://gafferongames.com/post/fix_your_timestep/
    accumulator += frameTime;
    while (accumulator >= Constants.STEP_TIME) {
      actInputs(Constants.STEP_TIME);
      world.step(Constants.STEP_TIME, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);
      accumulator -= Constants.STEP_TIME;
    }
  }

  private void actInputs(float deltaTime) {
    ships.forEach((key, shipEntity) -> {
      InputPacket inputPacket = lastInputs.get(key);
      if (inputPacket == null) return;

      shipEntity.getBody().applyAngularImpulse(inputPacket.getRotation() * 250_000f, true);

      float xForce = inputPacket.getAcceleration() * MathUtils.cos(shipEntity.getBody().getAngle()) * Constants.ACCELERATION_FORCE;
      float yForce = inputPacket.getAcceleration() * MathUtils.sin(shipEntity.getBody().getAngle()) * Constants.ACCELERATION_FORCE;
      shipEntity.getBody().applyForce(new Vector2(xForce, yForce), shipEntity.getBody().getWorldCenter(), true);
    });
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
