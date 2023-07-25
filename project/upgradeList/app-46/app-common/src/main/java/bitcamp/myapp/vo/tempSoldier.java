package bitcamp.myapp.vo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class tempSoldier implements Serializable {
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

  public tempSoldier() {}

  public tempSoldier(int no) {
    this.no = no;
  }

  public static tempSoldier fromCsv(String csv) {
    String[] values = csv.split(",");

    tempSoldier soldier = new tempSoldier(Integer.parseInt(values[0]));
    soldier.setName(values[1]);
    soldier.setRank(values[2]);
    soldier.setAge(Integer.parseInt(values[3]));
    soldier.setEnlistmentDate(LocalDate.parse(values[4]));

    if (tempSoldier.soldierId <= soldier.getNo()) {
      tempSoldier.soldierId = soldier.getNo() + 1;
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
    if (tempSoldier.soldierId <= this.no) {
      tempSoldier.soldierId = this.no + 1;
    }
  }

  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    tempSoldier s = (tempSoldier) obj;
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
    return this.dischargeDate;
  }

  public long getDDay() {
    if (enlistmentDate != null) {
      calculateDischargeDateAndDday(); // 조회할 때마다 D-day를 다시 계산
    }
    return dDay;
  }

  private void calculateDischargeDateAndDday() {
    if (enlistmentDate != null) {
      dischargeDate = enlistmentDate.plusMonths(18).minusDays(1); // 18개월 후 하루 전을 전역일로
      dDay = ChronoUnit.DAYS.between(LocalDate.now(), dischargeDate); // D-day 계산
    } else {
      dischargeDate = null;
      dDay = 0;
    }
  }
}
