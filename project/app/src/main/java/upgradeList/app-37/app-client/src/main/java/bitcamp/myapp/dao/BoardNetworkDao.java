package bitcamp.myapp.dao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import bitcamp.myapp.vo.Board;
import bitcamp.net.RequestEntity;
import bitcamp.net.ResponseEntity;

public class BoardNetworkDao implements BoardDao {

  String dataName;
  DataInputStream in;
  DataOutputStream out;

  public BoardNetworkDao(String dataName, DataInputStream in, DataOutputStream out) {
    this.dataName = dataName;
    this.in = in;
    this.out = out;
  }

  @Override
  public void insert(Board board) {
    try {
      // 서버에 요청을 보낸다.
      out.writeUTF(new RequestEntity().command(dataName + "/insert").data(board).toJson());

      // 서버에서 보낸 응답을 받는다.
      ResponseEntity response = ResponseEntity.fromJson(in.readUTF());
      if (response.getStatus().equals(ResponseEntity.ERROR)) {
        throw new RuntimeException(response.getResult());
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Board> list() {
    try {
      out.writeUTF(new RequestEntity().command(dataName + "/list").toJson());

      ResponseEntity response = ResponseEntity.fromJson(in.readUTF());
      if (response.getStatus().equals(ResponseEntity.ERROR)) {
        throw new RuntimeException(response.getResult());
      }

      return response.getList(Board.class);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Board findBy(int no) {
    try {
      out.writeUTF(new RequestEntity().command(dataName + "/findBy").data(no).toJson());

      ResponseEntity response = ResponseEntity.fromJson(in.readUTF());

      if (response.getStatus().equals(ResponseEntity.ERROR)) {
        throw new RuntimeException(response.getResult());
      } else if (response.getStatus().equals(ResponseEntity.ERROR)) {
        return null;
      }

      return response.getObject(Board.class);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int update(Board board) {
    try {
      // 서버에 요청을 보낸다.
      out.writeUTF(new RequestEntity().command(dataName + "/update").data(board).toJson());

      // 서버에서 보낸 응답을 받는다.
      ResponseEntity response = ResponseEntity.fromJson(in.readUTF());

      if (response.getStatus().equals(ResponseEntity.ERROR)) {
        throw new RuntimeException(response.getResult());
      } else if (response.getStatus().equals(ResponseEntity.ERROR)) {
        return 0;
      }

      return response.getObject(Integer.class);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int delete(int no) {
    try {
      out.writeUTF(new RequestEntity().command(dataName + "/delete").data(no).toJson());

      ResponseEntity response = ResponseEntity.fromJson(in.readUTF());

      if (response.getStatus().equals(ResponseEntity.ERROR)) {
        throw new RuntimeException(response.getResult());
      } else if (response.getStatus().equals(ResponseEntity.ERROR)) {
        return 0;
      }

      return response.getObject(Integer.class);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


}
