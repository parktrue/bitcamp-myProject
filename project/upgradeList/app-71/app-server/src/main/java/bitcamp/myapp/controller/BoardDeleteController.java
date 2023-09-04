package bitcamp.myapp.controller;

import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Soldier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller("/board/delete")
public class BoardDeleteController implements PageController {

  @Autowired
  BoardService boardService;

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
}









