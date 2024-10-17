package com.move.controller;

import com.move.model.Cell;
import com.move.model.Snake;

import java.util.List;
import java.util.Scanner;

import static com.move.model.BoardParams.*;

public class GameConditionController {

  private boolean isRestart = false;

  public boolean isGameOver(Cell head, Snake snake) {
    boolean isSnakeHitWall = head.x() == 0 || head.y() == 0 || head.x() == WIDTH - 1 || head.y() == HEIGHT - 1;
    List<Cell> snkBody = snake.getSnakeBody().subList(1, snake.getSnakeBody().size());
    boolean isSnakeHitItself = snkBody.contains(head);
    if (isSnakeHitWall || isSnakeHitItself) {
      return true;
    }
    return false;
  }

  public boolean isRestart() {
    return isRestart;
  }

  public void setRestart(String command) {
    if (command.toLowerCase().lastIndexOf('y') != -1) {
      isRestart = true;
    }
  }


}
