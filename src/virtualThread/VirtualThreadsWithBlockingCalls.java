package virtualThread;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.ls.LSOutput;

public class VirtualThreadsWithBlockingCalls {

  private static final int NUMBER_OF_VIRTUAL_THREADS = 100;

  public static void main(String[] args) throws InterruptedException {
    List<Thread> threads = new ArrayList<>();

    for(int i = 0; i < NUMBER_OF_VIRTUAL_THREADS; i++) {
      Thread virtualThread = Thread.ofVirtual().unstarted(new BlockingTask());
      threads.add(virtualThread);
    }
    for(Thread thread : threads) {
      thread.start();
    }
    for(Thread thread : threads) {
      thread.join();
    }
  }
  private static class BlockingTask implements Runnable {

    @Override
    public void run() {
      System.out.println("Inside Thread: "+ Thread.currentThread()+ " before Blocking call");
      try{
        Thread.sleep(1000);
      }catch(InterruptedException e){
        throw new RuntimeException(e);
      }
      System.out.println("Inside Thread: "+ Thread.currentThread()+ " after Blocking call");
      //1초간 대기 후에 세부 정보 호출
    }
//    Inside Thread: VirtualThread[#21]/runnable@ForkJoinPool-1-worker-1 before Blocking call
//    Inside Thread: VirtualThread[#22]/runnable@ForkJoinPool-1-worker-2 before Blocking call
//    Inside Thread: VirtualThread[#22]/runnable@ForkJoinPool-1-worker-1 after Blocking call
//    Inside Thread: VirtualThread[#21]/runnable@ForkJoinPool-1-worker-2 after Blocking call
    //21번은 1번 플랫폼 스레드에 마운트 되지만, blocking call 이후에는 2번에 마운트된다.
    //22번은 2번에 마운트되었지만, blocking call  이후는 1번에 마운트된다.
    //이는 개발자가 관여할 수 없는 영역이다. OS의 영역.


    //가상스레드는 코드에 스레드가 오래 머물러야 하는 경우 유리하다.

  }

}
