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

@Controller
public class BoardController {

  @Autowired
  BoardService boardService;

  @Autowired
  NcpObjectStorageService ncpObjectStorageService;


  @RequestMapping("/board/add")
  public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Soldier loginUser = (Soldier) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      return "redirect:../auth/login";
    }
    if (request.getMethod().equals("GET")) {
      return "/WEB-INF/jsp/board/form.jsp";
    }

    try {
      Board board = new Board();
      board.setWriter(loginUser);
      board.setTitle(request.getParameter("title"));
      board.setContent(request.getParameter("content"));
      board.setCategory(Integer.parseInt(request.getParameter("category")));

      ArrayList<AttachedFile> attachedFiles = new ArrayList<>();
      for (Part part : request.getParts()) {
        if (part.getName().equals("files") && part.getSize() > 0) {
          String uploadFileUrl =
              ncpObjectStorageService.uploadFile("bitcamp-nc7-bucket-01", "board2/", part);
          AttachedFile attachedFile = new AttachedFile();
          attachedFile.setFilePath(uploadFileUrl);
          attachedFiles.add(attachedFile);
        }
      }
      board.setAttachedFiles(attachedFiles);

      boardService.add(board);
      return "redirect:list?category=" + request.getParameter("category");

    } catch (Exception e) {
      request.setAttribute("message", "게시글 등록 오류!");
      request.setAttribute("refresh", "2;url=list?category=" + request.getParameter("category"));
      throw e;
    }
  }

  @RequestMapping("/board/delete")
  public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Soldier loginUser = (Soldier) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      return "redirect:../auth/login";
    }

    try {
      Board b = boardService.get(Integer.parseInt(request.getParameter("no")));
      if (b == null || b.getWriter().getNo() != loginUser.getNo()) {
        throw new Exception("해당 번호의 게시글이 없거나 삭제 권한이 없습니다.");
      } else {
        boardService.delete(b.getNo());
        return "redirect:list?category=" + request.getParameter("category");
      }

    } catch (Exception e) {
      request.setAttribute("refresh", "2;url=list?category=" + request.getParameter("category"));
      throw e;
    }
  }

  @RequestMapping("/board/detail")
  public String detail(HttpServletRequest request, HttpServletResponse response) throws Exception {

    try {
      int no = Integer.parseInt(request.getParameter("no"));

      Board board = boardService.get(no);
      if (board != null) {
        boardService.increaseViewCount(no);
        request.setAttribute("board", board);
      }
      return "/WEB-INF/jsp/board/detail.jsp";

    } catch (Exception e) {
      request.setAttribute("refresh",
          "5;url=/board/list?category=" + request.getParameter("category"));
      throw e;
    }
  }

  @RequestMapping("/board/list")
  public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      request.setAttribute("list",
          boardService.list(Integer.parseInt(request.getParameter("category"))));
      return "/WEB-INF/jsp/board/list.jsp";

    } catch (Exception e) {
      request.setAttribute("refresh", "1;url=/");
      throw e;
    }
  }

  @RequestMapping("/board/update")
  public String update(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
          String uploadFileUrl =
              ncpObjectStorageService.uploadFile("bitcamp-nc7-bucket-01", "board2/", part);
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

  @RequestMapping("/board/fileDelete")
  public String fileDelete(HttpServletRequest request, HttpServletResponse response)
      throws Exception {

    Soldier loginUser = (Soldier) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      return "redirect:../auth/login";
    }

    Board board = null;
    try {
      int fileNo = Integer.parseInt(request.getParameter("no"));

      AttachedFile attachedFile = boardService.getAttachedFile(fileNo);
      board = boardService.get(attachedFile.getBoardNo());
      if (board.getWriter().getNo() != loginUser.getNo()) {
        throw new Exception("게시글 변경 권한이 없습니다!");
      }

      if (boardService.deleteAttachedFile(fileNo) == 0) {
        throw new Exception("해당 번호의 첨부파일이 없다.");
      } else {
        return "redirect:detail?no=" + board.getNo();
      }

    } catch (Exception e) {
      request.setAttribute("refresh", "2;url=detail?no=" + board.getNo());
      throw e;
    }
  }
}











