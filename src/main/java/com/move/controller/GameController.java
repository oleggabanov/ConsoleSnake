package com.move.controller;

import com.move.model.Cell;
import com.move.model.Fruit;
import com.move.model.Snake;
import com.move.model.enums.SnakeSpeed;
import com.move.view.GameBoardView;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GameController {

  private static final int WIDTH = 40;
  private static final int HEIGHT = 12;
  private final Scanner scanner = new Scanner(System.in);
  private final Random random = new Random();
  private Snake snake = new Snake(WIDTH, HEIGHT);
  private Fruit fruit = new Fruit(random.nextInt(1, WIDTH - 1), random.nextInt(1, HEIGHT - 1));
  private ConsoleController console = new ConsoleController();
  private GameBoardView boardView = new GameBoardView(WIDTH, HEIGHT, snake, fruit);

  public void startConsoleSnake() {
    boardView.printGreetingMessage();
    console.directionListener();
    int speed = getSnakeSpeed();
    while (true) {
      stepDelay(speed);
      Cell head = snake.getSnakeBody().get(0);
      int snakeX = head.x();
      int snakeY = head.y();
      switch (console.getDirection()) {
        case 1 -> snakeY -= 1;
        case 2 -> snakeX -= 1;
        case 3 -> snakeY += 1;
        case 4 -> snakeX += 1;
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
      boardView.showGameBoard(new Cell(snakeX, snakeY),snake.getSnakeBody().size() - 2);
      console.clearConsole();
    }
  }


  private Integer getSnakeSpeed() {
    try {
      int i = 0;
      while (true) {
        i = Integer.parseInt(scanner.nextLine());
        if (i >= 1 && i <= 3) {
          break;
        }
      }
      return i;
    } catch (NumberFormatException e) {
      return 1;
    }
  }


  private void stepDelay(int speed) {
    int snakeSpeed = switch (speed) {
      case 1 -> SnakeSpeed.SLOW.getSpeed();
      case 2 -> SnakeSpeed.MEDIUM.getSpeed();
      case 3 -> SnakeSpeed.FAST.getSpeed();
      default -> throw new NumberFormatException();
    };
    try {
      Thread.sleep(snakeSpeed);
    } catch (InterruptedException e) {
      e.printStackTrace();
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


}