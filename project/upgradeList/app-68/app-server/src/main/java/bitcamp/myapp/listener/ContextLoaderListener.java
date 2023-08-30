package bitcamp.myapp.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

// 웹애플리케이션 실행에 필요한 설정이나 객체를 준비한다.
// 언제? 웹애플리케이션 시작될 때!
// @WebListener
public class ContextLoaderListener implements ServletContextListener {
  @Override
  public void contextInitialized(ServletContextEvent sce) {
  }
}
