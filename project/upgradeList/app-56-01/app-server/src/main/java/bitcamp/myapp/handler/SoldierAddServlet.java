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

@WebServlet("/soldier/add")
public class SoldierAddServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>병사 등록</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>병사 등록 폼</h1>");
    out.println("<form action='/soldier/add' method='post'>");
    out.println("군번: <input type='text' name='militaryNumber'><br>");
    out.println("이름: <input type='text' name='name'><br>");
    out.println("계급: <select name='rank'>");
    out.println("<option value='1'>이병</option>");
    out.println("<option value='2'>일병</option>");
    out.println("<option value='3'>상병</option>");
    out.println("<option value='4'>병장</option>");
    out.println("</select><br>");
    out.println("나이: <input type='number' name='age'><br>");
    out.println("비밀번호: <input type='password' name='password'><br>");
    out.println("입대일: <input type='date' name='enlistmentDate'><br>");
    out.println("<button>등록</button>");
    out.println("</form>");
    out.println("</body>");
    out.println("</html>");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Soldier s = new Soldier();
    s.setName(request.getParameter("name"));
    s.setMilitaryNumber(request.getParameter("militaryNumber"));
    s.setRank(request.getParameter("rank"));

    // 나이 값을 안전하게 파싱
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
