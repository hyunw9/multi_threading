package io_bound_application;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IoBoundApplication {

  private static final int NUMBER_OF_TASKS = 10000;

  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    ;
    System.out.println("Press enter to Start");
    s.nextLine();
    System.out.printf("Running &d tasks\n", NUMBER_OF_TASKS);

    long start = System.currentTimeMillis();
    performTasks();
    System.out.printf("Tasks took %dms to complete\n", (System.currentTimeMillis() - start));
  }

  private static void performTasks() {
    try (ExecutorService executorService = Executors.newFixedThreadPool(1000)) {
      for (int i = 0; i < 100; i++) {
        executorService.submit(IoBoundApplication::blockingIoOperation);
      }
    }
  }

  private static void blockingIoOperation() {
    System.out.println("Executing a blocking task from thread  " + Thread.currentThread());
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

}
