package bitcamp.myapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

// Application을 실행하는데 필요한 객체를 설정하는 일을 한다.
//
@ComponentScan(basePackages = "bitcamp.myapp.controller",
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*BoardController"),
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*HomeController"),
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*AuthController")
    }
)
public class AdminConfig {

  public AdminConfig() {
    System.out.println("AdminConfig() 호출됨!");
  }

  @Bean
  public MultipartResolver multipartResolver() {
    return new StandardServletMultipartResolver();
  }

  @Bean
  public ViewResolver viewResolver() {
    InternalResourceViewResolver vr = new InternalResourceViewResolver();
    vr.setViewClass(JstlView.class);
    vr.setPrefix("/WEB-INF/jsp/");
    vr.setSuffix(".jsp");
    return vr;
  }
}

