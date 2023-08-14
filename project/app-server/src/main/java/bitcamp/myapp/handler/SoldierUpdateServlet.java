package bitcamp.myapp.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.vo.Soldier;

@WebServlet("/soldier/update")
public class SoldierUpdateServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Soldier loginUser = (Soldier) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/form.html");
    }

    Soldier soldier = new Soldier();
    soldier.setNo(Integer.parseInt(request.getParameter("no")));
    soldier.setName(request.getParameter("name"));
    soldier.setRank(request.getParameter("rank"));
    soldier.setAge(Integer.parseInt(request.getParameter("age")));
    soldier.setMilitaryNumber(request.getParameter("militaryNumber"));

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
    out.println("<h1>병사 변경</h1>");

    try {
      if (InitServlet.soldierDao.update(soldier) == 0) {
        out.println("<p>해당 병사가 없습니다.</p>");
      } else {
        InitServlet.sqlSessionFactory.openSession(false).commit();
        out.println("<p>변경했습니다!</p>");
      }
    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();
      out.println("<p>변경 실패입니다!</p>");
      e.printStackTrace();
    }

    out.println("</body>");
    out.println("</html>");
  }

}
