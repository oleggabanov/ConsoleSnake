package com.move.controller;

import com.move.model.Cell;
import com.move.model.Fruit;
import com.move.model.Snake;
import com.move.view.GameBoardView;
import lc.kra.system.keyboard.GlobalKeyboardHook;

import java.util.Scanner;

public class GameController {

  private KeyboardListener listener;
  private GlobalKeyboardHook hook = new GlobalKeyboardHook();
  private final GameConditionController conditionController = new GameConditionController();
  private final GameSpeedController speedController = new GameSpeedController();
  private final FruitController fruitController = new FruitController();
  private Scanner scanner = new Scanner(System.in);
  private Snake snake;
  private Fruit fruit;
  private int bestScore;
  private int currentScore;
  private GameBoardView boardView;

  public GameController(KeyboardListener listener) {
    this.listener = listener;
    initialize();
  }

  private void initialize() {
    this.snake = new Snake();
    this.fruit = fruitController.spawnFruit(snake);
    this.boardView = new GameBoardView(snake, fruit);
    this.bestScore = Math.max(bestScore, currentScore);
    this.currentScore = 0;
    boardView.setGameSpeedController(speedController);
    conditionController.setRestart(false);
    listener.setKeyboardHook(hook);
    speedController.setKeyboardHook(hook);
    startConsoleSnake();
  }

  public void startConsoleSnake() {
    speedController.setRunning(true);
    if (speedController.isRunning()) {
      boardView.printMenu();
      speedController.menuConfigurationListener();
    }

    while (true) {
      System.out.println(speedController.getSpeed());
      listener.directionListener();
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
      }

      if (conditionController.isRestart()) {
        initialize();
      } else {
        System.exit(0);
      }
    }

}
