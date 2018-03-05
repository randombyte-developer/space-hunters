package de.fragstyle.spacehunters.common.models;

import java.util.UUID;

public class Player {

  private UUID uuid;

  private String name;

  private Player() { }

  public Player(UUID uuid, String name) {
    this.uuid = uuid;
    this.name = name;
  }

  public UUID getUuid() {
    return uuid;
  }

  public String getName() {
    return name;
  }
}
