package com.move.controller;

import com.move.model.Cell;
import com.move.model.Fruit;
import com.move.model.Snake;
import com.move.view.GameBoardView;

import java.util.Scanner;

public class GameController {

  private KeyboardListener listener;
  private final ConsoleController consoleController;
  private final GameSpeedController speedController = new GameSpeedController();
  private final FruitController fruitController = new FruitController();
  private Snake snake;
  private Fruit fruit;
  private GameBoardView boardView;
  private final GameConditionController conditionController = new GameConditionController();
  private Scanner scanner = new Scanner(System.in);
  private int bestScore;
  private int currentScore;

  public GameController(KeyboardListener listener, ConsoleController consoleController) {
    this.listener = listener;
    this.consoleController = consoleController;
    initialize();
  }

  private void initialize() {
    this.snake = new Snake();
    this.fruit = fruitController.spawnFruit(snake);
    this.boardView = new GameBoardView(snake, fruit);
    this.bestScore = Math.max(bestScore, currentScore);
    this.currentScore = 0;
    conditionController.setRestart(false);
    startConsoleSnake();
  }

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
        if (conditionController.isGameOver(new Cell(snakeX, snakeY), snake)) {
          boardView.printGameOver();
          boardView.printRestart();
          conditionController.setRestart(scanner.nextLine().toLowerCase().lastIndexOf('y') != -1);
          break;
        }

        if (isFruitEaten) {
          snake.expandSnake(snakeX, snakeY);
          fruit = fruitController.spawnFruit(snake);
          boardView.setFruit(fruit);
          currentScore++;
        } else {
          snake.moveSnake(snakeX, snakeY);
        }
        boardView.showGameBoard(new Cell(snakeX, snakeY), currentScore, bestScore);
        consoleController.clearConsole();
      }

      if (conditionController.isRestart()) {
        initialize();
      } else {
        System.exit(0);
      }
    }

}
