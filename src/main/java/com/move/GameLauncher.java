package com.move;

import com.move.controller.ConsoleController;
import com.move.controller.GameController;
import com.move.controller.KeyboardListener;

public class GameLauncher {

  public static void main(String[] args) {
    ConsoleController consoleController = new ConsoleController();
    KeyboardListener keyboardListener = new KeyboardListener();
    GameController gameController = new GameController(keyboardListener, consoleController);
    gameController.startConsoleSnake();
  }

}

