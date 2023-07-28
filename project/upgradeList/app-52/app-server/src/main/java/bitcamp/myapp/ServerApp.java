package bitcamp.myapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.MySQLBoardDao;
import bitcamp.myapp.dao.MySQLSoldierDao;
import bitcamp.myapp.dao.SoldierDao;
import bitcamp.myapp.handler.BoardAddListener;
import bitcamp.myapp.handler.BoardDeleteListener;
import bitcamp.myapp.handler.BoardDetailListener;
import bitcamp.myapp.handler.BoardListListener;
import bitcamp.myapp.handler.BoardUpdateListener;
import bitcamp.myapp.handler.LoginListener;
import bitcamp.myapp.handler.SoldierAddListener;
import bitcamp.myapp.handler.SoldierDeleteListener;
import bitcamp.myapp.handler.SoldierDetailListener;
import bitcamp.myapp.handler.SoldierListListener;
import bitcamp.myapp.handler.SoldierUpdateListener;
import bitcamp.myapp.handler.SoldierUpdater;
import bitcamp.net.NetProtocol;
import bitcamp.util.BreadcrumbPrompt;
import bitcamp.util.Menu;
import bitcamp.util.MenuGroup;
import bitcamp.util.SqlSessionFactoryProxy;

public class ServerApp {
  ExecutorService threadPool = Executors.newFixedThreadPool(10);

  SqlSessionFactory sqlSessionFactory;

  SoldierDao soldierDao;
  BoardDao boardDao;
  MenuGroup mainMenu = new MenuGroup("메인");
  int port;

  public ServerApp(int port) throws Exception {
    this.port = port;

    // 1) mybatis 설정 파일을 읽어들일 도구를 준비한다.
    InputStream mybatisConfigIn =
        Resources.getResourceAsStream("bitcamp/myapp/config/mybatis-config.xml");

    // 2) SqlSessionFactory를 만들어줄 빌더 객체 준비
    SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();

    // 3) 빌더 객체를 통해 SqlSessionFactory를 생성
    sqlSessionFactory = new SqlSessionFactoryProxy(builder.build(mybatisConfigIn));
    this.soldierDao = new MySQLSoldierDao(sqlSessionFactory);
    this.boardDao = new MySQLBoardDao(sqlSessionFactory);
    prepareMenu();
  }

  public void close() throws Exception {}

  public static void main(String[] args) throws Exception {
    ServerApp app = new ServerApp(8888);
    app.execute();
    app.close();
  }

  public void execute() {
    try (ServerSocket serverSocket = new ServerSocket(this.port)) {
      System.out.println("서버 실행 중...");

      while (true) {
        Socket socket = serverSocket.accept();
        threadPool.execute(() -> processRequest(socket));
      }
    } catch (Exception e) {
      System.out.println("서버 실행 오류!");
      e.printStackTrace();
    }
  }

  private void processRequest(Socket socket) {
    try (Socket s = socket;
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

      BreadcrumbPrompt prompt = new BreadcrumbPrompt(in, out);
      InetSocketAddress clientAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
      System.out.printf("%s 클라이언트 접속함!\n", clientAddress.getHostString());

      out.writeUTF("==================\n" + "[🪖병력 관리 시스템🪖]\n" + "==================\n");

      new LoginListener(soldierDao).service(prompt);
      mainMenu.execute(prompt);
      out.writeUTF(NetProtocol.NET_END);

    } catch (Exception e) {
      System.out.println("클라이언트 통신 오류!");
      e.printStackTrace();
    } finally {
      ((SqlSessionFactoryProxy) sqlSessionFactory).clean();
    }
  }

  private void prepareMenu() {
    MenuGroup soldierMenu = new MenuGroup("병력관리");
    soldierMenu.add(new Menu("등록", new SoldierAddListener(soldierDao, sqlSessionFactory)));
    soldierMenu.add(new Menu("목록", new SoldierListListener(soldierDao)));
    soldierMenu.add(new Menu("조회", new SoldierDetailListener(soldierDao, sqlSessionFactory)));
    soldierMenu.add(new Menu("변경", new SoldierUpdateListener(soldierDao, sqlSessionFactory)));
    soldierMenu.add(new Menu("삭제", new SoldierDeleteListener(soldierDao, sqlSessionFactory)));
    soldierMenu.add(new Menu("DB 업데이트", new SoldierUpdater(soldierDao, sqlSessionFactory)));
    mainMenu.add(soldierMenu);

    MenuGroup boardMenu = new MenuGroup("게시글");
    boardMenu.add(new Menu("등록", new BoardAddListener(1, boardDao, sqlSessionFactory)));
    boardMenu.add(new Menu("목록", new BoardListListener(1, boardDao)));
    boardMenu.add(new Menu("조회", new BoardDetailListener(1, boardDao, sqlSessionFactory)));
    boardMenu.add(new Menu("변경", new BoardUpdateListener(1, boardDao, sqlSessionFactory)));
    boardMenu.add(new Menu("삭제", new BoardDeleteListener(1, boardDao, sqlSessionFactory)));
    mainMenu.add(boardMenu);
  }
}

