package bitcamp.util;

import java.util.ArrayList;

public class ThreadPool implements ResourcePool<ManagedThread> {


  ArrayList<ManagedThread> list = new ArrayList<>();

  @Override
  public ManagedThread getResource() {
    if (list.size() == 0) {
      ManagedThread t = new ManagedThread(this);
      t.start();
      return t;
    }
    return list.remove(0);
  }

  @Override
  public void returnResource(ManagedThread resource) {

    list.add(resource);
  }
}
