package bitcamp.myapp.service;

import bitcamp.myapp.vo.Soldier;

import java.util.List;

// 비즈니스 로직을 수행하는 객체의 사용 규칙 정의
// 메서드 이름은 업무와 관련된 이름을 사용할 것.
//
public interface SoldierService {
  int add(Soldier soldier) throws Exception;

  List<Soldier> list() throws Exception;

  Soldier get(int soldierNo) throws Exception;

  Soldier get(String milNum, String password) throws Exception;

  int update(Soldier soldier) throws Exception;

  int delete(int soldierNo) throws Exception;

  void updateDday() throws Exception;

  String setMilNum(String enlistmentYear) throws Exception;
}
