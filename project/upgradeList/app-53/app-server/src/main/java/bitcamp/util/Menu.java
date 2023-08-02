package bitcamp.util;

import java.util.ArrayList;

public class Menu {

  private String path; // 메뉴를 식별하는 경로
  private String title; // 메뉴의 제목
  private ArrayList<ActionListener> listeners = new ArrayList<>();

  public Menu(String path, String title) {
    this.path = path;
    this.title = title;
  }

  public Menu(String path, String title, ActionListener listener) {
    this(path, title);
    this.addActionListener(listener);
  }

  public void addActionListener(ActionListener listener) {
    listeners.add(listener);
  }

  public void removeActionListener(ActionListener listener) {
    listeners.remove(listener);
  }

  public String getTitle() {
    return title;
  }

  public void execute(BreadcrumbPrompt prompt) {
    try {
      // 메뉴의 요청을 처리할 리스너를 실행하기 전에
      // 리스너가 메뉴를 알 수 있도록 메뉴의 경로를 설정한다.
      prompt.setAttribute("menuPath", this.path);

      for (int i = 0; i < listeners.size(); i++) {
        ActionListener listener = listeners.get(i);
        listener.service(prompt);
      }
    } catch (Exception e) {
      prompt.clear();
      prompt.println(e.getMessage());

    } finally {
      try {
        prompt.end();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
}
