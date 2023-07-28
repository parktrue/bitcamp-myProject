package bitcamp.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Stack;

public class BreadcrumbPrompt extends Prompt {

  private Stack<String> breadcrumbs = new Stack<>();

  public BreadcrumbPrompt(DataInputStream in, DataOutputStream out) {
    super(in, out);
  }

  public void appendBreadcrumb(String title) {
    this.breadcrumbs.push(title);
  }

  public void removeBreadcrumb() {
    this.breadcrumbs.pop();
  }

  public String inputMenu() throws Exception {
    StringBuilder titleBuilder = new StringBuilder(); // 예) 메인/회원>
    for (int i = 0; i < this.breadcrumbs.size(); i++) {
      if (titleBuilder.length() > 0) {
        titleBuilder.append("/");
      }
      titleBuilder.append(this.breadcrumbs.get(i));
    }
    titleBuilder.append("> ");
    return this.inputString(titleBuilder.toString());
  }

  public LocalDate inputLocalDate(String message) throws Exception {
    while (true) {
      String input = inputString(message);
      try {
        return LocalDate.parse(input);
      } catch (DateTimeParseException e) {
        System.out.println("입력한 날짜의 형식이 올바르지 않습니다. 'yyyy-MM-dd' 형식으로 다시 입력하세요.");
      }
    }
  }
}


