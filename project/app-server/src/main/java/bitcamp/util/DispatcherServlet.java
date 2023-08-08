package bitcamp.util;

import java.io.IOException;

public class DispatcherListener implements ActionListener {

  ApplicationContext iocContainer;

  public DispatcherListener(ApplicationContext iocContainer) throws Exception {
    this.iocContainer = iocContainer;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {

    ActionListener listener =
        (ActionListener) iocContainer.getBean((String) prompt.getAttribute("menuPath"));
    if (listener == null) {
      throw new RuntimeException("해당 요청을 처리할 수 없습니다.");
    }
    listener.service(prompt);
  }
}
