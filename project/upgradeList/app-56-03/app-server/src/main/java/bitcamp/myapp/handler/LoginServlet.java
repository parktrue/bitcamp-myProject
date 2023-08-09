package bitcamp.myapp.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.vo.Soldier;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Soldier s = new Soldier();
    s.setMilitaryNumber(request.getParameter("milNum")); // "milnum"을 "milNum"으로 수정
    s.setPassword(request.getParameter("password"));

    Soldier loginUser = InitServlet.soldierDao.findByMilnumAndPassword(s);
    if (loginUser != null) {
      request.getSession().setAttribute("loginUser", loginUser);
      response.sendRedirect("/");
      return;
    }

    response.setContentType("text/html;charset=UTF-8");
    response.getWriter().println("<html><body>");
    response.getWriter().println("<h1>병사 정보가 일치하지 않습니다.</h1>");
    response.getWriter().println("<a href='/auth/form.html'>로그인 페이지로 돌아가기</a>");
    response.getWriter().println("</body></html>");
  }
}
