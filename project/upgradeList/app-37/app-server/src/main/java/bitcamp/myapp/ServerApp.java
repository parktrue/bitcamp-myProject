package bitcamp.myapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.BoardListDao;
import bitcamp.myapp.dao.SoldierDao;
import bitcamp.myapp.dao.SoldierListDao;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Soldier;
import bitcamp.net.RequestEntity;
import bitcamp.net.ResponseEntity;

public class ServerApp {
  int port;
  ServerSocket serverSocket;

  SoldierDao soldierDao = new SoldierListDao("soldier.json");
  BoardDao boardDao = new BoardListDao("board.json");

  public ServerApp(int port) {
    this.port = port;
  }

  public void close() throws Exception {
    serverSocket.close();
  }

  public static void main(String[] args) throws Exception {
    if (args.length < 1) {
      System.out.println("실행 예) java... bitcamp.myapp.ServerApp 포트번호");
    }
    ServerApp app = new ServerApp(Integer.parseInt(args[0]));
    app.execute();
    app.close();
  }

  public void execute() throws Exception {
    System.out.println("[MyList 서버 애플리케이션]");
    this.serverSocket = new ServerSocket(port);
    System.out.println("서버 실행 중....");

    Socket socket = serverSocket.accept();
    DataInputStream in = new DataInputStream(socket.getInputStream());
    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
    while (true) {
      RequestEntity request = RequestEntity.fromJson(in.readUTF());

      String command = request.getCommand();
      System.out.println(command);

      if (command.equals("quit")) {
        break;
      }

      ResponseEntity response = new ResponseEntity();

      switch (command) {
        case "board/list":
          response.status(ResponseEntity.SUCCESS).result(boardDao.list());
          break;
        case "board/insert":
          boardDao.insert(request.getObject(Board.class));
          response.status(ResponseEntity.SUCCESS);
          break;
        case "board/findBy":
          Board board = boardDao.findBy(request.getObject(Integer.class));
          if (board == null) {
            response.status(ResponseEntity.ERROR).result("해당 번호의 게시글이 없습니다!");
          } else {
            response.status(ResponseEntity.SUCCESS).result(board);
          }
          break;
        case "board/update":
          int value = boardDao.update(request.getObject(Board.class));
          response.status(ResponseEntity.SUCCESS).result(value);
          break;
        case "board/delete":
          value = boardDao.delete(request.getObject(Integer.class));
          response.status(ResponseEntity.SUCCESS).result(value);
          break;
        case "soldier/list":
          response.status(ResponseEntity.SUCCESS).result(soldierDao.list());
          break;
        case "soldier/insert":
          soldierDao.insert(request.getObject(Soldier.class));
          response.status(ResponseEntity.SUCCESS);
          break;
        case "soldier/findBy":
          Soldier soldier = soldierDao.findBy(request.getObject(Integer.class));
          if (soldier == null) {
            response.status(ResponseEntity.ERROR).result("해당 번호의 병사가 없습니다!");
          } else {
            response.status(ResponseEntity.SUCCESS).result(soldier);
          }
          break;
        case "soldier/update":
          value = soldierDao.update(request.getObject(Soldier.class));
          response.status(ResponseEntity.SUCCESS).result(value);
          break;
        case "soldier/delete":
          value = soldierDao.delete(request.getObject(Integer.class));
          response.status(ResponseEntity.SUCCESS).result(value);
          break;
        default:
          response.status(ResponseEntity.ERROR).result("해당 명령을 지원하지 않습니다!");
      }

      out.writeUTF(response.toJson());
    }

  }


}
