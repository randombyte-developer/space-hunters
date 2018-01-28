package de.fragstyle.spacehunters.common.serializers;

import com.esotericsoftware.kryo.Kryo;
import java.util.HashMap;
import java.util.UUID;

public class Serializers {

  public static void registerSerializers(Kryo kryo) {
    kryo.register(UUID.class, new UuidSerializer());
    kryo.register(HashMap.class); // has to be called to activate the default serializer
  }
}
