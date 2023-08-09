package bitcamp.util;

import java.sql.Connection;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.TransactionIsolationLevel;

public class SqlSessionFactoryProxy implements SqlSessionFactory {

  SqlSessionFactory original;

  ThreadLocal<SqlSession> sqlSessionBox = new ThreadLocal<>();

  public SqlSessionFactoryProxy(SqlSessionFactory original) {
    this.original = original;
  }

  public void clean() {
    SqlSession sqlSession = sqlSessionBox.get();
    if (sqlSession != null) {
      sqlSession.rollback();
      sqlSessionBox.remove();
    }
  }

  public SqlSession openSession() {
    return original.openSession();
  }

  public SqlSession openSession(boolean autoCommit) {

    if (!autoCommit) {
      // 수동 커밋으로 동작하는 SqlSession 을 원한다는 것은
      // 여러 데이터 변경 작업을 묶어서 다루겠다는 의미다.
      // 그렇게 하려면 동일한 SqlSession 객체를 사용해야 한다.
      // 이를 위해 스레드에 SqlSession 객체를 보관해두고 리턴한다.

      // 1) 스레드에 보관된 SqlSession 객체를 꺼낸다.
      SqlSession sqlSession = sqlSessionBox.get();

      if (sqlSession == null) {
        // 2) 아직 스레드에 보관된 객체가 없다면 새로 만들어 보관한다.
        sqlSession = original.openSession(false);
        sqlSessionBox.set(sqlSession);
      }

      // 3) 스레드에 보관된 SqlSession 객체를 리턴한다.
      return sqlSession;
    }

    return original.openSession(autoCommit);
  }

  public SqlSession openSession(Connection connection) {
    return original.openSession(connection);
  }

  public SqlSession openSession(TransactionIsolationLevel level) {
    return original.openSession(level);
  }

  public SqlSession openSession(ExecutorType execType) {
    return original.openSession(execType);
  }

  public SqlSession openSession(ExecutorType execType, boolean autoCommit) {
    return original.openSession(execType, autoCommit);
  }

  public SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level) {
    return original.openSession(execType, level);
  }

  public SqlSession openSession(ExecutorType execType, Connection connection) {
    return original.openSession(execType, connection);
  }

  public Configuration getConfiguration() {
    return original.getConfiguration();
  }
}
