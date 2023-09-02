package bitcamp.myapp.controller;

import bitcamp.myapp.dao.SoldierDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("/soldier/delete")
public class SoldierDeleteController implements PageController {
  SoldierDao soldierDao;
  PlatformTransactionManager txManager;

  public SoldierDeleteController(SoldierDao soldierDao,   PlatformTransactionManager txManager) {
    this.soldierDao = soldierDao;
    this.txManager = txManager;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = txManager.getTransaction(def);

    try {
      if (soldierDao.delete(Integer.parseInt(request.getParameter("no"))) == 0) {
        throw new Exception("해당 번호의 인원이 없습니다.");
      } else {
        txManager.commit(status);
        return "redirect:list";
      }

    } catch (Exception e) {
      txManager.rollback(status);
      request.setAttribute("refresh", "2;url=list");

      throw e;
    }
  }
}
