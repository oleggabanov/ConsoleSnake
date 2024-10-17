package com.move.controller;

import com.move.model.Fruit;

import java.util.Random;

import static com.move.model.BoardParams.*;

public class FruitController {
  private final Random random = new Random();

  public Fruit spawnFruit() {

    return new Fruit(random.nextInt(1, WIDTH - 1), random.nextInt(1, HEIGHT - 1));
  }

  //width height        ooooo

}
