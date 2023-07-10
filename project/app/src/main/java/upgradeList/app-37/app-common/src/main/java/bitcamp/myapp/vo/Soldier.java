package bitcamp.myapp.vo;

import java.io.Serializable;

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

    if (Soldier.soldierId <= soldier.getNo()) {
      Soldier.soldierId = soldier.getNo() + 1;
    }

    return soldier;
  }

  @Override
  public String toCsvString() {
    return String.format("%d,%s,%s,%d", this.getNo(), this.getName(), this.getRank(),
        this.getAge());
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
}
