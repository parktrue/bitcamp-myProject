package bitcamp.app_11;

import bitcamp.myapp.handler.SoldierHandler;
import bitcamp.util.Prompt;

public class App {

  public static void main(String[] args) {
    // 키보드 스캐너 준비
    // 각 데이터를 키보드를 통해 입력받아서 출력한다.

    printTitle();
    printMenu();

    while (true) {
      String menuNo = Prompt.inputString("메인> ");
      if (menuNo.equals("6")) {
        break;
      } else if (menuNo.equals("menu")) {
        printMenu();
      } else if (menuNo.equals("1")) {
        SoldierHandler.inputSoldier();
      } else if (menuNo.equals("2")) {
        SoldierHandler.printSoldiers();
      } else if (menuNo.equals("3")) {
        SoldierHandler.viewSoldier();
      } else if (menuNo.equals("4")) {
        SoldierHandler.updateSoldier();
      } else if (menuNo.equals("5")) {
        SoldierHandler.deleteSoldier();
      } else {
        System.out.println(menuNo);
      }
    }

    Prompt.close();
  }

  static void printMenu() {
    System.out.println("1. 군인등록");
    System.out.println("2. 군인목록");
    System.out.println("3. 군인조회");
    System.out.println("4. 군인변경");
    System.out.println("5. 군인삭제");
    System.out.println("6. 종료");
  }

  static void printTitle() {
    System.out.println("병력 관리 시스템");
    System.out.println("-------------------------------\n");
  }
}
