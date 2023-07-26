package bitcamp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import bitcamp.myapp.dao.SoldierDao;
import bitcamp.myapp.vo.Soldier;

public class MySQLSoldierDao implements SoldierDao {

  Connection con;

  public MySQLSoldierDao(Connection con) {
    this.con = con;
  }

  @Override
  public void insert(Soldier soldier) {
    try (PreparedStatement stmt = con.prepareStatement("insert into myapp_soldier"
        + " (name, age, password, `rank`, enlis_date, dis_date, d_day, soldier_milnum)"
        + " values(?,?,sha1(?),?,?,?,?,?)")) {

      stmt.setString(1, soldier.getName());
      stmt.setInt(2, soldier.getAge());
      stmt.setString(3, soldier.getPassword());
      stmt.setString(4, soldier.getRank());
      stmt.setString(5, soldier.getEnlistmentDate().toString());
      stmt.setString(6, soldier.getDischargeDate().toString());
      stmt.setLong(7, soldier.getdDay());
      stmt.setString(8, soldier.getMilitaryNumber());

      stmt.executeUpdate();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


  @Override
  public List<Soldier> list() {
    try (PreparedStatement stmt =
        con.prepareStatement("select soldier_no, soldier_milnum, name, age, `rank`,"
            + " enlis_date, dis_date, d_day from myapp_soldier" + " order by soldier_no asc")) {

      try (ResultSet rs = stmt.executeQuery()) {

        List<Soldier> list = new ArrayList<>();

        while (rs.next()) {
          Soldier s = new Soldier();
          s.setNo(rs.getInt("soldier_no"));
          s.setMilitaryNumber(rs.getString("soldier_milnum")); // handle military number
          s.setName(rs.getString("name"));
          s.setAge(rs.getInt("age"));
          s.setRank(rs.getString("rank"));
          s.setEnlistmentDate(rs.getDate("enlis_date").toLocalDate());

          java.sql.Date dischargeDate = rs.getDate("dis_date");
          if (dischargeDate != null) {
            s.setDischargeDate(dischargeDate.toLocalDate());
          }

          s.setdDay(rs.getLong("d_day"));

          list.add(s);
        }

        return list;
      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Soldier findBy(int no) {
    try (PreparedStatement stmt =
        con.prepareStatement("select soldier_no, soldier_milnum, name, age, `rank`,"
            + " enlis_date, dis_date, d_day" + " from myapp_soldier where soldier_no=?")) {

      stmt.setInt(1, no);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          Soldier s = new Soldier();
          s.setNo(rs.getInt("soldier_no"));
          s.setMilitaryNumber(rs.getString("soldier_milnum")); // handle military number
          s.setName(rs.getString("name"));
          s.setAge(rs.getInt("age"));
          s.setRank(rs.getString("rank"));
          s.setEnlistmentDate(rs.getDate("enlis_date").toLocalDate());

          java.sql.Date dischargeDate = rs.getDate("dis_date");
          if (dischargeDate != null) {
            s.setDischargeDate(dischargeDate.toLocalDate());
          }

          s.setdDay(rs.getLong("d_day"));
          return s;
        }
        return null;
      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int update(Soldier soldier) {
    try (Statement stmt = con.createStatement()) {
      return stmt.executeUpdate(String.format(
          "UPDATE myapp_soldier SET name = '%s', age = '%d', `rank` = '%s', enlis_date = '%s', dis_date = '%s', d_day = '%d', soldier_milnum = '%s' WHERE soldier_no = %d",
          soldier.getName(), soldier.getAge(), soldier.getRank(),
          soldier.getEnlistmentDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
          soldier.getDischargeDate() != null
              ? soldier.getDischargeDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
              : null,
          soldier.getdDay(), soldier.getMilitaryNumber(), soldier.getNo()));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int delete(int no) {
    try (PreparedStatement stmt =
        con.prepareStatement("delete from myapp_soldier where soldier_no=?")) {

      stmt.setInt(1, no);

      return stmt.executeUpdate();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void updateAll(Consumer<Soldier> updater) {
    List<Soldier> soldiers = list();

    for (Soldier soldier : soldiers) {
      updater.accept(soldier);

      // 디데이 업데이트
      if (soldier.getDischargeDate() != null) {
        long dDay = ChronoUnit.DAYS.between(LocalDate.now(), soldier.getDischargeDate());
        soldier.setdDay(dDay);
      }

      update(soldier);
    }
  }

}
