package bitcamp.myapp;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.BoardListDao;
import bitcamp.myapp.dao.SoldierDao;
import bitcamp.myapp.dao.SoldierListDao;
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
import bitcamp.util.BreadcrumbPrompt;
import bitcamp.util.Menu;
import bitcamp.util.MenuGroup;

public class App {

  SoldierDao soldierDao = new SoldierListDao("soldier.json");
  BoardDao boardDao = new BoardListDao("board.json");

  BreadcrumbPrompt prompt = new BreadcrumbPrompt();

  MenuGroup mainMenu = new MenuGroup("메인");

  public App() {
    prepareMenu();
  }

  public static void main(String[] args) {
    new App().execute();
  }

  static void printTitle() {
    System.out.println("병력 관리 시스템");
    System.out.println("-------------------------------\n");
  }

  public void execute() {
    printTitle();

    mainMenu.execute(prompt);

    prompt.close();
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
