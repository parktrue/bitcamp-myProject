package bitcamp.myapp.dao;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import bitcamp.myapp.vo.Soldier;

public class MySQLSoldierDao implements SoldierDao {

  SqlSessionFactory sqlSessionFactory;

  public MySQLSoldierDao(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void insert(Soldier soldier) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);

    // 입대일을 기반으로 전역일을 계산하고 설정
    // Set the discharge date first
    LocalDate enlistmentDate = soldier.getEnlistmentDate();
    LocalDate dischargeDate = enlistmentDate.plusMonths(18).minusDays(1); // 18개월 후로 전역일 설정 (하루를 뺀
                                                                          // 일수)
    soldier.setDischargeDate(dischargeDate);

    // Calculate D-day based on discharge date
    long dDay = ChronoUnit.DAYS.between(LocalDate.now(), dischargeDate);
    soldier.setdDay(dDay);


    // 군번을 자동으로 생성하고 설정
    // Extract the current year's last two digits
    // Use the enlistment year's last two digits for the military number
    String enlistmentYear = String.valueOf(enlistmentDate.getYear()).substring(2);

    // Get the last military number for the current year
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
    SqlSession sqlSession = sqlSessionFactory.openSession();
    return sqlSession.selectList("bitcamp.myapp.dao.SoldierDao.findAll");
  }

  @Override
  public Soldier findBy(int no) {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    return sqlSession.selectOne("bitcamp.myapp.dao.SoldierDao.findBy", no);
  }

  @Override
  public Soldier findByMilnumAndPassword(Soldier soldier) {
    SqlSession sqlSession = sqlSessionFactory.openSession();
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
