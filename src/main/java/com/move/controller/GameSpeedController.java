package com.move.controller;

import com.move.model.enums.SnakeSpeed;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import lc.kra.system.keyboard.event.GlobalKeyListener;

import static com.move.view.GameBoardView.menu;

public class GameSpeedController {

  private GlobalKeyboardHook keyboardHook;
  private SnakeSpeed snakeSpeed;
  private int menuItemIndex;

  public SnakeSpeed getSpeed() {
    return snakeSpeed;
  }

  public GameSpeedController() {
    this.snakeSpeed = SnakeSpeed.MEDIUM;
  }

  public void setKeyboardHook(GlobalKeyboardHook keyboardHook) {
    this.keyboardHook = keyboardHook;
  }

  public boolean isRunning() {
    return running;
  }

  public void setRunning(boolean running) {
    this.running = running;
  }

  public int getMenuItemIndex() {
    return menuItemIndex;
  }

  private boolean running;

  public void menuConfigurationListener() {
    keyboardHook.addKeyListener(new GlobalKeyListener() {
      @Override
      public void keyPressed(GlobalKeyEvent globalKeyEvent) {
        switch (globalKeyEvent.getVirtualKeyCode()) {
          case GlobalKeyEvent.VK_UP:
            menuItemIndex = menuItemIndex > 0 ? menuItemIndex - 1 : menu.length - 1;
            break;
          case GlobalKeyEvent.VK_DOWN:
            menuItemIndex = menuItemIndex < menu.length - 1 ? menuItemIndex + 1 : 0;
            break;
          case GlobalKeyEvent.VK_RETURN:
            snakeSpeed = getSnakeSpeed();
            running = false;
            break;
          case GlobalKeyEvent.VK_ESCAPE:
            running = false;
            break;
        }
      }

      @Override
      public void keyReleased(GlobalKeyEvent globalKeyEvent) {

      }
    });
    while (isRunning()) {
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private SnakeSpeed getSnakeSpeed() {
    return switch (menuItemIndex) {
      case 0 -> SnakeSpeed.SLOW;
      case 2 -> SnakeSpeed.FAST;
      default -> SnakeSpeed.MEDIUM;
    };
  }

  public void delay() {
    try {
      Thread.sleep(snakeSpeed.getSpeed());
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
