package bitcamp.myapp.vo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Soldier implements Serializable, CsvObject, AutoIncrement {
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
  private LocalDate enlistmentDate;
  private LocalDate dischargeDate; // 전역일
  private long dDay; // D-day

  public Soldier() {}

  public Soldier(int no) {
    this.no = no;
  }

  public static Soldier fromCsv(String csv) {
    String[] values = csv.split(",");

    Soldier soldier = new Soldier(Integer.parseInt(values[0]));
    soldier.setName(values[1]);
    soldier.setRank(values[2]);
    soldier.setAge(Integer.parseInt(values[3]));
    soldier.setEnlistmentDate(LocalDate.parse(values[4]));
    soldier.setDischargeDate(LocalDate.parse(values[5]));

    if (Soldier.soldierId <= soldier.getNo()) {
      Soldier.soldierId = soldier.getNo() + 1;
    }

    return soldier;
  }

  @Override
  public String toCsvString() {
    return String.format("%d,%s,%s,%d,%s,%s", this.getNo(), this.getName(), this.getRank(),
        this.getAge(), this.getEnlistmentDate().toString(), this.getDischargeDate().toString());
  }

  @Override
  public void updateKey() {
    if (Soldier.soldierId <= this.no) {
      Soldier.soldierId = this.no + 1;
    }
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

  public LocalDate getEnlistmentDate() {
    return enlistmentDate;
  }

  public void setEnlistmentDate(LocalDate enlistmentDate) {
    this.enlistmentDate = enlistmentDate;
    calculateDischargeDateAndDday();
  }

  public LocalDate getDischargeDate() {
    return dischargeDate;
  }

  public void setDischargeDate(LocalDate dischargeDate) {
    this.dischargeDate = dischargeDate;
  }

  public long getDDay() {
    return dDay;
  }

  public void setDDay(long dDay) {
    this.dDay = dDay;
  }

  private void calculateDischargeDateAndDday() {
    if (enlistmentDate != null) {
      dischargeDate = enlistmentDate.plusMonths(18); // 18개월 후 전역일
      dDay = ChronoUnit.DAYS.between(LocalDate.now(), dischargeDate); // D-day 계산
    } else {
      dischargeDate = null;
      dDay = 0;
    }
  }
}

