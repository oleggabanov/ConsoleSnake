package com.move.model.enums;

public enum SnakeSpeed {

  SLOW(300), MEDIUM(200), FAST(100);

  private int speed;

  SnakeSpeed(int speed) {
    this.speed = speed;
  }

  public int getSpeed() {
    return speed;
  }
}
