package de.fragstyle.spacehunters.common.game;

public class Constants {
  public static final int TCP_PORT = 9880; // todo make configurable
  public static final int UDP_PORT = 9881;

  public static final int DISPLAY_GAME_TIME_OFFSET = 200;

  public static final float STEP_TIME = 1/60f;
  public static final int VELOCITY_ITERATIONS = 6;
  public static final int POSITION_ITERATIONS = 2;

  public static final float ACCELERATION_FORCE = 500_000;

  public static final float MAXIMAL_ABSOLUTE_SPEED = 500;
  public static final float MINIMAL_ABSOLUTE_SPEED = 0.05f;
  public static final float FRICTION = 150;

  public static final float MAXIMAL_ABSOLUTE_ROTATION_SPEED = 160;
  public static final float MINIMAL_ABSOLUTE_ROTATION_SPEED = 0.05f;
  public static final float ROTATION_FRICTION = 350;
}
