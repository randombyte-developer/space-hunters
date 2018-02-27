package de.fragstyle.spacehunters.common;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import de.fragstyle.spacehunters.common.models.EntityState;
import de.fragstyle.spacehunters.common.models.EntityType;
import de.fragstyle.spacehunters.common.models.ship.ShipState;
import de.fragstyle.spacehunters.common.models.wall.WallState;
import de.fragstyle.spacehunters.common.packets.client.InputPacket;
import de.fragstyle.spacehunters.common.packets.client.LoginRequest;
import de.fragstyle.spacehunters.common.packets.server.Disconnected;
import de.fragstyle.spacehunters.common.packets.server.GameSnapshot;
import de.fragstyle.spacehunters.common.packets.server.LoginAccepted;
import de.fragstyle.spacehunters.common.packets.server.Player;
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
    kryo.register(LoginRequest.class);

    // server
    kryo.register(Disconnected.class);
    kryo.register(GameSnapshot.class);
    kryo.register(LoginAccepted.class);

    kryo.register(Player.class);

    kryo.register(EntityState.class);
    kryo.register(WallState.class);
    kryo.register(ShipState.class);
    kryo.register(EntityType.class);
  }
}
