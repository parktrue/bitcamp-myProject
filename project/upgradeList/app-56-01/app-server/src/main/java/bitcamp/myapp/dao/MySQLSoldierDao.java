package bitcamp.myapp.dao;

import java.time.LocalDate;
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
    LocalDate enlistmentDate = soldier.getEnlistmentDate();
    LocalDate dischargeDate = enlistmentDate.plusMonths(18).minusDays(1); // 18개월 후로 전역일 설정 (하루를 뺀
                                                                          // 일수)
    soldier.setDischargeDate(dischargeDate);

    // 군번을 자동으로 생성하고 설정
    soldier.setMilitaryNumber(String.valueOf(System.currentTimeMillis()));

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
