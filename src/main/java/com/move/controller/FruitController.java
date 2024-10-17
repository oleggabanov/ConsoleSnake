package com.move.controller;

import com.move.model.Cell;
import com.move.model.Fruit;
import com.move.model.Snake;

import java.util.Random;

import static com.move.model.BoardParams.*;

public class FruitController {
  private final Random random = new Random();

  public Fruit spawnFruit(Snake snake) {
    Fruit fruit = null;
    do {
      int x = random.nextInt(1, WIDTH - 1);
      int y = random.nextInt(1, HEIGHT - 1);
      if (!snake.getSnakeBody().contains(new Cell(x, y))) {
        fruit = new Fruit(x, y);
        break;
      }
    }
    while (true);
    return fruit;
  }

}
