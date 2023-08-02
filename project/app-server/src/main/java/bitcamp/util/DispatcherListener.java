package bitcamp.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.MySQLBoardDao;
import bitcamp.myapp.dao.MySQLSoldierDao;
import bitcamp.myapp.dao.SoldierDao;
import bitcamp.myapp.handler.BoardAddListener;
import bitcamp.myapp.handler.BoardDeleteListener;
import bitcamp.myapp.handler.BoardDetailListener;
import bitcamp.myapp.handler.BoardListListener;
import bitcamp.myapp.handler.BoardUpdateListener;
import bitcamp.myapp.handler.LoginListener;
import bitcamp.myapp.handler.SoldierAddListener;
import bitcamp.myapp.handler.SoldierDeleteListener;
import bitcamp.myapp.handler.SoldierDetailListener;
import bitcamp.myapp.handler.SoldierListListener;
import bitcamp.myapp.handler.SoldierUpdateListener;
import bitcamp.myapp.handler.SoldierUpdater;

public class DispatcherListener implements ActionListener {

  // 객체 보관소
  Map<String, Object> beanContainer = new HashMap<>();

  public DispatcherListener() throws Exception {

    // Mybatis 준비
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryProxy(new SqlSessionFactoryBuilder()
        .build(Resources.getResourceAsStream("bitcamp/myapp/config/mybatis-config.xml")));
    beanContainer.put("sqlSessionFactory", sqlSessionFactory);

    // DAO 준비
    SoldierDao soldierDao = new MySQLSoldierDao(sqlSessionFactory);
    BoardDao boardDao = new MySQLBoardDao(sqlSessionFactory);
    beanContainer.put("soldierDao", soldierDao);
    beanContainer.put("boardDao", boardDao);

    // Listener 준비
    beanContainer.put("login", new LoginListener(soldierDao));

    beanContainer.put("soldier/add", new SoldierAddListener(soldierDao, sqlSessionFactory));
    beanContainer.put("soldier/list", new SoldierListListener(soldierDao));
    beanContainer.put("soldier/detail", new SoldierDetailListener(soldierDao));
    beanContainer.put("soldier/update", new SoldierUpdateListener(soldierDao, sqlSessionFactory));
    beanContainer.put("soldier/delete", new SoldierDeleteListener(soldierDao, sqlSessionFactory));
    beanContainer.put("soldier/updateAll", new SoldierUpdater(soldierDao, sqlSessionFactory));

    beanContainer.put("board/add", new BoardAddListener(1, boardDao, sqlSessionFactory));
    beanContainer.put("board/list", new BoardListListener(1, boardDao));
    beanContainer.put("board/detail", new BoardDetailListener(1, boardDao, sqlSessionFactory));
    beanContainer.put("board/update", new BoardUpdateListener(1, boardDao, sqlSessionFactory));
    beanContainer.put("board/delete", new BoardDeleteListener(1, boardDao, sqlSessionFactory));

    beanContainer.put("reading/add", new BoardAddListener(2, boardDao, sqlSessionFactory));
    beanContainer.put("reading/list", new BoardListListener(2, boardDao));
    beanContainer.put("reading/detail", new BoardDetailListener(2, boardDao, sqlSessionFactory));
    beanContainer.put("reading/update", new BoardUpdateListener(2, boardDao, sqlSessionFactory));
    beanContainer.put("reading/delete", new BoardDeleteListener(2, boardDao, sqlSessionFactory));
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    ActionListener listener = (ActionListener) beanContainer.get(prompt.getAttribute("menuPath"));
    if (listener == null) {
      throw new RuntimeException("해당 요청을 처리할 수 없습니다.");
    }
    listener.service(prompt);
  }

  public Object getBean(String name) {
    return beanContainer.get(name);
  }
}
