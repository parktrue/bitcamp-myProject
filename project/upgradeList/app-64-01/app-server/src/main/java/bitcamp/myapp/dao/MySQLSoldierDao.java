package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Soldier;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class MySQLSoldierDao implements SoldierDao {

  SqlSessionFactory sqlSessionFactory;

  public MySQLSoldierDao(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void insert(Soldier soldier) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);

    LocalDate enlistmentDate = soldier.getEnlistmentDate();
    LocalDate dischargeDate = enlistmentDate.plusMonths(18).minusDays(1);
    soldier.setDischargeDate(dischargeDate);

    long dDay = ChronoUnit.DAYS.between(LocalDate.now(), dischargeDate);
    soldier.setdDay(dDay);

    String enlistmentYear = String.valueOf(enlistmentDate.getYear()).substring(2);

    String lastMilitaryNumber = sqlSession
        .selectOne("bitcamp.myapp.dao.SoldierDao.findLatestMilitaryNumberByYear", enlistmentYear);

    String newMilitaryNumber;
    if (lastMilitaryNumber == null) {
      newMilitaryNumber = enlistmentYear + "-72000001";
    } else {
      int lastSequence = Integer.parseInt(lastMilitaryNumber.split("-")[1].substring(4));
      newMilitaryNumber = enlistmentYear + "-7200" + String.format("%04d", lastSequence + 1);
    }
    soldier.setMilitaryNumber(newMilitaryNumber);

    sqlSession.insert("bitcamp.myapp.dao.SoldierDao.insert", soldier);
    sqlSession.commit();
  }

  @Override
  public List<Soldier> findAll() {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.selectList("bitcamp.myapp.dao.SoldierDao.findAll");
  }

  @Override
  public Soldier findBy(int no) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.selectOne("bitcamp.myapp.dao.SoldierDao.findBy", no);
  }

  @Override
  public Soldier findByMilnumAndPassword(Soldier soldier) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.selectOne("bitcamp.myapp.dao.SoldierDao.findByMilnumAndPassword", soldier);
  }

  @Override
  public int update(Soldier soldier) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.update("bitcamp.myapp.dao.SoldierDao.update", soldier);
  }

  @Override
  public int delete(int no) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.delete("bitcamp.myapp.dao.SoldierDao.delete", no);
  }

  @Override
  public void updateDday() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
      sqlSession.update("bitcamp.myapp.dao.SoldierDao.updateDday");
    }
  }
}
