package bitcamp.myapp.handler;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Soldier;

@WebServlet("/board/update")
public class BoardUpdateServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Soldier loginUser = (Soldier) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/form.html");
      return;
    }

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>게시글</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>게시글 변경</h1>");

    try {
      List<FileItem> parts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);

      String uploadDir = request.getServletContext().getRealPath("/upload/board/");
      ArrayList<AttachedFile> attachedFiles = new ArrayList<>();

      Board board = new Board();
      board.setWriter(loginUser);

      for (FileItem part : parts) {
        if (part.isFormField()) {
          if (part.getFieldName().equals("title")) {
            board.setTitle(part.getString("UTF-8"));
          } else if (part.getFieldName().equals("content")) {
            board.setContent(part.getString("UTF-8"));
          } else if (part.getFieldName().equals("category")) {
            board.setCategory(Integer.parseInt(part.getString("UTF-8")));
          } else if (part.getFieldName().equals("no")) {
            board.setNo(Integer.parseInt(part.getString("UTF-8")));
          }
        } else {
          String filename = UUID.randomUUID().toString();
          part.write(new File(uploadDir, filename));
          AttachedFile attachedFile = new AttachedFile();
          attachedFile.setFilePath(filename);
          attachedFiles.add(attachedFile);
        }
      }
      board.setAttachedFiles(attachedFiles);

      if (InitServlet.boardDao.update(board) == 0) {
        out.println("<p>게시글이 없거나 변경 권한이 없습니다.</p>");
      } else {
        // 게시글을 정상적으로 변경했으면, 그 게시글의 첨부파일을 추가한다.
        int count = InitServlet.boardDao.insertFiles(board);
        System.out.println(count);

        out.println("<p>변경했습니다!</p>");
        response.setHeader("refresh", "1;url=/board/list?category=" + board.getCategory());
      }
      InitServlet.sqlSessionFactory.openSession(false).commit();

    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();
      out.println("<p>게시글 변경 실패입니다!</p>");
      e.printStackTrace();
    }
    out.println("</body>");
    out.println("</html>");
  }
}


