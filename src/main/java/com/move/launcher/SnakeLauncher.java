package com.move.launcher;

import com.move.model.Cell;
import com.move.model.Fruit;
import com.move.model.Snake;
import com.move.view.ConsolePrinter;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

import java.util.Random;
import java.util.Scanner;

public class SnakeLauncher {
  private final GlobalKeyboardHook keyboardHook;
  private final static int WIDTH = 40;
  private final static int HEIGHT = 12;
  private final ConsolePrinter printer;
  private final Scanner scanner;
  private final Random random;
  private int direction;
  private Snake snake;
  private Fruit fruit;

  private static final String GREETING_MESSAGE = """
          WELCOME T0 THE SNAKE GAME!
          
          Rules and gameplay: 
          1. Firstly you need to choose speed of snake: 
          // from 1 to 5 
          2. Press 'W A S D' buttons to choose snake direction. 
          3. Press 'Enter' to launch this game!""";
  private static final String GAME_OVER = "Game Over!";

  public SnakeLauncher() {
    this.scanner = new Scanner(System.in);
    this.printer = new ConsolePrinter();
    this.snake = new Snake();
    this.random = new Random();
    this.fruit = new Fruit(random.nextInt(1, WIDTH - 1), random.nextInt(1, HEIGHT - 1));
    this.keyboardHook = new GlobalKeyboardHook();
    changeDirection();
  }

  public void startConsoleSnake() {
    printer.printLine(GREETING_MESSAGE);
    scanner.nextLine();
    while (true) {
      try {
        Thread.sleep(400);
      } catch (InterruptedException e) {

      }
      Cell cell = snake.getSnakeBody().get(0);
      int snakeX = cell.x();
      int snakeY = cell.y();
      switch (direction) {
        case 1 -> snakeY -= 1;
        case 2 -> snakeX -= 1;
        case 3 -> snakeY += 1;
        case 4 -> snakeX += 1;
      }
      boolean isFruitEaten = cell.x() == fruit.x() && cell.y() == fruit.y();
      snake.moveSnake(snakeX, snakeY);
      expandSnakeWhenItAteFruit(isFruitEaten, snakeX, snakeY);
      printer.printLine("Score: " + snake.getSnakeBody().size());
      showGameBoard(cell);
      if (gameOver(cell)) {break;}
      System.out.flush();
    }
  }

  private void changeDirection() {
    keyboardHook.addKeyListener(new GlobalKeyAdapter() {
      @Override
      public void keyPressed(GlobalKeyEvent event) {
        char keyChar = event.getKeyChar();
        switch (String.valueOf(keyChar).toLowerCase()) {
          case "w" -> direction = 1;
          case "a" -> direction = 2;
          case "s" -> direction = 3;
          case "d" -> direction = 4;
          default -> {}
        }
      }
    });
  }

  private boolean gameOver(Cell cell) {
    if (cell.x() == 0 || cell.y() == 0 || cell.x() == WIDTH - 1 || cell.y() == HEIGHT - 1) {
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

  private void expandSnakeWhenItAteFruit(boolean isFruitEaten, int snakeX, int snakeY) {
    if (isFruitEaten) {
      snake.expandSnake(snakeX, snakeY);
      fruit = new Fruit(random.nextInt(1, WIDTH - 1), random.nextInt(1, HEIGHT - 1));
    }
  }


}