package threadInterrupt;

public class InterruptMain {

  public static void main(String[] args) {
    Thread thread = new Thread(new BlockingTask());

    thread.start();
    //start 와 run 의 차이는, 현재 컨텍스트에서 스레드를 실행하는지,
    //아니면 새로운 스레드를 생성해서 실행하는지의 차이가 있다.
    //우리 예제에서 Interrupt 는 새로운 스레드에서 진행되어야 하므로 start 필요 .

    thread.interrupt();

  }

  private static class BlockingTask implements Runnable {

    @Override
    public void run() {
      try {
        Thread.sleep(500000);
      }catch(InterruptedException e) {
        System.out.println("블로킹 스레드 종료. ");
      }
    }
  }

}
