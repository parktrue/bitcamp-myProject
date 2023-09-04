package bitcamp.myapp.service;

import bitcamp.myapp.dao.SoldierDao;
import bitcamp.myapp.vo.Soldier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Service
public class DefaultSoldierService implements SoldierService {
  SoldierDao soldierDao;
  TransactionTemplate txTemplate;

  public DefaultSoldierService(SoldierDao soldierDao, PlatformTransactionManager txManager) {
    this.soldierDao = soldierDao;
    this.txTemplate = new TransactionTemplate(txManager);
  }

  @Override
  public int add(Soldier soldier) throws Exception {
    return txTemplate.execute(status -> soldierDao.insert(soldier));
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

  @Override
  public int update(Soldier soldier) throws Exception {
    return txTemplate.execute(status -> soldierDao.update(soldier));
  }

  @Override
  public int delete(int soldierNo) throws Exception {
    return txTemplate.execute(status -> soldierDao.delete(soldierNo));
  }

  @Override
  public void updateDday() throws Exception {
    soldierDao.updateDday();
  }

  @Override
  public String setMilNum(String enlistmentYear) throws Exception {
    return soldierDao.findLatestMilitaryNumberByYear(enlistmentYear);
  }
}
