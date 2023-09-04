package bitcamp.myapp.config;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.SoldierDao;
import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.service.DefaultBoardService;
import bitcamp.myapp.service.DefaultSoldierService;
import bitcamp.myapp.service.SoldierService;
import bitcamp.util.TransactionProxyBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@ComponentScan(basePackages = {
    "bitcamp.myapp.dao",
    "bitcamp.myapp.controller",
    "bitcamp.myapp.service"})
@PropertySource({"classpath:bitcamp/myapp/config/ncloud/jdbc.properties"})
@MapperScan("bitcamp.myapp.dao")
public class AppConfig {

  public AppConfig() {
    System.out.println("AppConfig() 호출됨!");
  }

  @Bean
  public SqlSessionFactory sqlSessionFactory(DataSource dataSource, ApplicationContext appCtx) throws Exception {
    System.out.println("AppConfig.sqlSessionFactory() 호출됨!");

    org.apache.ibatis.logging.LogFactory.useLog4J2Logging();

    SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
    factoryBean.setDataSource(dataSource);
    factoryBean.setTypeAliasesPackage("bitcamp.myapp.vo");
    factoryBean.setMapperLocations(appCtx.getResources("classpath:bitcamp/myapp/dao/mysql/*Dao.xml"));

    return factoryBean.getObject();
  }

  @Bean
  public DataSource dataSource(
      @Value("${jdbc.driver}") String driver,
      @Value("${jdbc.url}") String url,
      @Value("${jdbc.username}") String username,
      @Value("${jdbc.password}") String password) {
    System.out.println("AppConfig.dataSource() 호출됨!");

    DriverManagerDataSource ds = new DriverManagerDataSource();
    ds.setDriverClassName(driver);
    ds.setUrl(url);
    ds.setUsername(username);
    ds.setPassword(password);

    return ds;
  }

  @Bean
  public PlatformTransactionManager transactionManager(DataSource dataSource) {
    System.out.println("AppConfig.transactionManager() 호출됨!");

    return new DataSourceTransactionManager(dataSource);
  }

  @Bean
  public TransactionProxyBuilder txProxyBuilder(PlatformTransactionManager txManager) {
    return new TransactionProxyBuilder(txManager);
  }

  @Bean
  public BoardService boardService(TransactionProxyBuilder txProxyBuilder, BoardDao boardDao) {
    return (BoardService) txProxyBuilder.build(new DefaultBoardService(boardDao));
  }

  @Bean
  public SoldierService soldierService(TransactionProxyBuilder txProxyBuilder, SoldierDao soldierDao) {
    return (SoldierService) txProxyBuilder.build(new DefaultSoldierService(soldierDao));
  }

}
