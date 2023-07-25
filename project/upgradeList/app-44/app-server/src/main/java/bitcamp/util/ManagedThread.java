package bitcamp.util;

public class ManagedThread extends Thread {

  ResourcePool<ManagedThread> pool;
  Job job;

  public ManagedThread(ResourcePool<ManagedThread> pool) {
    this.pool = pool;
  }

  public void setJob(Job job) {
    this.job = job;
    synchronized (this) {
      this.notify();

    }
  }

  @Override
  public void run() {
    while (true) {
      try {
        synchronized (this) {
          this.wait();
        }

        job.execute();
        pool.returnResource(this);
      } catch (Exception e) {
        System.err.println("스레드 실행 중 오류발생!");
        e.printStackTrace();
      }
    }
  }
}
