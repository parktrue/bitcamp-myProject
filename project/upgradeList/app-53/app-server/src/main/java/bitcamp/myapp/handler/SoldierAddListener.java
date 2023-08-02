package bitcamp.myapp.handler;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSessionFactory;
import bitcamp.myapp.dao.SoldierDao;
import bitcamp.myapp.vo.Soldier;
import bitcamp.util.BreadcrumbPrompt;

public class SoldierAddListener implements SoldierActionListener {

  SoldierDao soldierDao;
  SqlSessionFactory sqlSessionFactory;

  private static Map<Integer, Integer> yearToSerialNumber = new HashMap<>();

  public SoldierAddListener(SoldierDao soldierDao, SqlSessionFactory sqlSessionFactory) {
    this.soldierDao = soldierDao;
    this.sqlSessionFactory = sqlSessionFactory;

    List<Soldier> soldiers = soldierDao.findAll();
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
  public void service(BreadcrumbPrompt prompt) throws IOException {
    Soldier s = new Soldier();
    s.setName(prompt.inputString("이름을 입력하세요: "));
    s.setAge(prompt.inputInt("나이를 입력하세요: "));
    s.setPassword(prompt.inputString("암호를 입력하세요: "));
    s.setRank(SoldierActionListener.inputRank(null, prompt));

    LocalDate enlistmentDate = inputEnlistmentDate(prompt);
    s.setEnlistmentDate(enlistmentDate);

    // Generate the military number.
    int thisYear = enlistmentDate.getYear();
    String militaryNumber;
    do {
      yearToSerialNumber.putIfAbsent(thisYear, 720000000);
      int serialNumberForThisYear = yearToSerialNumber.get(thisYear) + 1;
      militaryNumber = String.format("%04d-%08d", thisYear, serialNumberForThisYear);
      yearToSerialNumber.put(thisYear, serialNumberForThisYear);
    } while (SoldierDeleteListener.deletedMilitaryNumbers.getOrDefault(thisYear, new HashSet<>())
        .contains(militaryNumber.split("-")[1]));

    s.setMilitaryNumber(militaryNumber);
    prompt.println("생성된 군번: " + militaryNumber);

    // Set the discharge date and D-Day.
    s.setDischargeDate(s.getEnlistmentDate().plusMonths(18).minusDays(1));
    s.setdDay(ChronoUnit.DAYS.between(LocalDate.now(), s.getDischargeDate()));

    // Insert the new soldier into the database.
    try {
      soldierDao.insert(s);
      sqlSessionFactory.openSession(false).commit();
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      throw new RuntimeException(e);
    }
  }

  private LocalDate inputEnlistmentDate(BreadcrumbPrompt prompt) throws IOException {
    LocalDate minEnlistmentDate = LocalDate.now().minusMonths(18);
    LocalDate maxEnlistmentDate = LocalDate.now();
    LocalDate enlistmentDate;

    while (true) {
      // Always display the possible range of enlistment dates before asking for the date.
      prompt.printf("입대일 입력 가능 기간은 %s 부터 %s 까지입니다.\n", minEnlistmentDate, maxEnlistmentDate);
      prompt.end(); // Immediately flush the buffer.

      String inputDate = prompt.inputString("입대일을 입력하세요 (yyyy-MM-dd 형식): ");
      try {
        enlistmentDate = LocalDate.parse(inputDate);
        if (enlistmentDate.isBefore(minEnlistmentDate)
            || enlistmentDate.isAfter(maxEnlistmentDate)) {
          prompt.println("입력 가능한 범위를 벗어났습니다. 다시 입력해주세요.");
          prompt.end(); // Immediately flush the buffer.
          continue;
        }
        break;
      } catch (DateTimeParseException e) {
        prompt.println("입력한 날짜의 형식이 올바르지 않습니다. 'yyyy-MM-dd' 형식으로 다시 입력하세요.");
        prompt.end(); // Immediately flush the buffer.
      }
    }
    return enlistmentDate;
  }

}
