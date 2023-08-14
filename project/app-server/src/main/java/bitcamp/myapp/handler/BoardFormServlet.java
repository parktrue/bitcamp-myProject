package bitcamp.myapp.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.vo.Soldier;

@WebServlet("/board/form")
public class BoardFormServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Soldier loginUser = (Soldier) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/form.html");
    }

    int category = Integer.parseInt(request.getParameter("category"));

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>게시글 작성</title>");
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
    out.println("    width: 400px;");
    out.println("    margin: 0 auto;");
    out.println("}");
    out.println("button, input[type='text'], textarea {");
    out.println("    box-sizing: border-box;");
    out.println("    display: block;");
    out.println("    width: 100%;");
    out.println("    padding: 10px;");
    out.println("    margin: 0 auto 15px;");
    out.println("    border: 1px solid #ccc;");
    out.println("    border-radius: 5px;");
    out.println("}");
    out.println("textarea {");
    out.println("    height: 150px;");
    out.println("    resize: none;");
    out.println("}");
    out.println(".btn {");
    out.println("    display: inline-block;");
    out.println("    background-color: #0072BC;");
    out.println("    color: #ffffff;");
    out.println("    padding: 10px 20px;");
    out.println("    border: none;");
    out.println("    border-radius: 5px;");
    out.println("    cursor: pointer;");
    out.println("    text-decoration: none;");
    out.println("}");
    out.println("</style>");
    out.println("</head>");
    out.println("<body>");
    out.println("<div class='container'>");
    out.println("<h1>게시글 작성</h1>");
    out.println("<form action='/board/add' method='post'>");
    out.println("<input type='text' name='title' placeholder='제목을 입력하세요'><br>");
    out.println("<textarea name='content' placeholder='내용을 입력하세요'></textarea><br>");
    out.printf("<input type='hidden' name='category' value='%d'>\n", category);
    out.println("<button type='submit' class='btn'>등록</button>");
    out.println("</form>");
    out.println("</div>");
    out.println("</body>");
    out.println("</html>");
  }
}
