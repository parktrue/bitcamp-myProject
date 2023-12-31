package bitcamp.myapp.handler;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import bitcamp.util.ActionListener;
import bitcamp.util.BreadcrumbPrompt;

public interface SoldierActionListener extends ActionListener {
  static String inputRank(String rank, BreadcrumbPrompt prompt) throws IOException {
    String label;
    if (rank == null) {
      label = "계급?\n";
    } else {
      label = String.format("계급(%s)?\n", rank);
    }

    while (true) {
      String menuNo =
          prompt.inputString(label + "  1. 이병\n" + "  2. 일병\n" + "  3. 상병\n" + "  4. 병장\n" + "> ");

      switch (menuNo) {
        case "1":
          return "이병";
        case "2":
          return "일병";
        case "3":
          return "상병";
        case "4":
          return "병장";
        default:
          prompt.println("무효한 번호입니다.");
      }
    }
  }

  default LocalDate inputLocalDate(BreadcrumbPrompt prompt) throws IOException {
    LocalDate minEnlistmentDate = LocalDate.now().minusMonths(18);

    while (true) {
      prompt.printf("입대일 입력 가능 기간은 %s 부터 %s 까지입니다.\n", minEnlistmentDate, LocalDate.now());
      String inputDate = prompt.inputString("입대일을 입력하세요 (yyyy-MM-dd 형식): ");
      try {
        LocalDate enlistmentDate = LocalDate.parse(inputDate);
        if (enlistmentDate.isBefore(minEnlistmentDate) || enlistmentDate.isAfter(LocalDate.now())) {
          prompt.println("입력 가능한 범위를 벗어났습니다. 다시 입력해주세요.");
          continue;
        }
        return enlistmentDate;
      } catch (DateTimeParseException e) {
        prompt.println("입력한 날짜의 형식이 올바르지 않습니다. 'yyyy-MM-dd' 형식으로 다시 입력하세요.");
      }
    }
  }
}
