package bitcamp.myapp.controller;

import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Soldier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller("/board/fileDelete")
public class BoardFileDeleteController {

  @Autowired
  BoardService boardService;

  @RequestMapping
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

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











