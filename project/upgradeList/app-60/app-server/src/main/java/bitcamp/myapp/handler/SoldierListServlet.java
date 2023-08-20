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

    Soldier loginUser = (Soldier) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/form.html");
      return;
    }

    List<Soldier> soldiers = InitServlet.soldierDao.findAll();
    InitServlet.soldierDao.updateDday();

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>병사 목록</title>");
    out.println("<style>");
    out.println("body {");
    out.println("    font-family: Arial, sans-serif;");
    out.println("    height: 100vh;");
    out.println("    margin: 0;");
    out.println("    display: flex;");
    out.println("    justify-content: center;");
    out.println("    align-items: center;");
    out.println("    background-color: #f4f4f4;");
    out.println("}");
    out.println(".container {");
    out.println("    background-color: #ffffff;");
    out.println("    padding: 30px;");
    out.println("    border-radius: 8px;");
    out.println("    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);");
    out.println("}");
    out.println(".btn {");
    out.println("    background-color: #0072BC; /* 국방색 */");
    out.println("    color: #ffffff;");
    out.println("    padding: 10px 20px;");
    out.println("    border: none;");
    out.println("    border-radius: 5px;");
    out.println("    cursor: pointer;");
    out.println("}");
    out.println("</style>");
    out.println("</head>");
    out.println("<body>");
    out.println("<div class='container'>");
    out.println("<h1>병사 목록</h1>");

    out.println("<a href='/soldier/add' class='btn'>전입신병 등록</a>");
    out.println("<a href='/index.html' class='btn'>메인 화면으로</a>");
    out.println("<img src='/marinedog2.png' alt='설명' style='margin-left: 20px' />");
    out.println("<br><br>");

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
    out.println("</div>");
    out.println("</body>");
    out.println("</html>");
  }
}
