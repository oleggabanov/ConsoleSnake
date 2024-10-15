package com.move.model.enums;

public enum SnakeDirection {

  UP(1), DOWN(3), LEFT(2), RIGHT(4);

  private int direction;

  SnakeDirection(int i) {
    direction = i;
  }

  public int getDirection() {
    return direction;
  }

}
