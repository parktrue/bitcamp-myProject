package bitcamp.myapp.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.vo.Soldier;

// ... (다른 import 문은 생략)

@WebServlet("/soldier/detail")
public class SoldierDetailServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Soldier soldier = InitServlet.soldierDao.findBy(Integer.parseInt(request.getParameter("no")));

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>병사</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>병사</h1>");

    if (soldier == null) {
      out.println("<p>해당 번호의 병사가 없습니다!</p>");

    } else {
      out.println("<form action='/soldier/update' method='post'>");
      out.println("<table border='1'>");
      out.printf("<tr><th style='width:120px;'>번호</th>"
          + " <td style='width:300px;'><input type='text' name='no' value='%d' readonly></td></tr>\n",
          soldier.getNo());
      out.printf("<tr><th>이름</th>" + " <td><input type='text' name='name' value='%s'></td></tr>\n",
          soldier.getName());
      out.printf(
          "<tr><th>군번</th>"
              + " <td><input type='text' name='militaryNumber' value='%s'></td></tr>\n",
          soldier.getMilitaryNumber());
      out.printf("<tr><th>나이</th>" + " <td><input type='text' name='age' value='%d'></td></tr>\n",
          soldier.getAge());
      out.println("<tr><th>암호</th>" + " <td><input type='password' name='password'></td></tr>");
      out.printf(
          "<tr><th>입대일</th>"
              + " <td><input type='date' name='enlistmentDate' value='%s'></td></tr>\n",
          soldier.getEnlistmentDate().toString());
      out.printf(
          "<tr><th>전역일</th>"
              + " <td><input type='date' name='dischargeDate' value='%s'></td></tr>\n",
          soldier.getDischargeDate().toString());
      out.printf("<tr><th>D-Day</th>" + " <td>%d</td></tr>\n", soldier.getdDay());
      out.printf(
          "<tr><th>계급</th>\n" + " <td><select name='rank'>\n"
              + " <option value='이병' %s>이병</option>\n" + " <option value='일병' %s>일병</option>\n"
              + " <option value='상병' %s>상병</option>\n"
              + " <option value='병장' %s>병장</option></select></td></tr>\n",
          (soldier.getRank().equals("이병") ? "selected" : ""),
          (soldier.getRank().equals("일병") ? "selected" : ""),
          (soldier.getRank().equals("상병") ? "selected" : ""),
          (soldier.getRank().equals("병장") ? "selected" : ""));
      out.println("</table>");

      out.println("<div>");
      out.println("<button>변경</button>");
      out.println("<button type='reset'>초기화</button>");
      out.printf("<a href='/soldier/delete?no=%d'>삭제</a>\n", soldier.getNo());
      out.println("<a href='/soldier/list'>목록</a>\n");
      out.println("</div>");
      out.println("</form>");
    }

    out.println("</body>");
    out.println("</html>");
  }
}
