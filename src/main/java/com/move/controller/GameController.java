package com.move.controller;

import com.move.model.Cell;
import com.move.model.Fruit;
import com.move.model.Snake;
import com.move.view.GameBoardView;

import java.util.List;
import java.util.Random;

public class GameController {

  private static final int WIDTH = 40;
  private static final int HEIGHT = 12;
  private final Random random = new Random();
  private Snake snake = new Snake(WIDTH, HEIGHT);
  private Fruit fruit = new Fruit(random.nextInt(1, WIDTH - 1), random.nextInt(1, HEIGHT - 1));
  private KeyboardListener listener = new KeyboardListener();
  private GameBoardView boardView = new GameBoardView(WIDTH, HEIGHT, snake, fruit);
  private GameSpeedController speedController = new GameSpeedController();

  public void startConsoleSnake() {
    boardView.printGreetingMessage();
    speedController.getSnakeSpeed();
    listener.directionListener();
    while (true) {
      speedController.delay();
      Cell head = snake.getSnakeBody().get(0);
      int snakeX = head.x();
      int snakeY = head.y();
      switch (listener.getDirection()) {
        case 1 -> snakeY--;
        case 2 -> snakeX--;
        case 3 -> snakeY++;
        case 4 -> snakeX++;
      }
      boolean isFruitEaten = snakeX == fruit.x() && snakeY == fruit.y();
      if (isGameOver(new Cell(snakeX, snakeY))) {
        break;
      }
      if (isFruitEaten) {
        snake.expandSnake(snakeX, snakeY);
        fruit = new Fruit(random.nextInt(1, WIDTH - 1), random.nextInt(1, HEIGHT - 1));
        boardView.setFruit(fruit);
      } else {
        snake.moveSnake(snakeX, snakeY);
      }
      boardView.showGameBoard(new Cell(snakeX, snakeY), snake.getSnakeBody().size() - 2);
      clearConsole();
    }
  }


  private boolean isGameOver(Cell head) {
    boolean isSnakeHitWall = head.x() == 0 || head.y() == 0 || head.x() == WIDTH - 1 || head.y() == HEIGHT - 1;
    List<Cell> snkBody = snake.getSnakeBody().subList(1, snake.getSnakeBody().size());
    boolean isSnakeHitItself = snkBody.contains(head);
    if (isSnakeHitWall || isSnakeHitItself) {
      boardView.printGameOver();
      return true;
    }
    return false;
  }

  public void clearConsole() {
    try {
      Thread.sleep(250);
      new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


}