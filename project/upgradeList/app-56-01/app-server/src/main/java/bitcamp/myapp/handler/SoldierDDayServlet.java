package bitcamp.myapp.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/soldier/dbupdate")
public class SoldierDDayServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // 디데이 업데이트 쿼리를 호출합니다.
    InitServlet.soldierDao.updateDday();

    response.sendRedirect("list"); // 업데이트 후 병사 목록 페이지로 리다이렉트
  }
}
