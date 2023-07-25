package bitcamp.myapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import bitcamp.myapp.dao.BoardListDao;
import bitcamp.myapp.dao.SoldierListDao;
import bitcamp.net.RequestEntity;
import bitcamp.net.ResponseEntity;

public class ServerApp {
  int port;
  ServerSocket serverSocket;

  HashMap<String, Object> daoMap = new HashMap<>();

  public ServerApp(int port) {
    this.port = port;

    daoMap.put("soldier", new SoldierListDao("soldier.json"));
    daoMap.put("board", new BoardListDao("board.json"));
  }

  public void close() throws Exception {
    serverSocket.close();
  }

  public static void main(String[] args) throws Exception {
    if (args.length < 1) {
      System.out.println("실행 예) java... bitcamp.myapp.ServerApp 포트번호");
      return;
    }
    ServerApp app = new ServerApp(Integer.parseInt(args[0]));
    app.execute();
    app.close();
  }

  public void execute() throws Exception {

    System.out.println("[MyList 서버 애플리케이션]");

    this.serverSocket = new ServerSocket(port);
    System.out.println("서버 실행 중....");

    while (true) {
      processRequest(serverSocket.accept());
    }
  }

  public static Method findMethod(Object obj, String methodName) {
    Method[] methods = obj.getClass().getDeclaredMethods();
    for (int i = 0; i < methods.length; i++) {
      if (methods[i].getName().equals(methodName)) {
        return methods[i];
      }
    }
    return null;
  }

  public static Object call(Object obj, Method method, RequestEntity request) throws Exception {
    Parameter[] params = method.getParameters();
    if (params.length > 0) {
      return method.invoke(obj, request.getObject(params[0].getType()));
    } else {
      return method.invoke(obj);
    }
  }

  public void processRequest(Socket socket) {
    try (Socket s = socket;
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

      InetSocketAddress socketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
      System.out.printf("%s:%s 클라이언트 접속\n", socketAddress.getHostString(), socketAddress.getPort());


      RequestEntity request = RequestEntity.fromJson(in.readUTF());

      String command = request.getCommand();
      System.out.println(command);

      String[] values = command.split("/");
      String dataName = values[0];
      String methodName = values[1];

      Object dao = daoMap.get(dataName);
      if (dao == null) {
        out.writeUTF(
            new ResponseEntity().status(ResponseEntity.ERROR).result("데이터를 찾을 수 없습니다").toJson());
        return;
      }

      Method method = findMethod(dao, methodName);
      if (method == null) {
        out.writeUTF(
            new ResponseEntity().status(ResponseEntity.ERROR).result("메서드를 찾을 수 없습니다").toJson());
        return;
      }

      try {
        Object result = call(dao, method, request);

        ResponseEntity response = new ResponseEntity();
        response.status(ResponseEntity.SUCCESS);
        response.result(result);
        out.writeUTF(response.toJson());
      } catch (Exception e) {

        ResponseEntity response = new ResponseEntity();
        response.status(ResponseEntity.ERROR);
        response.result(e.getMessage());
        out.writeUTF(response.toJson());
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

  }
}
