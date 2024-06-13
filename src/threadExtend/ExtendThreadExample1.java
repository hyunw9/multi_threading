package threadExtend;

public class ExtendThreadExample1 {

  public static void main(String[] args) throws InterruptedException {
    Thread thread = new CustomThread();
    thread.setName("새로운 워커스레드");

    thread.setPriority(Thread.MAX_PRIORITY);

    System.out.println(Thread.currentThread().getName()+"스레드 시작 전");
    thread.start();
    System.out.println(Thread.currentThread().getName()+"스레드 시작 후");

    Thread.sleep(10000);

  }

  public static class CustomThread extends Thread {
    //Thread 클래스의 run 을 Override 함으로써 로직 분리 가능
    @Override
    public void run() {
      System.out.println(Thread.currentThread().getName());

    }
  }
}
