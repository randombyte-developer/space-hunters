package de.fragstyle.spacehunters.common.packets;

import com.esotericsoftware.kryo.Kryo;
import de.fragstyle.spacehunters.common.models.ship.ShipState;
import de.fragstyle.spacehunters.common.packets.client.InputPacket;
import de.fragstyle.spacehunters.common.packets.client.LoginRequest;
import de.fragstyle.spacehunters.common.packets.server.Disconnected;
import de.fragstyle.spacehunters.common.packets.server.GameSnapshot;
import de.fragstyle.spacehunters.common.packets.server.LoginAccepted;
import de.fragstyle.spacehunters.common.packets.server.Player;

public class Packets {

  public static void registerPackets(Kryo kryo) {
    // client
    kryo.register(InputPacket.class);
    kryo.register(LoginRequest.class);

    // server
    kryo.register(Disconnected.class);
    kryo.register(GameSnapshot.class);
    kryo.register(LoginAccepted.class);
    kryo.register(Player.class);
    kryo.register(ShipState.class);
  }
}
