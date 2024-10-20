package com.move.controller;

import com.move.model.enums.SnakeDirection;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

public class KeyboardListener {

  private GlobalKeyboardHook keyboardHook;
  private SnakeDirection snakeDirection;

  public void setKeyboardHook(GlobalKeyboardHook keyboardHook) {
    this.keyboardHook = keyboardHook;
  }

  public KeyboardListener() {
    snakeDirection = SnakeDirection.RIGHT;
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
          default -> snakeDirection;
        };
      }
    });
  }

  public int getDirection() {
    return snakeDirection.getDirection();
  }
}
