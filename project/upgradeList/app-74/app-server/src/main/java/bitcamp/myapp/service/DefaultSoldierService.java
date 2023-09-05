package bitcamp.myapp.service;

import bitcamp.myapp.dao.SoldierDao;
import bitcamp.myapp.vo.Soldier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefaultSoldierService implements SoldierService {
  SoldierDao soldierDao;

  public DefaultSoldierService(SoldierDao soldierDao) {
    this.soldierDao = soldierDao;
  }

  @Transactional
  @Override
  public int add(Soldier soldier) throws Exception {
    return soldierDao.insert(soldier);
  }

  @Override
  public List<Soldier> list() throws Exception {
    soldierDao.updateDday();
    return soldierDao.findAll();
  }

  @Override
  public Soldier get(int soldierNo) throws Exception {
    return soldierDao.findBy(soldierNo);
  }

  @Override
  public Soldier get(String milNum, String password) throws Exception {
    return soldierDao.findByMilnumAndPassword(milNum, password);
  }

  @Transactional
  @Override
  public int update(Soldier soldier) throws Exception {
    return soldierDao.update(soldier);
  }

  @Transactional
  @Override
  public int delete(int soldierNo) throws Exception {
    return soldierDao.delete(soldierNo);
  }

  @Transactional
  @Override
  public void updateDday() throws Exception {
    soldierDao.updateDday();
  }

  @Transactional
  @Override
  public String setMilNum(String enlistmentYear) throws Exception {
    return soldierDao.findLatestMilitaryNumberByYear(enlistmentYear);
  }
}
