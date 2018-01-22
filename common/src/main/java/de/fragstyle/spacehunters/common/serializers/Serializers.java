package de.fragstyle.spacehunters.common.serializers;

import com.esotericsoftware.kryo.Kryo;
import java.util.UUID;

public class Serializers {

  public static void registerSerializers(Kryo kryo) {
    kryo.register(UUID.class, new UuidSerializer());
  }
}
