package bitcamp.myapp.controller;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.service.NcpObjectStorageService;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Soldier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.util.ArrayList;

@Component("/board/update")
public class BoardUpdateController implements PageController {
  BoardDao boardDao;
  PlatformTransactionManager txManager;
  NcpObjectStorageService ncpObjectStorageService;


  public BoardUpdateController(BoardDao boardDao, PlatformTransactionManager txManager,
      NcpObjectStorageService ncpObjectStorageService) {
    this.boardDao = boardDao;
    this.txManager = txManager;
    this.ncpObjectStorageService = ncpObjectStorageService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Soldier loginUser = (Soldier) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      return "redirect:../auth/login";
    }

    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = txManager.getTransaction(def);

    try {
      Board board = new Board();
      board.setWriter(loginUser);
      board.setNo(Integer.parseInt(request.getParameter("no")));
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

      if (boardDao.update(board) == 0) {
        throw new Exception("게시글이 없거나 변경 권한이 없습니다.");
      } else {
        if (attachedFiles.size() > 0) {
          int count = boardDao.insertFiles(board);
          System.out.println(count);
        }

        txManager.commit(status);
        return "redirect:list?category=" + request.getParameter("category");
      }

    } catch (Exception e) {
      txManager.rollback(status);
      request.setAttribute("refresh", "2;url=detail?category=" + request.getParameter(
          "category") + "&no=" + request.getParameter("no"));
      throw e;
    }
  }
}











