package bitcamp.myapp.service;

import bitcamp.myapp.dao.SoldierDao;
import bitcamp.myapp.vo.Soldier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

@Service
public class DefaultSoldierService implements SoldierService{
  SoldierDao soldierDao;
  PlatformTransactionManager txManager;

  public DefaultSoldierService(SoldierDao soldierDao, PlatformTransactionManager txManager) {
    this.soldierDao = soldierDao;
    this.txManager = txManager;
  }

  @Override
  public int add(Soldier soldier) throws Exception {
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = txManager.getTransaction(def);
    try {
      int count = soldierDao.insert(soldier);
      txManager.commit(status);
      return count;
    } catch (Exception e) {
      txManager.rollback(status);
      throw e;
    }
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
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = txManager.getTransaction(def);
    try {
      int count = soldierDao.update(soldier);
      txManager.commit(status);
      return count;
    } catch (Exception e) {
      txManager.rollback(status);
      throw e;
    }
  }

  @Override
  public int delete(int soldierNo) throws Exception {
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = txManager.getTransaction(def);
    try {
      int count = soldierDao.delete(soldierNo);
      txManager.commit(status);
      return count;
    } catch (Exception e) {
      txManager.rollback(status);
      throw e;
    }
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
