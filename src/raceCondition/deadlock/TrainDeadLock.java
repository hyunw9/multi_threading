package raceCondition.deadlock;

import java.util.Random;

public class TrainDeadLock {

  /*
   * No preemption , Circular wait, Mutual exclusion, hold and wait
   * 네가지 요건이 만족될 경우, 데드락이 발생할 수 있다.
   * 네가지 중 하나라도 만족하지 못할 경우, 발생하지 않는데, 이 떄 개발자가 해결하기 가장 쉬운 방식은 순환 대기를 끊는 것
   * 순환대기를 끊는다는 것은, 락킹 순서를 동일하게 유지해줄 경우 끊을 수 있다.
   * 기존은 T1 : A-> B  T2 : B->A 순으로 락킹 했다면, T1 : A-> B , T2: A->B로 순서를 변경하면 순환대기를 끊을 수 있다.
   */

  public static void main(String[] args) {

    Intersection intersection = new Intersection();
    Thread trainAThread = new Thread(new TrainA(intersection));
    Thread trainBThread = new Thread(new TrainB(intersection));

    trainAThread.start();
    trainBThread.start();
  }

  public static class TrainA implements Runnable {

    private Intersection intersection;
    private Random random = new Random();

    public TrainA(Intersection intersection) {
      this.intersection = intersection;
    }

    @Override
    public void run() {
      while (true) {
        long sleepingTIme = random.nextInt(5);
        try {
          Thread.sleep(sleepingTIme);
        } catch (InterruptedException e) {

        }
        intersection.takeRoadA();
      }
    }
  }

  public static class TrainB implements Runnable {

    private Intersection intersection;
    private Random random = new Random();

    public TrainB(Intersection intersection) {
      this.intersection = intersection;
    }

    @Override
    public void run() {
      while (true) {
        long sleepingTIme = random.nextInt(5);
        try {
          Thread.sleep(sleepingTIme);
        } catch (InterruptedException e) {

        }
        intersection.takeRoadB();
      }
    }
  }

  public static class Intersection {

    private Object roadA = new Object();
    private Object roadB = new Object();

    public void takeRoadA() {
      synchronized (roadA) {
        System.out.println("Road A is locked by Thread" + Thread.currentThread().getName());

        synchronized (roadB) {
          System.out.println("Train is passing through road A ");

          try {
            Thread.sleep(1);
          } catch (InterruptedException e) {
          }
        }
      }
    }

    public void takeRoadB() {
      // locking 해제 순서를 일치시키면 데드락 발생 피할 수 있음.

      synchronized (roadA) {
        System.out.println("Road B is locked by Thread" + Thread.currentThread().getName());

        synchronized (roadB) {
          System.out.println("Train is passing through road B");
          try {
            Thread.sleep(1);
          } catch (InterruptedException e) {

          }
        }
      }
    }
  }

}
