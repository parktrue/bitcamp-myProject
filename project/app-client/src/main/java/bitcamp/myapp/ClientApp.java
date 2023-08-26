package bitcamp.myapp;

import bitcamp.net.NetProtocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp {

  String ip;
  int port;

  public ClientApp(String ip, int port) throws Exception {
    this.ip = ip;
    this.port = port;
  }

  public static void main(String[] args) throws Exception {
    if (args.length < 2) {
      System.out.println("실행 예) java ... bitcamp.myapp.ClientApp 서버주소 포트번호");
      return;
    }

    ClientApp app = new ClientApp(args[0], Integer.parseInt(args[1]));
    app.execute();
  }

  public void execute() {
    try (Scanner keyscan = new Scanner(System.in);
        Socket socket = new Socket(this.ip, this.port);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream())) {

      System.out.println(in.readUTF());

      while (true) {
        String response = in.readUTF();
        if (response.equals(NetProtocol.RESPONSE_END)) {
          continue;
        } else if (response.equals(NetProtocol.PROMPT)) {
          out.writeUTF(keyscan.nextLine());
          continue;
        } else if (response.equals(NetProtocol.NET_END)) {
          break;
        }
        System.out.print(response);
      }


    } catch (Exception e) {
      System.out.println("서버 통신 오류!");
      e.printStackTrace();
    }
  }
}
