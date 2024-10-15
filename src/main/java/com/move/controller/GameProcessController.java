package com.move.controller;

import com.move.model.Cell;
import com.move.model.enums.SnakeDirection;
import com.move.model.Fruit;
import com.move.model.Snake;
import com.move.model.enums.SnakeSpeed;
import com.move.view.ConsolePrinter;
import lc.kra.system.keyboard.GlobalKeyboardHook;

import java.util.*;

public class GameProcessController {
  private static final int WIDTH = 40;
  private static final int HEIGHT = 12;
  private static final String GREETING_MESSAGE = """
          WELCOME T0 THE SNAKE GAME!
          
          Rules and gameplay: 
          1. Firstly you need to choose speed of snake: 
          >> 1 - SLOW
          >> 2 - MEDIUM
          >> 3 - FAST
          2. Press 'W A S D' buttons to choose snake's head direction 
          3. Press 'Enter' to launch this game!""";

  private static final String GAME_OVER = "Game Over!";
  private static final String RESTART = "To restart enter '1', otherwise 'Enter' to quit game!";
  private final GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook();
  private final Scanner scanner = new Scanner(System.in);
  private final Random random = new Random();
  private final ConsolePrinter printer;
  private SnakeDirection snakeDirection = SnakeDirection.RIGHT;
  private SnakeSpeed snakeSpeed = SnakeSpeed.MEDIUM;
  private Snake snake;
  private Fruit fruit;
  private ConsoleController consoleController = new ConsoleController();

  public GameProcessController() {
    this.printer = new ConsolePrinter();
    this.snake = new Snake(WIDTH, HEIGHT);
    this.fruit = new Fruit(random.nextInt(1, WIDTH - 1), random.nextInt(1, HEIGHT - 1));
  }

  public void startConsoleSnake() {
    printer.printLine(GREETING_MESSAGE);
    int snakeSpeed = getSnakeSpeed();
    consoleController.directionListener();
    while (true) {

      snakeSpeed(snakeSpeed);
      Cell head = snake.getSnakeBody().get(0);
      int snakeX = head.x();
      int snakeY = head.y();
      switch (snakeDirection.getDirection()) {
        case 1 -> snakeY -= 1;
        case 2 -> snakeX -= 1;
        case 3 -> snakeY += 1;
        case 4 -> snakeX += 1;
      }
      boolean isFruitEaten = snakeX == fruit.x() && snakeY == fruit.y();
      if (gameOver(new Cell(snakeX, snakeY))) {
        break;
      }
      if (isFruitEaten) {
        snake.expandSnake(snakeX, snakeY);
        fruit = new Fruit(random.nextInt(1, WIDTH - 1), random.nextInt(1, HEIGHT - 1));
      } else {
        snake.moveSnake(snakeX, snakeY);
      }
      printer.printLine("");
      showGameBoard(new Cell(snakeX, snakeY));
      printer.printLine("Score: " + snake.getSnakeBody().size());
      consoleController.clearConsole();
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


  private void snakeSpeed(int speed) {
    int snakeSpeed = switch (speed){
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


  private boolean gameOver(Cell head) {
    if (head.x() == 0 || head.y() == 0 || head.x() == WIDTH - 1 || head.y() == HEIGHT - 1) {
      printer.printLine(GAME_OVER);
      return true;
    }
    List<Cell> snkBody = snake.getSnakeBody().subList(1, snake.getSnakeBody().size());
    if (snkBody.contains(head)) {
      printer.printLine(GAME_OVER);
      return true;
    }
    return false;
  }

  private void showGameBoard(Cell cell) {
    for (int i = 0; i < HEIGHT; i++) {
      for (int j = 0; j < WIDTH; j++) {
        boolean snakeCondition = snake.getSnakeBody().contains(new Cell(j, i));
        if (snakeCondition) {
          printer.print(cell.toString());
        }
        boolean fruitCondition = i == fruit.y() && j == fruit.x();
        if (fruitCondition) {
          printer.print(fruit.toString());
        }
        boolean wallCondition = i == 0 || j == 0 || i == HEIGHT - 1 || j == WIDTH - 1;
        if (wallCondition) {
          printer.print("■");
        }
        if (!fruitCondition && !wallCondition && !snakeCondition) {
          printer.print(" ");
        }
      }
      printer.printLine("");
    }
  }

}