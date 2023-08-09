package bitcamp.myapp.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.vo.Soldier;

@WebServlet("/soldier/list")
public class SoldierListServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    List<Soldier> soldiers = InitServlet.soldierDao.findAll();
    InitServlet.soldierDao.updateDday();

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>병사 목록</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>병사 목록</h1>");

    out.println("<div style='margin:5px;'>");
    out.println("<a href='/soldier/form.html'>새 병사</a>");
    out.println("</div>");

    out.println("<table border='1'>");
    out.println("<thead>");
    out.println(
        "<tr><th>번호</th><th>이름</th><th>계급</th><th>나이</th><th>입대일</th><th>전역일</th><th>D-Day</th><th>동작</th></tr>");
    out.println("</thead>");

    for (Soldier soldier : soldiers) {
      long dDay = ChronoUnit.DAYS.between(LocalDate.now(), soldier.getDischargeDate());
      out.printf(
          "<tr><td>%d</td><td><a href='/soldier/detail?no=%d'>%s</a></td><td>%s</td><td>%d</td><td>%s</td><td>%s</td><td>%d</td><td><a href='/soldier/detail?no=%d'>변경</a> | <a href='/soldier/delete?no=%d'>삭제</a></td></tr>\n",
          soldier.getNo(), soldier.getNo(), soldier.getName(), soldier.getRank(), soldier.getAge(),
          soldier.getEnlistmentDate(), soldier.getDischargeDate(), dDay, soldier.getNo(),
          soldier.getNo());
    }

    out.println("</tbody>");
    out.println("</table>");
    out.println("<a href='/'>메인</a>");
    out.println("</body>");
    out.println("</html>");
  }
}
