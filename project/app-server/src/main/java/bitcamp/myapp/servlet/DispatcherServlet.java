package bitcamp.myapp.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/app/*")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class DispatcherServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    String pageControllerPath = request.getPathInfo();

    response.setContentType("text/html;charset=UTF-8");

    // 클라이언트가 요청한 페이지 컨트롤러를 실행한다.
    request.getRequestDispatcher(pageControllerPath).include(request, response);

    // 페이지 컨트롤러 실행 중 오류가 발생했다면, 예외를 던진다.
    Exception exception = (Exception) request.getAttribute("exception");
    if (exception != null) {
      throw new ServletException(exception);
    }

    // 페이지 컨트롤러를 실행한 후, 페이지 컨트롤러가 설정한 페이지를 인클루드 한다.
    // 만약 redirect URL이 설정되어 있다면, redirect 한다.
    String viewUrl = (String) request.getAttribute("viewUrl");
    if (viewUrl.startsWith("redirect:")) {
      response.sendRedirect(viewUrl.substring(9)); // 예) redirect:/app/board/list
    } else {
      request.getRequestDispatcher(viewUrl).include(request, response);
    }
  }
}
