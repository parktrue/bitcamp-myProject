package bitcamp.myapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableTransactionManagement
@SpringBootApplication
public class App implements WebMvcConfigurer {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(App.class, args);
  }

  /*  @Bean
  public MultipartResolver multipartResolver() {
    return new StandardServletMultipartResolver();
  }*/

  /*
  @Bean
  public ViewResolver viewResolver() {
    InternalResourceViewResolver vr = new InternalResourceViewResolver();
    vr.setViewClass(JstlView.class);
    vr.setPrefix("/WEB-INF/jsp/");
    vr.setSuffix(".jsp");
    return vr;
    }
  */

  /*
  @Override
  public void configurePathMatch(PathMatchConfigurer configurer) {
    System.out.println("AppConfig.configurePathMatch() 호출!");
    UrlPathHelper urlPathHelper = new UrlPathHelper();

    // @MatrixVariable 기능 활성화
    urlPathHelper.setRemoveSemicolonContent(false);

    // DispatcherServlet의 MVC Path관련 설정을 변경한다.
    configurer.setUrlPathHelper(urlPathHelper);
  }
  */

  /*
  @Override
    public void addInterceptors(InterceptorRegistry registry) {
      System.out.println("AdminConfig.addInterceptors() 호출!");

     registry
         .addInterceptor(new MyInterceptor())
          .addPathPatterns("/c04_1/**");
    }
  */
}
