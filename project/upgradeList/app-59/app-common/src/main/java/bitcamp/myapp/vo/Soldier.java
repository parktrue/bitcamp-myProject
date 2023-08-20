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
  private String militaryNumber; // 군번

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

  public String getMilitaryNumber() {
    return militaryNumber;
  }

  public void setMilitaryNumber(String militaryNumber) {
    this.militaryNumber = militaryNumber;
  }

  public String calculateDday() {
    try {
      LocalDate today = LocalDate.now();
      LocalDate dischargeDate = this.getDischargeDate();
      long dday = dischargeDate.toEpochDay() - today.toEpochDay();

      if (dday == 0) {
        return "전역일 D-day";
      } else if (dday < 0) {
        return "전역일 D+" + Math.abs(dday);
      } else {
        return "전역일 D-" + dday;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return "날짜 오류";
    }
  }

  // 새로운 메서드 추가: rank 값을 문자열로 변환해주는 메서드
  public String getRankString() {
    switch (rank) {
      case "1":
        return "이병";
      case "2":
        return "일병";
      case "3":
        return "상병";
      case "4":
        return "병장";
      default:
        return "등급없음";
    }
  }
}
