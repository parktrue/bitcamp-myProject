package bitcamp.util;

import java.util.ArrayList;

public class MenuGroup extends Menu {

  ArrayList<Menu> childs;

  public MenuGroup(String path, String title) {
    super(path, title);
    this.childs = new ArrayList<>();
  }

  public void add(Menu menu) {
    this.childs.add(menu);
  }

  public void add(String menuPath, String title, ActionListener listener) {
    this.childs.add(new Menu(menuPath, title, listener));
  }

  @Override
  public void execute(BreadcrumbPrompt prompt) {
    try {
      prompt.appendBreadcrumb(this.getTitle());

      this.printMenu(prompt);

      while (true) {
        String input = prompt.inputMenu();
        if (input.equals("menu")) {
          this.printMenu(prompt);
          continue;
        }

        try {
          int menuNo = Integer.parseInt(input);
          if (menuNo < 0 || menuNo > childs.size()) {
            prompt.println("메뉴 번호가 옳지 않습니다!");
            prompt.end();
          } else if (menuNo == 0) {
            prompt.removeBreadcrumb();
            return;
          } else {
            Menu menu = this.childs.get(menuNo - 1);
            menu.execute(prompt);
          }
        } catch (Exception e) {
          prompt.printf("실행 오류: %s\n", e.getMessage());
          prompt.end();
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void printMenu(BreadcrumbPrompt prompt) throws Exception {
    for (int i = 0; i < childs.size(); i++) {
      Menu menu = childs.get(i);
      prompt.printf("%d. %s\n", i + 1, menu.getTitle());
    }
    prompt.println("0. 이전/종료");
    prompt.end();
  }
}
