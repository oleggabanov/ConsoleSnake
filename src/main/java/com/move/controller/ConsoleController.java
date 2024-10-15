package com.move.controller;

public class ConsoleController {

  public void clearConsole() {
    try {
      Thread.sleep(250);
      new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
