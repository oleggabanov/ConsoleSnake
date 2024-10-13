package com.move.model;

import java.util.ArrayList;
import java.util.List;

public class Snake {
  private List<Cell> snakeBody;

  public Snake() {
    snakeBody = new ArrayList<>();
    snakeBody.add(new Cell(10, 7));
    snakeBody.add(new Cell(9, 7));
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
