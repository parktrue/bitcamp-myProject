package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Soldier;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SoldierDao {
  int insert(Soldier soldier);

  List<Soldier> findAll();

  Soldier findBy(int no);

  Soldier findByMilnumAndPassword(@Param("militaryNumber") String milNum, @Param("password") String password);

  int update(Soldier soldier);

  int delete(int no);

  void updateDday();

  String findLatestMilitaryNumberByYear(String enlistmentYear);
}
