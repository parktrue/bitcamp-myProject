package bitcamp.net;

import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class RequestEntity {
  String command;
  String data;

  @SuppressWarnings("unchecked")
  public <T> T getObject(Class<T> clazz) {
    if (clazz == String.class) {
      return (T) data;
    } else {
      return new Gson().fromJson(data, clazz);
    }
  }

  public <T> List<T> getList(Class<T> clazz) {
    return new Gson().fromJson(data, TypeToken.getParameterized(List.class, clazz).getType());
  }

  public String toJson() {
    return new Gson().toJson(this);
  }

  public static RequestEntity fromJson(String json) {
    return new Gson().fromJson(json, RequestEntity.class);
  }

  public RequestEntity command(String command) {
    this.command = command;
    return this;
  }

  public RequestEntity data(Object obj) {
    if (obj == null) {
      return this;
    }

    if (obj.getClass() == String.class) {
      this.data = (String) obj;
    } else {
      this.data = new Gson().toJson(obj);
    }
    return this;
  }

  public String getCommand() {
    return command;
  }

  public String getData() {
    return data;
  }

}
