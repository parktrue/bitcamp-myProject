package bitcamp.myapp.vo;

import java.io.Serializable;
import java.time.LocalDate;

public class Soldier implements Serializable {
  private static final long serialVersionUID = 1L;

  public static final int PRIVATE_FIRST_CLASS = 1;
  public static final int PRIVATE = 2;
  public static final int CORPORAL = 3;
  public static final int SERGEANT = 4;

  public static int soldierId = 1;

  private int no;
  private String name;
  private String rank;
  private int age;
  private String password;
  private LocalDate enlistmentDate;
  private LocalDate dischargeDate; // 전역일
  private long dDay; // D-day

  public Soldier() {}

  public Soldier(int no) {
    this.no = no;
  }

  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    Soldier s = (Soldier) obj;
    if (this.getNo() != s.getNo()) {
      return false;
    }
    return true;
  }

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRank() {
    return rank;
  }

  public void setRank(String rank) {
    this.rank = rank;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public LocalDate getEnlistmentDate() {
    return enlistmentDate;
  }

  public void setEnlistmentDate(LocalDate enlistmentDate) {
    this.enlistmentDate = enlistmentDate;
  }

  public LocalDate getDischargeDate() {
    return dischargeDate;
  }

  public void setDischargeDate(LocalDate dischargeDate) {
    this.dischargeDate = dischargeDate;
  }

  public long getdDay() {
    return dDay;
  }

  public void setdDay(long dDay) {
    this.dDay = dDay;
  }
}

