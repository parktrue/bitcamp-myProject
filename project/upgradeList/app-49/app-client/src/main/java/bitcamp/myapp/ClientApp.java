package bitcamp.myapp;

import java.sql.Connection;
import java.sql.DriverManager;
import bitcamp.dao.MySQLBoardDao;
import bitcamp.dao.MySQLSoldierDao;
import bitcamp.myapp.dao.BoardDao;
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
import bitcamp.myapp.vo.Soldier;
import bitcamp.util.BreadcrumbPrompt;
import bitcamp.util.Menu;
import bitcamp.util.MenuGroup;

public class ClientApp {

  public static Soldier loginUser;

  SoldierDao soldierDao;
  BoardDao boardDao;

  BreadcrumbPrompt prompt = new BreadcrumbPrompt();

  MenuGroup mainMenu = new MenuGroup("메인");

  public ClientApp(String ip, int port) throws Exception {

    Connection con = DriverManager.getConnection("jdbc:mysql://study:1111@localhost:3306/studydb");

    this.soldierDao = new MySQLSoldierDao(con);
    this.boardDao = new MySQLBoardDao(con, 1);

    prepareMenu();
  }

  public void close() throws Exception {
    prompt.close();
  }

  public static void main(String[] args) throws Exception {
    if (args.length < 2) {
      System.out.println("실행 예) java... bitcamp.myapp.ClientApp 서버주소 포트번호");
      return;
    }
    ClientApp app = new ClientApp(args[0], Integer.parseInt(args[1]));
    app.execute();
    app.close();
  }

  static void printTitle() {
    System.out.println("================");
    System.out.println("🪖병력 관리 시스템🪖");
    System.out.println("================");
  }

  public void execute() {
    printTitle();

    new LoginListener(soldierDao).service(prompt);

    mainMenu.execute(prompt);
  }

  private void prepareMenu() {
    MenuGroup soldierMenu = new MenuGroup("병력관리");
    soldierMenu.add(new Menu("등록", new SoldierAddListener(soldierDao)));
    soldierMenu.add(new Menu("목록", new SoldierListListener(soldierDao)));
    soldierMenu.add(new Menu("조회", new SoldierDetailListener(soldierDao)));
    soldierMenu.add(new Menu("변경", new SoldierUpdateListener(soldierDao)));
    soldierMenu.add(new Menu("삭제", new SoldierDeleteListener(soldierDao)));
    soldierMenu.add(new Menu("DB 업데이트", new SoldierUpdater(soldierDao)));
    mainMenu.add(soldierMenu);

    MenuGroup boardMenu = new MenuGroup("게시글");
    boardMenu.add(new Menu("등록", new BoardAddListener(boardDao)));
    boardMenu.add(new Menu("목록", new BoardListListener(boardDao)));
    boardMenu.add(new Menu("조회", new BoardDetailListener(boardDao)));
    boardMenu.add(new Menu("변경", new BoardUpdateListener(boardDao)));
    boardMenu.add(new Menu("삭제", new BoardDeleteListener(boardDao)));
    mainMenu.add(boardMenu);
  }
}
