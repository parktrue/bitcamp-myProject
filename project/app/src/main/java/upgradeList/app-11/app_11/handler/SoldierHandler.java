package bitcamp.app_11.handler;

import bitcamp.app_11.vo.Soldier;
import bitcamp.util.Prompt;

public class SoldierHandler {

  static final int MAX_SIZE = 100;
  static Soldier[] Soldiers = new Soldier[MAX_SIZE];

  static int userId = 1;
  static int length = 0;

  static final String SERGEANT = "병장";
  static final String CORPORAL = "상병";
  static final String PRIVATEFC = "일병";
  static final String PRIVATE = "이병";

  public static void inputSoldier() {
    if (!available()) {
      System.out.println("더이상 입력할 수 없습니다!");
      return;
    }

    Soldier m = new Soldier();
    m.name = Prompt.inputString("이름? ");
    m.age = Prompt.inputString("나이? ");
    m.rank = inputRank("계급? ");
    m.no = userId++;

    Soldiers[length++] = m;
  }

  public static void printSoldiers() {
    System.out.println("-----------------------------");
    System.out.println("번호, 이름, 나이, 계급");
    System.out.println("-----------군인 목록-----------");
    for (int i = 0; i < length; i++) {
      Soldier m = Soldiers[i];
      System.out.printf("%d, %s, %s, %s\n", m.no, m.name, m.age, m.rank);
    }
  }

  public static void viewSoldier() {
    String SoldierNo = Prompt.inputString("번호? ");
    for (int i = 0; i < length; i++) {
      Soldier m = Soldiers[i];
      if (m.no == Integer.parseInt(SoldierNo)) {
        System.out.printf("이름: %s\n", m.name);
        System.out.printf("나이: %s\n", m.age);
        System.out.printf("계급: %s\n", m.rank);
        return;
      }
    }
    System.out.println("해당 번호의 군인이 없습니다!");
  }

  public static void updateSoldier() {
    String SoldierNo = Prompt.inputString("변경할 군인번호? ");
    for (int i = 0; i < length; i++) {
      Soldier m = Soldiers[i];
      if (m.no == Integer.parseInt(SoldierNo)) {
        System.out.printf("이름(%s)?", m.name);
        m.name = Prompt.inputString("");
        System.out.printf("나이(%s)?", m.age);
        m.age = Prompt.inputString("");
        System.out.println("계급");
        m.rank = inputRank(String.format("계급(%s)?", m.rank));
        return;
      }
    }
    System.out.println("해당 번호의 군인이 없습니다!");
  }

  private static String inputRank(String label) {
    while (true) {
      String menuNo =
          Prompt.inputString(label + "\n1. 병장\n" + "2. 상병\n" + "3. 일병\n" + "4. 이병\n" + "> ");
      switch (menuNo) {
        case "1":
          return SERGEANT;
        case "2":
          return CORPORAL;
        case "3":
          return PRIVATEFC;
        case "4":
          return PRIVATE;
        default:
          System.out.println("무효한 번호입니다.");
      }
    }
  }

  public static void deleteSoldier() {
    int SoldierNo = Prompt.inputInt("삭제할 군인번호? ");
    int deletedIndex = indexOf(SoldierNo);
    if (deletedIndex == -1) {
      System.out.println("해당 번호의 군인이 없습니다!");
      return;
    }

    for (int i = deletedIndex; i < length - 1; i++) {
      Soldiers[i] = Soldiers[i + 1];
    }

    Soldiers[--length] = null;
  }

  private static int indexOf(int SoldierNo) {
    for (int i = 0; i < length; i++) {
      Soldier m = Soldiers[i];
      if (m.no == SoldierNo) {
        return i;
      }
    }
    return -1;
  }

  private static boolean available() {
    return length < MAX_SIZE;
  }
}
