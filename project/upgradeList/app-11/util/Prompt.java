package bitcamp.util;

import java.util.Scanner;

public class Prompt {

  static Scanner scanner = new java.util.Scanner(System.in);

  public static String inputString(String title) {
    System.out.print(title);
    return scanner.nextLine();
  }

  public static int inputInt(String title) {
    return Integer.parseInt(inputString(title));
  }

  public static void close() {
    scanner.close();
  }
}
