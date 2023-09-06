package bitcamp.myapp.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

// Application을 실행하는데 필요한 객체를 설정하는 일을 한다.
//
@MapperScan("bitcamp.myapp.dao")
public class MybatisConfig {

  public MybatisConfig() {
    System.out.println("MybatisConfig() 호출됨!");
  }

  @Bean
  public SqlSessionFactory sqlSessionFactory(DataSource dataSource, ApplicationContext appCtx)
      throws Exception {
    System.out.println("MybatisConfig.sqlSessionFactory() 호출");

    // Mybatis 에서 Log4j 2.x 버전을 사용하도록 활성화시킨다.
    // 활성화 시키지 않으면 Mybatis에서 로그를 출력하지 않는다!
    org.apache.ibatis.logging.LogFactory.useLog4J2Logging();

    SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
    factoryBean.setDataSource(dataSource);
    factoryBean.setTypeAliasesPackage("bitcamp.myapp.vo");

    factoryBean.setMapperLocations(
        appCtx.getResources("classpath:bitcamp/myapp/dao/mysql/*Dao.xml"));
    return factoryBean.getObject();
  }
}

