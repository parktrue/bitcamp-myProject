// ResponseEntity.java
package bitcamp.net;

import java.time.LocalDate;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ResponseEntity {

  public static final String SUCCESS = "success";
  public static final String ERROR = "ERROR";

  String status;
  String result;

  // 새로운 Gson 객체 생성
  private static final Gson gson =
      new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

  @SuppressWarnings("unchecked")
  public <T> T getObject(Class<T> clazz) {
    if (clazz == String.class) {
      return (T) result;
    } else {
      return gson.fromJson(result, clazz);
    }
  }

  public <T> List<T> getList(Class<T> clazz) {
    return gson.fromJson(result, TypeToken.getParameterized(List.class, clazz).getType());
  }

  public String toJson() {
    return gson.toJson(this);
  }

  public static ResponseEntity fromJson(String json) {
    return gson.fromJson(json, ResponseEntity.class);
  }

  public ResponseEntity status(String status) {
    this.status = status;
    return this;
  }

  public ResponseEntity result(Object obj) {
    if (obj == null) {
      return this;
    }

    if (obj.getClass() == String.class) {
      this.result = (String) obj;
    } else {
      this.result = gson.toJson(obj);
    }
    return this;
  }

  public String getStatus() {
    return status;
  }

  public String getResult() {
    return result;
  }
}
