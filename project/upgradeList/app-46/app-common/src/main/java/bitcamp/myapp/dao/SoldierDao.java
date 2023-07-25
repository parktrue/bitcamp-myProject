package bitcamp.myapp.dao;

import java.util.List;
import java.util.function.Consumer;
import bitcamp.myapp.vo.Soldier;

public interface SoldierDao {
  void insert(Soldier soldier);

  List<Soldier> list();

  Soldier findBy(int no);

  int update(Soldier soldier);

  int delete(int no);

  void updateAll(Consumer<Soldier> updater);
}
