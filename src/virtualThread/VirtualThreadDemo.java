package virtualThread;

import java.util.ArrayList;
import java.util.List;

public class VirtualThreadDemo {

  private static final int NUMBER_OF_VIRTUAL_THREADS = 1000;

  public static void main(String[] args) throws InterruptedException {
    Runnable runnable = () -> System.out.println("Inside Thread: "+ Thread.currentThread());

    Thread platformThread = new Thread(runnable);

    platformThread.start();
    platformThread.join();
//    Inside Thread: Thread[#21,Thread-0,5,main]
//    platform 이라 지정했지만, 실제 Platform 인지 모른다.

    //platform Thread 명시
    Thread platformThread2 = Thread.ofPlatform().unstarted(runnable);
    platformThread2.start();
    platformThread2.join();

    Thread platformThread3 = Thread.ofVirtual().unstarted(runnable);
    platformThread3.start();
    platformThread3.join();
//Inside Thread: VirtualThread[#23]/runnable@ForkJoinPool-1-worker-1
//    JVM이 내부 스레드 풀 생성, ForkJoinPool이고, worker 1 에 Mount
    System.out.println();
    System.out.println();
    List<Thread> virtualThreads = new ArrayList<>();
    for(int i = 0 ; i < NUMBER_OF_VIRTUAL_THREADS ; i++){
      Thread virtualThread = Thread.ofVirtual().unstarted(runnable);
      virtualThreads.add(virtualThread);
    }

    for(Thread virtualThread : virtualThreads){
      virtualThread.start();
    }
    for(Thread virtualThread : virtualThreads){
      virtualThread.join();
    }
//    Inside Thread: VirtualThread[#26]/runnable@ForkJoinPool-1-worker-1
//    Inside Thread: VirtualThread[#27]/runnable@ForkJoinPool-1-worker-2
    //동일 캐리어 스레드 풀에서 실행 (ForkJoinPool) , 그러나 worker-1 , worker-2 로 서로 다르고,
    // 각 스레드는 동시에 실행되었기 때문에 다른 작업 스레드에 장착되어서 캐리어 역할을 한다.
//    1은 Worker 1, 2는 worker 2

    //1000개로 늘려도 오직 7개만 플랫폼 스레드를 생성한다.
    // 컴이 8코어기 때문에, 플랫폼 스레드는 최대 코어 수 만큼밖에 생성하지 못한다.
    // OS 1:1

  }

}
