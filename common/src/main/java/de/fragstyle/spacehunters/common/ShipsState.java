package de.fragstyle.spacehunters.common;

import com.badlogic.gdx.Gdx;
import de.fragstyle.spacehunters.common.packets.client.InputPacket;
import de.fragstyle.spacehunters.common.packets.server.ShipPacket;

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
    ship.setXSpeed(ship.getXSpeed() + (Constants.SHIP_ACCELERATION * inputPacket.getX()));
    ship.setYSpeed(ship.getYSpeed() + (Constants.SHIP_ACCELERATION * inputPacket.getY()));
  }

  /**
   * Call this in the app's render() method.
   */
  // todo anti-cheat checks, fill in missing player states
  public void act() {
    for (ShipPacket ship : ships.values()) {
      ship.setX(ship.getX() + ship.getXSpeed());
      ship.setY(ship.getY() + ship.getYSpeed());

      ship.setXSpeed(ship.getXSpeed() - ship.getXSpeed() * Constants.SHIP_FRICTION);
      ship.setYSpeed(ship.getYSpeed() - ship.getYSpeed() * Constants.SHIP_FRICTION);
    }
  }

  public void logAllShips() {
    for (Entry<UUID, ShipPacket> entry : ships.entrySet()) {
      ShipPacket ship = entry.getValue();
      String output = "UUID: " + entry.getKey().toString() + "\n\tPos: " + ((int) ship.getX()) + ";" + ((int) ship.getY());
      Gdx.app.log("", output);
    }
  }
}
