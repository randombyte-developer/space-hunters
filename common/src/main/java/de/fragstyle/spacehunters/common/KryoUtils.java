package de.fragstyle.spacehunters.common;

import com.esotericsoftware.kryo.Kryo;
import de.fragstyle.spacehunters.common.packets.Packets;
import de.fragstyle.spacehunters.common.serializers.Serializers;

public class KryoUtils {
  public static void prepareKryo(Kryo kryo) {
    Packets.registerPackets(kryo);
    Serializers.registerSerializers(kryo);
  }
}
