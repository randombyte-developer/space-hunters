package de.fragstyle.spacehunters.common.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import de.fragstyle.spacehunters.common.models.Entity;
import de.fragstyle.spacehunters.common.models.EntityState;
import de.fragstyle.spacehunters.common.models.wall.WallEntity;
import de.fragstyle.spacehunters.common.models.wall.WallState;
import de.fragstyle.spacehunters.common.models.wall.Walls;
import de.fragstyle.spacehunters.common.packets.client.InputPacket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

/**
 * The mutable game state which contains all movable objects. It can update itself(physics).
 */
public class GameState {

  private World world;
  private Map<UUID, Entity> entities;

  private Map<UUID, InputPacket> lastInputs = new HashMap<>();
  private float accumulator = 0;

  public GameState() {
    this(new World(Vector2.Zero, false), new HashMap<>());
  }

  public GameState(World world, Map<UUID, Entity> entities) {
    this.world = world;
    this.entities = entities;

    setupGamefieldFrame();
  }

  private void setupGamefieldFrame() {
    List<WallState> wallStates = Walls.createFrame(0, 0, 2000, 1000, 10);
    for (WallState wallState : wallStates) {
      addEntity(new WallEntity(wallState, world));
    }
    addEntity(new WallEntity(new WallState(new EntityState(UUID.randomUUID(), new Vector2(50, 100), 30, Vector2.Zero), new Vector2(800, 5)), world));
    addEntity(new WallEntity(new WallState(new EntityState(UUID.randomUUID(), new Vector2(-300, -100), 70, Vector2.Zero), new Vector2(400, 100)), world));
    addEntity(new WallEntity(new WallState(new EntityState(UUID.randomUUID(), new Vector2(200, -600), 10, Vector2.Zero), new Vector2(400, 500)), world));
  }

  public void addEntity(Entity entity) {
    entities.put(entity.getState().getUuid(), entity);
  }

  public Map<UUID, Entity> getEntities() {
    return entities;
  }

  public void removeShip(UUID uuid) {
    entities.remove(uuid);
  }

  public void handleInputPacket(UUID shipUuid, InputPacket inputPacket) {
    if (!entities.containsKey(shipUuid)) {
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
    entities.forEach((key, shipEntity) -> {
      InputPacket inputPacket = lastInputs.get(key);
      if (inputPacket == null) return;

      shipEntity.getBody().applyAngularImpulse(inputPacket.getRotation() * 100_000f, true);

      float xForce = inputPacket.getAcceleration() * MathUtils.cos(shipEntity.getBody().getAngle()) * Constants.ACCELERATION_FORCE;
      float yForce = inputPacket.getAcceleration() * MathUtils.sin(shipEntity.getBody().getAngle()) * Constants.ACCELERATION_FORCE;
      shipEntity.getBody().applyForce(new Vector2(xForce, yForce), shipEntity.getBody().getWorldCenter(), true);
    });
  }

  public void logAllShips() {
    for (Entry<UUID, Entity> entry : entities.entrySet()) {
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
