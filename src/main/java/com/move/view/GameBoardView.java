package com.move.view;

import com.move.model.*;
import org.fusesource.jansi.AnsiConsole;

import static com.move.model.BoardParams.*;
import static org.fusesource.jansi.Ansi.ansi;

public class GameBoardView implements ConsolePrinter {

  private static final String GREETING_MESSAGE = """
          WELCOME T0 THE SNAKE GAME!
          
          Rules and gameplay: 
          1. Firstly you need to choose speed of snake: 
          >> 1 - SLOW
          >> 2 - MEDIUM
          >> 3 - FAST
          2. Press 'W A S D' buttons to choose snake's head direction 
          3. Press 'Enter' to launch this game!""";

  private static final String GAME_OVER = "Game Over!\n";
  private static final String RESTART = "Wish to continue (y/n)\n";
  private static final String SCORE = "Score: %d \t Best score: %d";
  private static final String WALL_CHAR = "■";
  private Snake snake;
  private Fruit fruit;

  public GameBoardView(Snake snake, Fruit fruit) {
    this.snake = snake;
    this.fruit = fruit;
  }

  public void printGreetingMessage() {
    printLine(GREETING_MESSAGE);
  }

  public void printGameOver() {
    print(GAME_OVER);
  }

  public void printRestart() {
    print(RESTART);
  }


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
          print(WALL_CHAR);
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
