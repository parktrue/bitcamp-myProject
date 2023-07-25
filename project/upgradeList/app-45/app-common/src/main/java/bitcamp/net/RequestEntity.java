package bitcamp.net;

import java.time.LocalDate;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class RequestEntity {
  String command;
  String data;

  // 새로운 Gson 객체 생성
  private static final Gson gson =
      new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

  @SuppressWarnings("unchecked")
  public <T> T getObject(Class<T> clazz) {
    if (clazz == String.class) {
      return (T) data;
    } else {
      return gson.fromJson(data, clazz);
    }
  }

  public <T> List<T> getList(Class<T> clazz) {
    return gson.fromJson(data, TypeToken.getParameterized(List.class, clazz).getType());
  }

  public String toJson() {
    return gson.toJson(this);
  }

  public static RequestEntity fromJson(String json) {
    return gson.fromJson(json, RequestEntity.class);
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
      this.data = gson.toJson(obj);
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
