package bitcamp.myapp.handler;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import bitcamp.myapp.dao.SoldierDao;
import bitcamp.myapp.vo.Soldier;
import bitcamp.util.BreadcrumbPrompt;

public class SoldierAddListener implements SoldierActionListener {

  SoldierDao soldierDao;

  private static Map<Integer, Integer> yearToSerialNumber = new HashMap<>();

  public SoldierAddListener(SoldierDao soldierDao) {
    this.soldierDao = soldierDao;

    List<Soldier> soldiers = soldierDao.list();
    for (Soldier soldier : soldiers) {
      String[] parts = soldier.getMilitaryNumber().split("-");
      int year = Integer.parseInt(parts[0]);
      int serialNumber = Integer.parseInt(parts[1]);
      yearToSerialNumber.putIfAbsent(year, serialNumber);
      int maxSerialNumberForYear = yearToSerialNumber.get(year);
      yearToSerialNumber.put(year, Math.max(maxSerialNumberForYear, serialNumber));
    }
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    Soldier s = new Soldier();
    s.setName(prompt.inputString("이름? "));
    s.setAge(prompt.inputInt("나이? "));
    s.setPassword(prompt.inputString("암호? "));
    s.setRank(SoldierActionListener.inputRank(null, prompt));
    s.setEnlistmentDate(this.inputLocalDate(prompt));

    int thisYear = s.getEnlistmentDate().getYear();
    String militaryNumber;

    do {
      yearToSerialNumber.putIfAbsent(thisYear, 0);
      int serialNumberForThisYear = yearToSerialNumber.get(thisYear) + 1;
      militaryNumber = String.format("%02d-7200%04d", thisYear, serialNumberForThisYear);
      yearToSerialNumber.put(thisYear, serialNumberForThisYear);
    } while (SoldierDeleteListener.deletedMilitaryNumbers.getOrDefault(thisYear, new HashSet<>())
        .contains(militaryNumber.split("-")[1]));

    s.setMilitaryNumber(militaryNumber);
    System.out.println("Generated military number: " + militaryNumber);

    s.setDischargeDate(s.getEnlistmentDate().plusMonths(18).minusDays(1));
    s.setdDay(ChronoUnit.DAYS.between(LocalDate.now(), s.getDischargeDate()));

    soldierDao.insert(s);
  }

  @Override
  public LocalDate inputLocalDate(BreadcrumbPrompt prompt) {
    LocalDate minEnlistmentDate = LocalDate.now().minusMonths(18);

    while (true) {
      System.out.printf("입대일 입력 가능 기간은 %s 부터 %s 까지입니다.\n", minEnlistmentDate, LocalDate.now());
      String inputDate = prompt.inputString("입대일을 입력하세요 (yyyy-MM-dd 형식): ");
      try {
        LocalDate enlistmentDate = LocalDate.parse(inputDate);
        if (enlistmentDate.isBefore(minEnlistmentDate) || enlistmentDate.isAfter(LocalDate.now())) {
          System.out.println("입력 가능한 범위를 벗어났습니다. 다시 입력해주세요.");
          continue;
        }
        return enlistmentDate;
      } catch (DateTimeParseException e) {
        System.out.println("입력한 날짜의 형식이 올바르지 않습니다. 'yyyy-MM-dd' 형식으로 다시 입력하세요.");
      }
    }
  }
}
