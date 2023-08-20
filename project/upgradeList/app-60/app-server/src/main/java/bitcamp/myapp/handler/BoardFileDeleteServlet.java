package bitcamp.myapp.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Soldier;

@WebServlet("/board/file/delete")
public class BoardFileDeleteServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Soldier loginUser = (Soldier) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/form.html");
      return;
    }

    int category = Integer.parseInt(request.getParameter("category"));
    int fileNo = Integer.parseInt(request.getParameter("no"));

    // 첨부파일 번호로 첨부파일 데이터를 가져온다.
    AttachedFile attachedFile = InitServlet.boardDao.findFileBy(fileNo);
    // System.out.println(attachedFile);

    // 첨부파일 데이터에 있는 게시글 번호로 게시글 데이터를 가져온다.
    Board board = InitServlet.boardDao.findBy(category, attachedFile.getBoardNo());
    // System.out.println(board);

    // 게시글 데이터의 작성자와 로그인 한 작성자가 일치하는지 검사한다.
    if (board.getWriter().getNo() != loginUser.getNo()) {
      throw new ServletException("게시글 변경 권한이 없습니다!");
    }

    // 일치하면 첨부파일을 삭제한다.
    try {
      if (InitServlet.boardDao.deleteFile(fileNo) == 0) {
        throw new Exception("해당 번호의 첨부파일이 없거나 삭제 권한이 없습니다.");
      } else {
        response.sendRedirect("/board/detail?category=" + category + "&no=" + board.getNo());
      }
      InitServlet.sqlSessionFactory.openSession(false).commit();

    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();
      throw new RuntimeException(e);
    }
  }
}


