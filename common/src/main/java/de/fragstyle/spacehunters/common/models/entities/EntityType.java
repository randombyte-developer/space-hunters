package de.fragstyle.spacehunters.common.models.entities;

public enum EntityType {
  SHIP("ship"),
  WALL("wall");

  private final String id;

  EntityType(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }
}
