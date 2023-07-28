package bitcamp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

// - 스레드 별로 DB 커넥션을 관리하고 제공하는 역할
// - 풀링(pooling) 기법을 이용하여 DB 커넥션 재활용
// - GoF의 Flyweight 패턴
//
public class DataSource {

  // DBMS에 연결할 때 사용할 정보
  String jdbcUrl;
  String username;
  String password;

  // 커넥션 목록을 유지하기 위한 객체: 커넥션 풀(connection pool)
  List<Connection> connectionPool = new ArrayList<>();

  // 현재 스레드에 값을 넣고 꺼내는 일을 하는 객체
  ThreadLocal<Connection> connectionBox = new ThreadLocal<>();

  public DataSource(String jdbcUrl, String username, String password) {
    this.jdbcUrl = jdbcUrl;
    this.username = username;
    this.password = password;
  }

  public Connection getConnection(boolean autoCommit) throws Exception {
    Connection con = this.getConnection();
    con.setAutoCommit(autoCommit);
    return con;
  }

  public Connection getConnection() throws Exception {

    // connectionBox를 통해 현재 스레드 보관소에 저장된 Connection 객체를 꺼낸다.
    Connection con = connectionBox.get();

    if (con == null) {
      // 현재 스레드에 장착된 DB 커넥션 객체가 없다면,

      if (connectionPool.size() > 0) {
        // 커넥션풀에 사용할 수 있는 Connection 객체가 있다면 꺼낸다.
        con = connectionPool.remove(0);
        System.out.printf("[%s] - DB 커넥션풀에서 꺼냄!\n", Thread.currentThread().getName());

      } else {
        // 커넥션 풀에 사용할 Connection 객체가 없다면 새로 생성한다.
        con = DriverManager.getConnection(jdbcUrl, username, password);
        con.setAutoCommit(true);
        System.out.printf("[%s] - 새 DB 커넥션 생성!\n", Thread.currentThread().getName());
      }

      // connectionBox를 통해 새로 생성한 Connection 객체를 현재 스레드에 보관한다.
      connectionBox.set(con);
      System.out.printf("[%s] - 스레드에 커넥션 객체 보관!\n", Thread.currentThread().getName());

    } else {
      System.out.printf("[%s] - 스레드에 보관된 커넥션 사용!\n", Thread.currentThread().getName());
    }

    return con;
  }

  public void clean() {
    // 스레드가 작업을 끝냈으면, 이 스레드에 보관된 Connection 객체를 제거한다.
    Connection con = connectionBox.get();
    if (con != null) {
      // 커넥션을 커넥션풀에 반납하기 전에 커넥션에서 수행한 작업 중
      // 미완료 작업은 취소한다.
      try {
        con.rollback();
      } catch (Exception e) {
      }

      // 스레드가 사용한 DB 커넥션을 다른 스레드에서 사용할 수 있도록 커넥션풀에 저장한다.
      // 다음에 다시 사용해야 하기 때문에 close() 하면 안된다.
      connectionPool.add(con);
      System.out.printf("[%s] - 커넥션풀에 DB 커넥션 저장!\n", Thread.currentThread().getName());

      // 스레드에에서는 제거한다.
      connectionBox.remove();

      System.out.printf("[%s] - 스레드에서 커넥션 제거!\n", Thread.currentThread().getName());
    }
  }

}


