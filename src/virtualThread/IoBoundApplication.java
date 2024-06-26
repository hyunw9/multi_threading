package virtualThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IoBoundApplication  {

  private  static final int NUMBER_OF_TASKS = 10000;

  public static void main(String[] args) {
    System.out.printf("Running %d tasks\n", NUMBER_OF_TASKS);

    long start = System.currentTimeMillis();
    performTasks();
    System.out.printf("Tasks took %dms to complete\n",System.currentTimeMillis()-start);
  }

  private static void performTasks() {
    try(ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()){
      /*
      * 작업 단위 스레드 모델을 사용할 때, 캐시 스레드 풀을 사용하면 충돌이 발생한다.
      * 운영체제가 플랫폼스레드를 더 할당하는 것을 거부.
      * 그러면 어떻게 해야하나 ? newVirtualThreadPerTaskThreadExecutor() 를 사용하면
      * 발생하는 모든 작업에 대해 가상 스레드를 생성한다.
       */
      for(int i = 0; i < NUMBER_OF_TASKS; i++){
        executorService.submit(new Runnable() {
          @Override
          public void run() {
            for(int j = 0; j < 100; j++){
              blockingIoOperation();
            }
          }
        });
      }
    }
  }

  private static void blockingIoOperation() {
    System.out.println("Executing a blokcing task from Thread: "+ Thread.currentThread());
    try{
      Thread.sleep(10);
    }catch (InterruptedException e){
      throw new RuntimeException(e);
    }
  }

}
