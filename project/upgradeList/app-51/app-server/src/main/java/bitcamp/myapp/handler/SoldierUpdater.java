package bitcamp.myapp.handler;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import bitcamp.myapp.dao.SoldierDao;
import bitcamp.myapp.vo.Soldier;
import bitcamp.util.ActionListener;
import bitcamp.util.BreadcrumbPrompt;
import bitcamp.util.DataSource;

public class SoldierUpdater implements ActionListener {
  SoldierDao soldierDao;
  DataSource ds;

  public SoldierUpdater(SoldierDao soldierDao, DataSource ds) {
    this.soldierDao = soldierDao;
    this.ds = ds;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    try {
      soldierDao.updateAll(soldier -> updateDday(soldier));
      ds.getConnection().commit();
      prompt.println("전체 병력 정보를 업데이트 했습니다.");
    } catch (Exception e) {
      try {
        ds.getConnection().rollback();
      } catch (Exception e2) {
      }
      throw new RuntimeException(e);
    }
  }

  private void updateDday(Soldier soldier) {
    LocalDate enlistmentDate = soldier.getEnlistmentDate();
    LocalDate dischargeDate = soldier.getDischargeDate();

    // Check if the dates are null or invalid
    if (enlistmentDate == null) {
      throw new RuntimeException("Enlistment date is null");
    }

    if (dischargeDate == null) {
      // 전역일이 null인 경우 D-Day를 null 또는 다른 적절한 값으로 설정
      soldier.setdDay(1004);
    } else {
      // enlistDate와 dischargeDate 사이의 일수를 계산
      long dday = ChronoUnit.DAYS.between(enlistmentDate, dischargeDate);

      // 계산된 D-Day 값을 병사 객체에 설정
      soldier.setdDay(dday);
    }
  }
}
