package bitcamp.myapp.controller;

import bitcamp.myapp.dao.SoldierDao;
import bitcamp.myapp.service.NcpObjectStorageService;
import bitcamp.myapp.vo.Soldier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@Component("/soldier/update")
public class SoldierUpdateController implements PageController {
  SoldierDao soldierDao;
  PlatformTransactionManager txManager;
  NcpObjectStorageService ncpObjectStorageService;

  public SoldierUpdateController(SoldierDao soldierDao, PlatformTransactionManager txManager,
      NcpObjectStorageService ncpObjectStorageService) {
    this.soldierDao = soldierDao;
    this.txManager = txManager;
    this.ncpObjectStorageService = ncpObjectStorageService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = txManager.getTransaction(def);

    try {
      Soldier soldier = new Soldier();
      soldier.setNo(Integer.parseInt(request.getParameter("no")));
      soldier.setName(request.getParameter("name"));
      soldier.setRank(request.getParameter("rank"));
      soldier.setAge(Integer.parseInt(request.getParameter("age")));
      soldier.setMilitaryNumber(request.getParameter("militaryNumber"));

      Part photoPart = request.getPart("photo");
      if (photoPart.getSize() > 0) {
        String uploadFileUrl =
            ncpObjectStorageService.uploadFile("bitcamp-nc7-bucket-01", "soldier/", photoPart);
        soldier.setPhoto(uploadFileUrl);
      }

      if (soldierDao.update(soldier) == 0) {
        throw new Exception("해당 인원이 없습니다.");
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
