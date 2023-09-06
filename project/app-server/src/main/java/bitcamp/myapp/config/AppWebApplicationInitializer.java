package bitcamp.myapp.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

public class AppWebApplicationInitializer extends
    AbstractAnnotationConfigDispatcherServletInitializer {

  public AppWebApplicationInitializer() {
    System.out.println("AppWebApplicationInitializer 생성!");
  }

  @Override
  protected String getServletName() {
    return "app";
  }

  @Override
  protected Class<?>[] getRootConfigClasses() {
    // ContextLoaderListener의 IoC 컨테이너가 사용할 java config 클래스를 지정한다.
    return new Class[]{RootConfig.class, DbConfig.class, MybatisConfig.class};
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    // DispatcherServlet의 IoC 컨테이너가 사용할 java config 클래스를 지정한다.
    return new Class[]{AppConfig.class};
  }

  @Override
  protected String[] getServletMappings() {
    // DispatcherServlet의 URL을 지정한다.
    return new String[]{"/app/*"};
  }

  @Override
  protected void customizeRegistration(Dynamic registration) {
    registration.setMultipartConfig(
        new MultipartConfigElement(null, 10000000, 15000000, 5000000));
  }

  @Override
  protected Filter[] getServletFilters() {
    return new Filter[]{new CharacterEncodingFilter("UTF-8")};
  }
}
