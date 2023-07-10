package bitcamp.myapp.dao;

import java.util.ArrayList;
import java.util.List;
import bitcamp.myapp.vo.Soldier;
import bitcamp.util.JsonDataHelper;

public class SoldierListDao implements SoldierDao {

  String filename;
  ArrayList<Soldier> list = new ArrayList<>();

  public SoldierListDao(String filename) {
    this.filename = filename;
    JsonDataHelper.loadJson(filename, list, Soldier.class);
  }

  @Override
  public void insert(Soldier soldier) {
    soldier.setNo(Soldier.soldierId++);
    this.list.add(soldier);
    JsonDataHelper.saveJson(filename, list);
  }

  @Override
  public List<Soldier> list() {
    return this.list;
  }

  @Override
  public Soldier findBy(int no) {
    for (int i = 0; i < this.list.size(); i++) {
      Soldier s = this.list.get(i);
      if (s.getNo() == no) {
        return s;
      }
    }
    return null;
  }

  @Override
  public int update(Soldier soldier) {
    for (int i = 0; i < this.list.size(); i++) {
      if (list.get(i).getNo() == soldier.getNo()) {
        list.set(i, soldier);
        JsonDataHelper.saveJson(filename, list);
        return 1;
      }
    }
    return 0;
  }

  @Override
  public int delete(int no) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getNo() == no) {
        list.remove(i);
        JsonDataHelper.saveJson(filename, list);
        return 1;
      }
    }
    return 0;
  }
}
