package bitcamp.myapp.dao;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Consumer;
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
    sqlSession.insert("bitcamp.myapp.dao.SoldierDao.insert", soldier);
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
  public void updateAll(Consumer<Soldier> updater) {
    List<Soldier> soldiers = findAll();

    for (Soldier soldier : soldiers) {
      updater.accept(soldier);

      // 디데이 업데이트
      if (soldier.getDischargeDate() != null) {
        long dDay = ChronoUnit.DAYS.between(LocalDate.now(), soldier.getDischargeDate());
        soldier.setdDay(dDay);
      }

      update(soldier);
    }
  }

}
