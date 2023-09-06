package bitcamp.myapp.listener;

import bitcamp.myapp.config.AppConfig;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;

//@WebListener
public class ApplicationInitListener implements ServletContextListener {

  public ApplicationInitListener() {
    System.out.println("ApplicationInitListener() 생성");
  }

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    // 서블릿 컨테이너가 웹 애플리케이션을 로딩할때 이 메서드를 호출한다.
    ServletContext sc = sce.getServletContext();

    // DispatcherServlet이 사용할 IoC컨테이너를 준비한다.
    AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
    context.register(AppConfig.class);

    // DispatcherServlet을 생성하여 서블릿 컨테이너에 등록한다.
    DispatcherServlet servlet = new DispatcherServlet(context);
    ServletRegistration.Dynamic registration = sc.addServlet("app", servlet);
    registration.setLoadOnStartup(1);
    registration.addMapping("/app/*");
    registration.setMultipartConfig(new MultipartConfigElement("/tmp", 10000000, 15000000, 5000000));
  }
}
