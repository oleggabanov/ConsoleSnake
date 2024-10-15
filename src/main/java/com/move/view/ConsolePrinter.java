package com.move.view;

public interface ConsolePrinter  {

   default void printLine(String s) {
    System.out.println(s);
  }

   default void print(String s) {
    System.out.print(s);
  }

}
