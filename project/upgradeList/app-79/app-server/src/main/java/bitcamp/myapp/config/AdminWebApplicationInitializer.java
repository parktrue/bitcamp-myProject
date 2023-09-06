package bitcamp.myapp.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

public class AdminWebApplicationInitializer extends
    AbstractAnnotationConfigDispatcherServletInitializer {

  public AdminWebApplicationInitializer() {
    System.out.println("AdminWebApplicationInitializer 생성!");
  }

  @Override
  protected String getServletName() {
    return "admin";
  }

  @Override
  protected Class<?>[] getRootConfigClasses() {
    // ContextLoaderListener의 IoC 컨테이너가 사용할 java config 클래스를 지정한다.
    // => AppWebApplicationInitializer 에서
    // Rootconfig를 가지고 ContextLoaderListener을 만들었기 때문에,
    // 여기서는 설정하지 않는다!
    return null;
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    // DispatcherServlet의 IoC 컨테이너가 사용할 java config 클래스를 지정한다.
    return new Class[]{AdminConfig.class};
  }

  @Override
  protected String[] getServletMappings() {
    // DispatcherServlet의 URL을 지정한다.
    return new String[]{"/admin/*"};
  }

  @Override
  protected void customizeRegistration(Dynamic registration) {
    registration.setMultipartConfig(
        new MultipartConfigElement(null, 10000000, 15000000, 5000000));
  }

  @Override
  protected Filter[] getServletFilters() {
    return new Filter[] {new CharacterEncodingFilter("UTF-8")};
  }
}
