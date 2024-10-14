package com.move.model;

public enum Direction {

  UP(1), DOWN(3), LEFT(2), RIGHT(4);

  private int direction;

  public int getDirection() {
    return direction;
  }

  Direction(int i) {
    direction = i;
  }
}
