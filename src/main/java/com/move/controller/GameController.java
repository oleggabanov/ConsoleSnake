package com.move.controller;

import com.move.model.Cell;
import com.move.model.Fruit;
import com.move.model.Snake;
import com.move.view.GameBoardView;

public class GameController {

  private final KeyboardListener listener;
  private final ConsoleController consoleController;
  private final GameSpeedController speedController = new GameSpeedController();
  private final FruitController fruitController = new FruitController();
  private final Snake snake = new Snake();
  private Fruit fruit = fruitController.spawnFruit();
  private final GameBoardView boardView = new GameBoardView(snake, fruit);
  private GameConditionController conditionController = new GameConditionController();

  public GameController(KeyboardListener listener, ConsoleController consoleController) {
    this.listener = listener;
    this.consoleController = consoleController;
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
        break;
      }
      if (isFruitEaten) {
        snake.expandSnake(snakeX, snakeY);
        fruit = fruitController.spawnFruit();
        boardView.setFruit(fruit);
      } else {
        snake.moveSnake(snakeX, snakeY);
      }
      boardView.showGameBoard(new Cell(snakeX, snakeY), snake.getSnakeBody().size() - 2);
      consoleController.clearConsole();
    }
  }


}