package bitcamp.myapp.config;

import bitcamp.util.SqlSessionFactoryProxy;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(
    basePackages = {"bitcamp.myapp.dao", "bitcamp.myapp.controller", "bitcamp.myapp.service"})
public class AppConfig {

  public AppConfig() {
    System.out.println("AppConfig() 호출됨!");
  }
  // Mybatis 객체 준비
  @Bean
  public SqlSessionFactory sqlSessionFactory() throws Exception {
    return new SqlSessionFactoryProxy(new SqlSessionFactoryBuilder().build(
        Resources.getResourceAsStream("bitcamp/myapp/config/mybatis-config.xml")));
  }
}
