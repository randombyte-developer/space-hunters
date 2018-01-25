package de.fragstyle.spacehunters.common.packets;

import com.esotericsoftware.kryo.Kryo;
import de.fragstyle.spacehunters.common.Player;
import de.fragstyle.spacehunters.common.packets.client.InputPacket;
import de.fragstyle.spacehunters.common.packets.client.LoginRequest;
import de.fragstyle.spacehunters.common.packets.server.Disconnected;
import de.fragstyle.spacehunters.common.packets.server.LoginAccepted;
import de.fragstyle.spacehunters.common.packets.server.ShipPacketList;

public class Packets {

  public static void registerPackets(Kryo kryo) {
    kryo.register(LoginRequest.class);
    kryo.register(LoginAccepted.class);
    kryo.register(Disconnected.class);

    kryo.register(InputPacket.class);

    kryo.register(Player.class);
    kryo.register(ShipPacketList.class);
  }
}
