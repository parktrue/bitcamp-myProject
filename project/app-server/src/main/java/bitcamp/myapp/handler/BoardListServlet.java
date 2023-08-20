package bitcamp.myapp.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.vo.Board;

@WebServlet("/board/list")
public class BoardListServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    int category = Integer.parseInt(request.getParameter("category"));

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>게시글</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>게시글 목록</h1>");
    out.println("<div style='margin:5px;'>");
    out.printf("<a href='/board/form?category=%d'>새 글</a>\n", category);
    out.println("</div>");
    out.println("<table border='1'>");
    out.println("<thead>");
    out.println("  <tr><th>번호</th> <th>제목</th> <th>작성자</th> <th>조회수</th> <th>등록일</th></tr>");
    out.println("</thead>");

    List<Board> list = InitServlet.boardDao.findAll(category);

    out.println("<tbody>");
    for (Board board : list) {
      out.printf(
          "<tr>" + " <td>%d</td>" + " <td><a href='/board/detail?category=%d&no=%d'>%s</a></td>"
              + " <td>%s</td>" + " <td>%d</td>" + " <td>%s</td></tr>\n",
          board.getNo(), board.getCategory(), board.getNo(),
          (board.getTitle().length() > 0 ? board.getTitle() : "제목없음"), board.getWriter().getName(),
          board.getViewCount(), dateFormatter.format(board.getCreatedDate()));
    }
    out.println("</tbody>");
    out.println("</table>");
    out.println("<a href='/'>메인</a>");
    out.println("</body>");
    out.println("</html>");
  }

}


