package de.fragstyle.spacehunters.common;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import de.fragstyle.spacehunters.common.models.entities.EntityState;
import de.fragstyle.spacehunters.common.models.entities.EntityType;
import de.fragstyle.spacehunters.common.models.entities.ship.ShipState;
import de.fragstyle.spacehunters.common.models.entities.wall.WallState;
import de.fragstyle.spacehunters.common.packets.client.InputPacket;
import de.fragstyle.spacehunters.common.packets.client.LoginRequestPacket;
import de.fragstyle.spacehunters.common.packets.server.DisconnectedPacket;
import de.fragstyle.spacehunters.common.game.GameSnapshot;
import de.fragstyle.spacehunters.common.packets.server.LoginAcceptedPacket;
import de.fragstyle.spacehunters.common.models.Player;
import de.fragstyle.spacehunters.common.serializers.UuidSerializer;
import de.fragstyle.spacehunters.common.serializers.Vector2Serializer;
import java.util.HashMap;
import java.util.UUID;

public class KryoUtils {

  public static void prepareKryo(Kryo kryo) {
    kryo.register(UUID.class, new UuidSerializer());
    kryo.register(Vector2.class, new Vector2Serializer());
    kryo.register(HashMap.class); // has to be called to activate the default serializer

    // client
    kryo.register(InputPacket.class);
    kryo.register(LoginRequestPacket.class);

    // server
    kryo.register(DisconnectedPacket.class);
    kryo.register(GameSnapshot.class);
    kryo.register(LoginAcceptedPacket.class);

    kryo.register(Player.class);

    kryo.register(EntityState.class);
    kryo.register(WallState.class);
    kryo.register(ShipState.class);
    kryo.register(EntityType.class);
  }
}
