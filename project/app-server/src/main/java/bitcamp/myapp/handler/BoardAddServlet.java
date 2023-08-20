package bitcamp.myapp.handler;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletContext;
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

@WebServlet("/board/add")
public class BoardAddServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Soldier loginUser = (Soldier) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/form.html");
      return;
    }

    try {
      // 멀티파트의 각 파트 데이터를 저장할 객체를 만드는 공장
      DiskFileItemFactory factory = new DiskFileItemFactory();

      // 멀티파트 형식으로 넘어 온 요청 파라미터를 분석하여 처리하는 객체
      ServletFileUpload upload = new ServletFileUpload(factory);

      // 멀티파트 요청 파라미터를 분석
      List<FileItem> parts = upload.parseRequest(request);

      // 각각의 파트에서 값을 꺼낸다.
      Board board = new Board();
      board.setWriter(loginUser);

      // 웹 애플리케이션 환경 정보를 알고 있는 객체 꺼내기
      ServletContext 웹애플리케이션환경정보 = request.getServletContext();

      // 웹 애플리케이션 환경정보에서 /upload/board 디렉토리의 실제 경로를 계산하여 추출한다.
      String uploadDir = 웹애플리케이션환경정보.getRealPath("/upload/board/");
      // System.out.println(uploadDir);

      ArrayList<AttachedFile> attachedFiles = new ArrayList<>();

      for (FileItem part : parts) {
        if (part.isFormField()) { // 일반 데이터
          if (part.getFieldName().equals("title")) {
            board.setTitle(part.getString("UTF-8"));
          } else if (part.getFieldName().equals("content")) {
            board.setContent(part.getString("UTF-8"));
          } else if (part.getFieldName().equals("category")) {
            board.setCategory(Integer.parseInt(part.getString("UTF-8")));
          }
        } else { // 파일 데이터
          // 업로드 파일은 배포 폴더에 저장한다.
          // 1) 파일을 저장할 때 사용할 이름을 준비한다.
          String filename = UUID.randomUUID().toString();

          // 2) upload 배포 폴더에서 파일을 저장한다.
          part.write(new File(uploadDir, filename));

          // 3) 파일 이름을 객체에 보관하여 목록에 추가한다.
          AttachedFile attachedFile = new AttachedFile();
          attachedFile.setFilePath(filename);

          attachedFiles.add(attachedFile);
        }
      }
      board.setAttachedFiles(attachedFiles);


      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<meta charset='UTF-8'>");
      out.printf("<meta http-equiv='refresh' content='1;url=/board/list?category=%d'>\n",
          board.getCategory());
      out.println("<title>게시글</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>게시글 등록</h1>");
      try {
        // System.out.println(board.getNo());
        InitServlet.boardDao.insert(board);
        // System.out.println(board.getNo());
        int count = InitServlet.boardDao.insertFiles(board);
        System.out.println(count);

        InitServlet.sqlSessionFactory.openSession(false).commit();
        out.println("<p>등록 성공입니다!</p>");

      } catch (Exception e) {
        InitServlet.sqlSessionFactory.openSession(false).rollback();
        out.println("<p>등록 실패입니다!</p>");
        e.printStackTrace();
      }
      out.println("</body>");
      out.println("</html>");

    } catch (Exception e) {
      throw new ServletException(e);
    }
  }
}


