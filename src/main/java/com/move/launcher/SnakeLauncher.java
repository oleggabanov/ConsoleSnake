package com.move.launcher;

import com.move.model.Cell;
import com.move.model.Direction;
import com.move.model.Fruit;
import com.move.model.Snake;
import com.move.view.ConsolePrinter;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

import java.util.*;

public class SnakeLauncher {
  private static final int WIDTH = 40;
  private static final int HEIGHT = 12;
  private static final String GREETING_MESSAGE = """
          WELCOME T0 THE SNAKE GAME!
          
          Rules and gameplay: 
          1. Firstly you need to choose speed of snake: 
          // 1, 2 or 3 
          2. Press 'W A S D' buttons to choose snake direction. 
          3. Press 'Enter' to launch this game!""";

  private static final String GAME_OVER = "Game Over!";
  private static final String RESTART = "To restart enter '1', otherwise 'Enter' to quit game!";
  private final GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook();
  private final Scanner scanner = new Scanner(System.in);
  private final Random random = new Random();
  private Direction direction = Direction.RIGHT;
  private final ConsolePrinter printer;
  private Snake snake;
  private Fruit fruit;
  private List<Integer> chooseSnakeSpeed = new ArrayList<>(Arrays.asList(0, 250, 300, 400));

  public SnakeLauncher() {
    this.printer = new ConsolePrinter();
    this.snake = new Snake(WIDTH, HEIGHT);
    this.fruit = new Fruit(random.nextInt(1, WIDTH - 1), random.nextInt(1, HEIGHT - 1));
  }

  public void startConsoleSnake() {
    printer.printLine(GREETING_MESSAGE);
    int snakeSpeed = getSnakeSpeed();
    directionListener();
    while (true) {

      snakeSpeed(snakeSpeed);
      Cell head = snake.getSnakeBody().get(0);
      int snakeX = head.x();
      int snakeY = head.y();
      switch (direction.getDirection()) {
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
      clearConsole();
    }



  }

  private static void clearConsole() {
    try {
      Thread.sleep(250);
      new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    } catch (Exception E) {
      System.out.println(E);
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
    try {
      Thread.sleep(chooseSnakeSpeed.get(speed));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }


  private void directionListener() {
    keyboardHook.addKeyListener(new GlobalKeyAdapter() {
      @Override
      public void keyPressed(GlobalKeyEvent event) {
        char keyChar = event.getKeyChar();
        direction = switch (String.valueOf(keyChar).toLowerCase()) {
          case "w" -> direction == Direction.DOWN ? Direction.DOWN : Direction.UP;
          case "a" -> direction == Direction.RIGHT ? Direction.RIGHT : Direction.LEFT;
          case "s" -> direction == Direction.UP ? Direction.UP : Direction.DOWN;
          case "d" -> direction == Direction.LEFT ? Direction.LEFT : Direction.RIGHT;
          default -> {
            throw new IllegalArgumentException("Illegal argument!");
          }
        };
      }
    });
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