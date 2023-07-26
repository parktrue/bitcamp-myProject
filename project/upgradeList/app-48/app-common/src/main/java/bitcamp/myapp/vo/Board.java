package bitcamp.myapp.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class Board implements Serializable {
  private static final long serialVersionUID = 1L;

  private int no;
  private String title;
  private String content;
  private Soldier writer;
  private String password;
  private int viewCount;
  private Timestamp createdDate;
  private int category;

  @Override
  public int hashCode() {
    return Objects.hash(no);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Board other = (Board) obj;
    return no == other.no;
  }

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Soldier getWriter() {
    return writer;
  }

  public void setWriter(Soldier writer) {
    this.writer = writer;
  }

  public int getViewCount() {
    return viewCount;
  }

  public void setViewCount(int viewCount) {
    this.viewCount = viewCount;
  }

  public Timestamp getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getCategory() {
    return category;
  }

  public void setCategory(int category) {
    this.category = category;
  }
}
