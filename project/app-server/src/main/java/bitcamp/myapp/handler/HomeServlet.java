package bitcamp.myapp.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.vo.Soldier;

@WebServlet("/index.html")
public class HomeServlet extends HttpServlet {

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
    out.println("<title>병력 관리 시스템</title>");
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
    out.println("    position: relative;");
    out.println("    text-align: center;");
    out.println("    background-color: #ffffff;");
    out.println("    padding: 30px;");
    out.println("    border-radius: 8px;");
    out.println("    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);");
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
    out.println("img.logo {");
    out.println("    width: 100px;");
    out.println("    margin-bottom: 20px;");
    out.println("}");
    out.println(".login-info {");
    out.println("    position: absolute;");
    out.println("    bottom: 10px;");
    out.println("    left: 10px;");
    out.println("    font-size: 12px;");
    out.println("}");
    out.println("</style>");
    out.println("</head>");
    out.println("<body>");
    out.println("<div class='container'>");
    out.println("<img src='/marinedog.png' alt='병력 관리' class='logo'>");
    out.println("<h1>병력 관리 시스템</h1>");
    out.println("<a href='/soldier/list' class='btn'>병사 목록</a>");
    out.println("<a href='/board/list?category=1' class='btn'>게시판</a>");
    out.println("<a href='/board/list?category=2' class='btn'>독서록</a>");

    Soldier loginUser = (Soldier) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      out.println("<a href='/auth/form.html' class='btn'>로그인</a>");
    } else {
      out.printf("<a href='/auth/logout' class='btn'>로그아웃</a>");
      out.printf("<div class='login-info'>로그인중인 병사: %s</div>", loginUser.getName());
    }
    out.println("</div>");
    out.println("</body>");
    out.println("</html>");
  }
}
