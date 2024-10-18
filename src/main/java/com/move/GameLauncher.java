package com.move;

import com.move.controller.GameController;
import com.move.controller.KeyboardListener;

public class GameLauncher {

  public static void main(String[] args) {
    KeyboardListener keyboardListener = new KeyboardListener();
    GameController gameController = new GameController(keyboardListener);
    gameController.startConsoleSnake();
  }

}

