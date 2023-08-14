package bitcamp.myapp.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.vo.Soldier;

@WebServlet("/soldier/add.html")
public class SoldierAddServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Soldier loginUser = (Soldier) request.getSession().getAttribute("loginUser");
    if (loginUser == null || !loginUser.getMilitaryNumber().equals("07-72000001")) {
      response.sendRedirect("/auth/form.html");
      return;
    }

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>병사 등록</title>");
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
    out.println("    width: 400px;");
    out.println("    background-color: #ffffff;");
    out.println("    padding: 30px;");
    out.println("    border-radius: 8px;");
    out.println("    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);");
    out.println("}");
    out.println("table {");
    out.println("    width: 100%;");
    out.println("    border-collapse: collapse;");
    out.println("}");
    out.println("th {");
    out.println("    text-align: left;");
    out.println("    width: 60px;");
    out.println("    padding-right: 10px;");
    out.println("}");
    out.println("td {");
    out.println("    padding: 8px 0;");
    out.println("}");
    out.println(".btn, button.btn, a.btn, a.btn:link, a.btn:visited {");
    out.println("    display: inline-block;");
    out.println("    background-color: #0072BC;");
    out.println("    color: #ffffff;");
    out.println("    padding: 10px 20px;");
    out.println("    margin: 10px 0;");
    out.println("    border: none;");
    out.println("    border-radius: 5px;");
    out.println("    cursor: pointer;");
    out.println("    text-decoration: none;");
    out.println("    font-size: 16px;");
    out.println("    line-height: 1.5;");
    out.println("    vertical-align: middle;");
    out.println("    box-sizing: border-box;");
    out.println("    transition: background-color 0.3s;");
    out.println("}");
    out.println(".btn:hover {");
    out.println("    background-color: #005a93;");
    out.println("}");
    out.println("button.btn {");
    out.println("    overflow: visible;");
    out.println("    width: auto;");
    out.println("    height: auto;");
    out.println("    -webkit-appearance: none;");
    out.println("    -moz-appearance: none;");
    out.println("    appearance: none;");
    out.println("}");
    out.println("</style>");
    out.println("</head>");
    out.println("<body>");
    out.println("<div class='container'>");
    out.println("<h1>병사 등록</h1>");
    out.println("<form action='/soldier/add' method='post'>");
    out.println("<table>");
    out.println("<tr>");
    out.println("<th>이름</th>");
    out.println("<td><input type='text' name='name' required></td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("<th>나이</th>");
    out.println("<td><input type='number' name='age' required></td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("<th>암호</th>");
    out.println("<td><input type='password' name='password' required></td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("<th>계급</th>");
    out.println("<td>");
    out.println("<select name='rank'>");
    out.println("<option value='이병'>이병</option>");
    out.println("<option value='일병'>일병</option>");
    out.println("<option value='상병'>상병</option>");
    out.println("<option value='병장'>병장</option>");
    out.println("</select>");
    out.println("</td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("<th>입대일</th>");
    out.println("<td><input type='date' name='enlistmentDate' required></td>");
    out.println("</tr>");
    out.println("</table>");
    out.println("<br>");
    out.println("<a href='/soldier/list' class='btn'>뒤로</a>");
    out.println("<button class='btn'>등록</button>");
    out.println("</form>");
    out.println("</div>");
    out.println("</body>");
    out.println("</html>");
  }



  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Soldier s = new Soldier();
    s.setName(request.getParameter("name"));
    s.setRank(request.getParameter("rank"));

    String ageStr = request.getParameter("age");
    if (ageStr != null && !ageStr.isEmpty()) {
      s.setAge(Integer.parseInt(ageStr));
    }

    s.setPassword(request.getParameter("password"));
    s.setEnlistmentDate(LocalDate.parse(request.getParameter("enlistmentDate")));

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<meta http-equiv='refresh' content='1;url=/soldier/list'>");
    out.println("<title>병사</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>병사 등록</h1>");

    try {
      InitServlet.soldierDao.insert(s);
      out.println("<p>등록 성공입니다!</p>");

    } catch (Exception e) {
      out.println("<p>등록 실패입니다!</p>");
      e.printStackTrace();
    }

    out.println("</body>");
    out.println("</html>");
  }
}
