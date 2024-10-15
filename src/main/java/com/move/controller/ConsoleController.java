package com.move.controller;

import com.move.model.enums.SnakeDirection;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

public class ConsoleController {

  private final GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook();
  private SnakeDirection snakeDirection;

  public ConsoleController() {
    snakeDirection = SnakeDirection.RIGHT;
  }


  public void clearConsole() {
    try {
      Thread.sleep(250);
      new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  public void directionListener() {
    keyboardHook.addKeyListener(new GlobalKeyAdapter() {
      @Override
      public void keyPressed(GlobalKeyEvent event) {
        char keyChar = event.getKeyChar();
        snakeDirection = switch (String.valueOf(keyChar).toLowerCase()) {
          case "w" -> snakeDirection == SnakeDirection.DOWN ? SnakeDirection.DOWN : SnakeDirection.UP;
          case "a" -> snakeDirection == SnakeDirection.RIGHT ? SnakeDirection.RIGHT : SnakeDirection.LEFT;
          case "s" -> snakeDirection == SnakeDirection.UP ? SnakeDirection.UP : SnakeDirection.DOWN;
          case "d" -> snakeDirection == SnakeDirection.LEFT ? SnakeDirection.LEFT : SnakeDirection.RIGHT;
          default -> throw new IllegalArgumentException("Console Illegal argument!");
        };
      }
    });
  }


  public int getDirection() {
    return snakeDirection.getDirection();
  }
}
