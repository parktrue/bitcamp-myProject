package bitcamp.myapp.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.vo.Soldier;

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
    out.println("<title>병사 상세 정보</title>");
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
    out.println("    text-align: center;");
    out.println("    background-color: #ffffff;");
    out.println("    padding: 30px;");
    out.println("    border-radius: 8px;");
    out.println("    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);");
    out.println("}");
    out.println(".btn, h1, p, th, td {");
    out.println("    font-size: 16px;"); // 글자 크기 조정
    out.println("}");
    out.println(".btn {");
    out.println("    display: inline-block;");
    out.println("    background-color: #0072BC;");
    out.println("    color: #ffffff;");
    out.println("    padding: 10px 20px;");
    out.println("    margin: 10px;");
    out.println("    border: none;");
    out.println("    border-radius: 5px;");
    out.println("    cursor: pointer;");
    out.println("    text-decoration: none;");
    out.println("}");
    out.println("table {");
    out.println("    margin: 20px auto;");
    out.println("}");
    out.println("td {");
    out.println("    text-align: left;");
    out.println("    padding-left: 10px;");
    out.println("}");
    out.println("</style>");
    out.println("</head>");
    out.println("<body>");
    out.println("<div class='container'>");
    out.println("<h1>병사 상세 정보</h1>");

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
      out.println("<button class='btn'>변경</button>");
      out.println("<button class='btn' type='reset'>초기화</button>");
      out.printf("<a href='/soldier/delete?no=%d' class='btn'>삭제</a>\n", soldier.getNo());
      out.println("<a href='/soldier/list' class='btn'>목록</a>\n");
      out.println("</div>");
      out.println("</form>");
    }

    out.println("</div>"); // container div 종료
    out.println("</body>");
    out.println("</html>");
  }
}
