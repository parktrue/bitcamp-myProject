package bitcamp.myapp.controller;

import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.service.NcpObjectStorageService;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Soldier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.util.ArrayList;

@Controller("/board/update")
public class BoardUpdateController implements PageController {

  @Autowired
  BoardService boardService;

  @Autowired
  NcpObjectStorageService ncpObjectStorageService;

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Soldier loginUser = (Soldier) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      request.getParts(); // 일단 클라이언트가 보낸 파일을 읽는다. 그래야 응답 가능!
      return "redirect:../auth/login";
    }

    try {
      Board board = boardService.get(Integer.parseInt(request.getParameter("no")));
      if (board == null || board.getWriter().getNo() != loginUser.getNo()) {
        throw new Exception("게시글이 존재하지 않거나 변경 권한이 없습니다.");
      }

      board.setTitle(request.getParameter("title"));
      board.setContent(request.getParameter("content"));

      ArrayList<AttachedFile> attachedFiles = new ArrayList<>();
      for (Part part : request.getParts()) {
        if (part.getName().equals("files") && part.getSize() > 0) {
          String uploadFileUrl = ncpObjectStorageService.uploadFile(
              "bitcamp-nc7-bucket-01", "board2/", part);
          AttachedFile attachedFile = new AttachedFile();
          attachedFile.setFilePath(uploadFileUrl);
          attachedFiles.add(attachedFile);
        }
      }
      board.setAttachedFiles(attachedFiles);

      boardService.update(board);
      return "redirect:list?category=" + board.getCategory();

    } catch (Exception e) {
      request.setAttribute("refresh", "2;url=detail?no=" + request.getParameter("no"));
      throw e;
    }
  }
}











