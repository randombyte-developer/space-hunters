package de.fragstyle.spacehunters.common.packets;

import com.esotericsoftware.kryo.Kryo;
import de.fragstyle.spacehunters.common.Player;
import de.fragstyle.spacehunters.common.packets.login.Disconnected;
import de.fragstyle.spacehunters.common.packets.login.LoginAccepted;
import de.fragstyle.spacehunters.common.packets.login.LoginRequest;

public class Packets {

  public static void registerPackets(Kryo kryo) {
    kryo.register(LoginRequest.class);
    kryo.register(LoginAccepted.class);
    kryo.register(Disconnected.class);

    kryo.register(Player.class);
  }
}
