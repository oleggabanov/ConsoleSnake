package com.move.model;

import java.util.ArrayList;
import java.util.List;

import static com.move.model.BoardParams.*;

public class Snake {
  private List<Cell> snakeBody;

  public Snake() {
    snakeBody = new ArrayList<>();
    int x = WIDTH / 2;
    int y = HEIGHT / 2;
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
