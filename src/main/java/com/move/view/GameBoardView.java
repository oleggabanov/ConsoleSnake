package com.move.view;

import com.move.controller.GameSpeedController;
import com.move.model.*;
import org.fusesource.jansi.AnsiConsole;

import static com.move.model.BoardParams.*;
import static org.fusesource.jansi.Ansi.ansi;
import com.move.controller.GameSpeedController.*;
public class GameBoardView implements ConsolePrinter {

  public static final String[] menu = {
          "SLOW",
          "MEDIUM",
          "FAST"
  };
  private static final String GAME_OVER = "Game Over!\n";
  private static final String RESTART = "Wish to continue (y/n)\n";
  private static final String SCORE = "Score: %d \t Best score: %d";
  private static final String WALL = "■";
  private Snake snake;
  private Fruit fruit;
  private GameSpeedController gameSpeedController;

  public void setGameSpeedController(GameSpeedController gameSpeedController) {
    this.gameSpeedController = gameSpeedController;
  }

  public void printMenu() {
    clearScreen();
    printLine("SNAKE GAME");

    for (int i = 0; i < menu.length; i++) {
      if (i == gameSpeedController.getMenuItemIndex()) {
        System.out.println("\u001B[32m> " + menu[i] + " <\u001B[0m");
      } else {
        printLine("  " + menu[i]);
      }
    }
  }

  public void handleSelection() {
    clearScreen();
    printLine("You selected: " + menu[gameSpeedController.getMenuItemIndex()]);
    if (menu[gameSpeedController.getMenuItemIndex()].equalsIgnoreCase("Option 3: Exit")) {
      gameSpeedController.setRunning(false);
    }
  }

  public void clearScreen() {
    System.out.print("\u001B[H\u001B[2J");
    System.out.flush();
  }

  public GameBoardView(Snake snake, Fruit fruit) {
    this.snake = snake;
    this.fruit = fruit;
  }

  public void printGameOver() {
    print(GAME_OVER);
  }

  public void printRestart() {
    print(RESTART);
  }

//  public void moveToPosition(int row, int column) {
//    System.out.print("\u001B[" + row + ";" + column + "H");
//  }

  public void showGameBoard(Cell cell, int score, int bestScore) {
    AnsiConsole.systemInstall();
    print(ansi().a("\u001B[?25l").toString());
    print(ansi().cursor(0, 0).toString());
    print(ansi().a("\u001B[H\u001B[2J").toString());
    for (int i = 0; i < HEIGHT; i++) {
      for (int j = 0; j < WIDTH; j++) {
        boolean snakeCondition = snake.getSnakeBody().contains(new Cell(j, i));
        if (snakeCondition) {
          print(ansi().fgGreen().a(cell).reset().toString());
        } else if (i == fruit.y() && j == fruit.x()) {
          print(ansi().fgBrightYellow().a(fruit).reset().toString());
        } else if (i == 0 || j == 0 || i == HEIGHT - 1 || j == WIDTH - 1) {
          print(WALL);
        } else {
          print(" ");
        }
      }
      printLine("");
    }
    printLine(String.format(SCORE, score, bestScore));
    print(ansi().a("\u001B[?25h").toString());
  }


  public void setFruit(Fruit fruit) {
    this.fruit = fruit;
  }
}
