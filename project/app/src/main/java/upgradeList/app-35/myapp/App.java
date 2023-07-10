package bitcamp.myapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
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
import bitcamp.myapp.vo.AutoIncrement;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Soldier;
import bitcamp.util.BreadcrumbPrompt;
import bitcamp.util.Menu;
import bitcamp.util.MenuGroup;

public class App {

  ArrayList<Soldier> soldierList = new ArrayList<>();
  LinkedList<Board> boardList = new LinkedList<>();

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

    loadData();
    mainMenu.execute(prompt);
    saveData();

    prompt.close();
  }

  private void loadData() {
    loadJson("soldier.json", soldierList, Soldier.class);
    loadJson("board.json", boardList, Board.class);
  }

  private void saveData() {
    saveJson("soldier.json", soldierList);
    saveJson("board.json", boardList);
  }

  private void prepareMenu() {
    MenuGroup soldierMenu = new MenuGroup("병력관리");
    soldierMenu.add(new Menu("등록", new SoldierAddListener(soldierList)));
    soldierMenu.add(new Menu("목록", new SoldierListListener(soldierList)));
    soldierMenu.add(new Menu("조회", new SoldierDetailListener(soldierList)));
    soldierMenu.add(new Menu("변경", new SoldierUpdateListener(soldierList)));
    soldierMenu.add(new Menu("삭제", new SoldierDeleteListener(soldierList)));
    mainMenu.add(soldierMenu);

    MenuGroup boardMenu = new MenuGroup("게시글");
    boardMenu.add(new Menu("등록", new BoardAddListener(boardList)));
    boardMenu.add(new Menu("목록", new BoardListListener(boardList)));
    boardMenu.add(new Menu("조회", new BoardDetailListener(boardList)));
    boardMenu.add(new Menu("변경", new BoardUpdateListener(boardList)));
    boardMenu.add(new Menu("삭제", new BoardDeleteListener(boardList)));
    mainMenu.add(boardMenu);
  }

  private <T> void loadJson(String filename, List<T> list, Class<T> clazz) {
    try {
      FileReader in0 = new FileReader(filename);
      BufferedReader in = new BufferedReader(in0);

      StringBuilder strBuilder = new StringBuilder();
      String line = null;

      while ((line = in.readLine()) != null) {
        strBuilder.append(line);
      }
      in.close();

      Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
      Collection<T> objects = gson.fromJson(strBuilder.toString(),
          TypeToken.getParameterized(Collection.class, clazz).getType());

      list.addAll(objects);

      Class<?>[] interfaces = clazz.getInterfaces();
      for (Class<?> info : interfaces) {
        if (info == AutoIncrement.class) {
          AutoIncrement autoIncrement = (AutoIncrement) list.get(list.size() - 1);
          autoIncrement.updateKey();
          break;
        }
      }
    } catch (Exception e) {
      System.out.println(filename + " 파일을 읽는 중 오류 발생!");
    }
  }

  private void saveJson(String filename, List<?> list) {
    try {
      FileWriter out0 = new FileWriter(filename);
      BufferedWriter out = new BufferedWriter(out0); // <== Decorator(장식품) 역할 수행!

      Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().create();
      out.write(gson.toJson(list));
      out.close();

    } catch (Exception e) {
      System.out.println(filename + " 파일을 저장하는 중 오류 발생!");
    }
  }
}
