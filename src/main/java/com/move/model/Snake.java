package com.move.model;

import java.util.ArrayList;
import java.util.List;

public class Snake {
  private List<Cell> snakeBody;

  public Snake(int width, int height) {
    snakeBody = new ArrayList<>();
    int x = width / 2;
    int y = height / 2;
    snakeBody.add(new Cell(x, y));
    snakeBody.add(new Cell(x - 1, y));
  }

  public List<Cell> getSnakeBody() {
    return snakeBody;
  }

  public void moveSnake(int snakeX, int snakeY) {
    snakeBody.add(0, new Cell(snakeX, snakeY));
    snakeBody.remove(snakeBody.size() - 1);
  }


  public void expandSnake(int snakeX, int snakeY) {
    snakeBody.add(0, new Cell(snakeX, snakeY));
  }


}
