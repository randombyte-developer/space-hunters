package de.fragstyle.spacehunters.common.serializers;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class Vector2Serializer extends Serializer<Vector2> {

  public Vector2Serializer() {
    setImmutable(false);
  }

  @Override
  public void write(Kryo kryo, Output output, Vector2 vector2) {
    output.writeFloat(vector2.x);
    output.writeFloat(vector2.y);
  }

  @Override
  public Vector2 read(Kryo kryo, Input input, Class<Vector2> type) {
    return new Vector2(input.readFloat(), input.readFloat());
  }
}
