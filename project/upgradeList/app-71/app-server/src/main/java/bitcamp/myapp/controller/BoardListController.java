package bitcamp.myapp.controller;

import bitcamp.myapp.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller("/board/list")
public class BoardListController implements PageController {

  @Autowired
  BoardService boardService;

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      request.setAttribute("list", boardService.list(Integer.parseInt(request.getParameter("category"))));
      return "/WEB-INF/jsp/board/list.jsp";

    } catch (Exception e) {
      request.setAttribute("refresh", "1;url=/");
      throw e;
    }
  }

}











