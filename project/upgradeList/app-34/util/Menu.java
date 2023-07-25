package bitcamp.util;

import java.util.ArrayList;

public class Menu {

  private String title;
  private ArrayList<ActionListener> listeners = new ArrayList<>();

  public Menu(String title) {
    this.title = title;
  }

  public Menu(String title, ActionListener listener) {
    this(title);
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
    for (int i = 0; i < listeners.size(); i++) {
      ActionListener listener = listeners.get(i);
      listener.service(prompt);
    }
  }
}
