package bitcamp.myapp.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import bitcamp.myapp.vo.Soldier;
import bitcamp.util.JsonDataHelper;

public class SoldierListDao implements SoldierDao {

  String filename;
  ArrayList<Soldier> list = new ArrayList<>();

  public SoldierListDao(String filename) {
    this.filename = filename;
    JsonDataHelper.loadJson(filename, list, Soldier.class);
  }

  public List<Soldier> getInductee(LocalDate date) {
    List<Soldier> resultList =
        list.stream().filter(s -> s.getEnlistmentDate().equals(date)).collect(Collectors.toList());

    JsonDataHelper.saveJson("./inductees.json", resultList);

    return resultList;
  }

  @Override
  public void insert(Soldier soldier) {
    soldier.setNo(Soldier.soldierId++);
    this.list.add(soldier);
    try {
      JsonDataHelper.saveJson(filename, list);
    } catch (Exception e) {
      System.err.println(filename + " 파일을 저장하는 중 오류 발생:");
      e.printStackTrace();
      throw new RuntimeException(filename + " 파일을 저장하는 중 오류 발생: " + e.getMessage(), e);
    }
  }

  @Override
  public List<Soldier> list() {
    return list;
  }

  @Override
  public Soldier findBy(int no) {
    for (int i = 0; i < list.size(); i++) {
      Soldier s = list.get(i);
      if (s.getNo() == no) {
        return s;
      }
    }
    return null;
  }

  @Override
  public int update(Soldier soldier) {
    for (int i = 0; i < list.size(); i++) {
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
