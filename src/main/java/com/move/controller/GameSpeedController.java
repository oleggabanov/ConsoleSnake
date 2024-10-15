package com.move.controller;

import com.move.model.enums.SnakeSpeed;

import java.util.Scanner;

public class GameSpeedController {

  private final Scanner scanner = new Scanner(System.in);

  private int delay;

  public void getSnakeSpeed() {
    try {
      int i = 0;
      while (true) {
        i = Integer.parseInt(scanner.nextLine());
        if (i >= 1 && i <= 3) {
          delay = stepDelay(i);
          break;
        }
      }
    } catch (NumberFormatException e) {
      delay = stepDelay(1);
    }
  }

  private int stepDelay(int speed) {
    return switch (speed) {
      case 1 -> SnakeSpeed.SLOW.getSpeed();
      case 2 -> SnakeSpeed.MEDIUM.getSpeed();
      case 3 -> SnakeSpeed.FAST.getSpeed();
      default -> throw new NumberFormatException();
    };
  }

  public void delay() {
    try {
      Thread.sleep(delay);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
