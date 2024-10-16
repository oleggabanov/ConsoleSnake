package com.move.view;

import com.move.model.*;

import static com.move.model.BoardParams.*;

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

  private static final String GAME_OVER = "Game Over!";
  private static final String RESTART = "To restart enter '1', otherwise 'Enter' to quit game!";
  private static final String SCORE = "Score: %d";

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

  public void showGameBoard(Cell cell, int score) {
    printLine("");
    for (int i = 0; i < HEIGHT; i++) {
      for (int j = 0; j < WIDTH; j++) {
        boolean snakeCondition = snake.getSnakeBody().contains(new Cell(j, i));
        if (snakeCondition) {
          print(cell.toString());
        }
        boolean fruitCondition = i == fruit.y() && j == fruit.x();
        if (fruitCondition) {
          print(fruit.toString());
        }
        boolean wallCondition = i == 0 || j == 0 || i == HEIGHT - 1 || j == WIDTH - 1;
        if (wallCondition) {
          print("■");
        }
        if (!fruitCondition && !wallCondition && !snakeCondition) {
          print(" ");
        }
      }
      printLine("");
    }
    printLine("");
    printLine(String.format(SCORE, score));
  }

  public void setFruit(Fruit fruit) {
    this.fruit = fruit;
  }
}
