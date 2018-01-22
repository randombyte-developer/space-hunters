package de.fragstyle.spacehunters.common.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import java.util.UUID;

public class UuidSerializer extends Serializer<UUID> {
  public UuidSerializer() {
    setImmutable(true);
  }

  @Override
  public void write(Kryo kryo, Output output, UUID uuid) {
    output.writeLong(uuid.getMostSignificantBits());
    output.writeLong(uuid.getLeastSignificantBits());
  }

  @Override
  public UUID read(Kryo kryo, Input input, Class<UUID> uuidClass) {
    return new UUID(input.readLong(), input.readLong());
  }
}
