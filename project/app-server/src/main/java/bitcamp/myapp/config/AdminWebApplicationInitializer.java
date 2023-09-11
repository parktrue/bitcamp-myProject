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
    return null;
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class[]{AdminConfig.class};
  }

  @Override
  protected String[] getServletMappings() {
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
