package de.fragstyle.spacehunters.server.state;

import static de.fragstyle.spacehunters.server.Constants.SHIP_ACCELERATION;
import static de.fragstyle.spacehunters.server.Constants.SHIP_FRICTION;

import com.badlogic.gdx.Gdx;
import de.fragstyle.spacehunters.common.packets.client.InputPacket;
import de.fragstyle.spacehunters.common.packets.server.ShipPacket;
import de.fragstyle.spacehunters.server.SpaceHuntersServer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class ShipsState {
  private final Map<UUID, ShipPacket> ships = new HashMap<>();

  public ShipsState() {

  }

  public void addShip(ShipPacket ship) {
    ships.put(ship.getUuid(), ship);
  }

  public Map<UUID, ShipPacket> getShips() {
    return ships;
  }

  public void removeShip(UUID uuid) {
    ships.remove(uuid);
  }

  public void handleInputPacket(UUID shipUuid, InputPacket inputPacket) {
    if (!ships.containsKey(shipUuid)) {
      throw new IllegalArgumentException("Ship with UUID '" + shipUuid.toString() + "' not found!");
    }

    ShipPacket ship = ships.get(shipUuid);
    ship.setXSpeed(ship.getXSpeed() + (SHIP_ACCELERATION * inputPacket.getX()));
    ship.setYSpeed(ship.getYSpeed() + (SHIP_ACCELERATION * inputPacket.getY()));
  }

  /**
   * Call this in the app's render() method.
   */
  public void act() {
    for (ShipPacket ship : ships.values()) {
      ship.setX(ship.getX() + ship.getXSpeed());
      ship.setY(ship.getY() + ship.getYSpeed());

      ship.setXSpeed(ship.getXSpeed() * SHIP_FRICTION);
      ship.setYSpeed(ship.getYSpeed() * SHIP_FRICTION);
    }
  }

  public void logAllShips() {
    for (Entry<UUID, ShipPacket> entry : ships.entrySet()) {
      ShipPacket ship = entry.getValue();
      String output = "UUID: " + entry.getKey().toString() + "\n\tPos: " + ((int) ship.getX()) + ";" + ((int) ship.getY());
      Gdx.app.log(SpaceHuntersServer.TAG, output);
    }
  }
}
