package bitcamp.myapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.BoardNetworkDao;
import bitcamp.myapp.dao.SoldierDao;
import bitcamp.myapp.dao.SoldierNetworkDao;
import bitcamp.myapp.handler.BoardAddListener;
import bitcamp.myapp.handler.BoardDeleteListener;
import bitcamp.myapp.handler.BoardDetailListener;
import bitcamp.myapp.handler.BoardListListener;
import bitcamp.myapp.handler.BoardUpdateListener;
import bitcamp.myapp.handler.SoldierAddListener;
import bitcamp.myapp.handler.SoldierDeleteListener;
import bitcamp.myapp.handler.SoldierDetailListener;
import bitcamp.myapp.handler.SoldierListListener;
import bitcamp.myapp.handler.SoldierUpdateListener;
import bitcamp.net.RequestEntity;
import bitcamp.util.BreadcrumbPrompt;
import bitcamp.util.Menu;
import bitcamp.util.MenuGroup;

public class ClientApp {

  Socket socket;
  DataOutputStream out;
  DataInputStream in;

  SoldierDao soldierDao;
  BoardDao boardDao;
  BoardDao readingDao;

  BreadcrumbPrompt prompt = new BreadcrumbPrompt();

  MenuGroup mainMenu = new MenuGroup("메인");

  public ClientApp(String ip, int port) throws Exception {

    this.socket = new Socket(ip, port);
    this.out = new DataOutputStream(socket.getOutputStream());
    this.in = new DataInputStream(socket.getInputStream());

    this.soldierDao = new SoldierNetworkDao("soldier", in, out);
    this.boardDao = new BoardNetworkDao("board", in, out);
    this.readingDao = new BoardNetworkDao("reading", in, out);

    prepareMenu();
  }

  public void close() throws Exception {
    prompt.close();
    out.close();
    in.close();
    socket.close();
  }

  public static void main(String[] args) throws Exception {
    if (args.length < 2) {
      System.out.println("실행 예) java... bitcamp.myapp.ClientApp 서버주소 포트번");
      return;
    }
    ClientApp app = new ClientApp(args[0], Integer.parseInt(args[1]));
    app.execute();
    app.close();
  }

  static void printTitle() {
    System.out.println("병력 관리 시스템");
    System.out.println("-------------------------------\n");
  }

  public void execute() {
    printTitle();
    mainMenu.execute(prompt);
    try {
      out.writeUTF(new RequestEntity().command("quit").toJson());
    } catch (Exception e) {
      System.out.println("종료 오류!");
      e.printStackTrace();
    }

  }

  private void prepareMenu() {
    MenuGroup soldierMenu = new MenuGroup("병력관리");
    soldierMenu.add(new Menu("등록", new SoldierAddListener(soldierDao)));
    soldierMenu.add(new Menu("목록", new SoldierListListener(soldierDao)));
    soldierMenu.add(new Menu("조회", new SoldierDetailListener(soldierDao)));
    soldierMenu.add(new Menu("변경", new SoldierUpdateListener(soldierDao)));
    soldierMenu.add(new Menu("삭제", new SoldierDeleteListener(soldierDao)));
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
