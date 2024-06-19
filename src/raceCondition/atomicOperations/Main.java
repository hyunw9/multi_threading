package raceCondition.atomicOperations;

import java.util.Random;

public class Main {

  public static void main(String[] args) {
    Metrics metrics = new Metrics();

    BusinessLogic businessLogicThread1 = new BusinessLogic(metrics);

    BusinessLogic businessLogicThread2 = new BusinessLogic(metrics);

    MetricsPrinter metricsPrinter = new MetricsPrinter(metrics);

    businessLogicThread1.start();
    businessLogicThread2.start();
    metricsPrinter.start();
  }

  public static class MetricsPrinter extends Thread {

    private Metrics metrics;

    public MetricsPrinter(Metrics metrics) {
      this.metrics = metrics;
    }

    @Override
    public void run() {
      while (true) {
        try {
          Thread.sleep(1);
        } catch (InterruptedException e) {
        }

        double currentAverage = metrics.getAverage();

        System.out.println("Current Average is " + currentAverage);
      }
    }
  }

  public static class BusinessLogic extends Thread {

    private Metrics metrics;
    private Random random = new Random();

    public BusinessLogic(Metrics metrics) {
      this.metrics = metrics;
    }

    @Override
    public void run() {
      while (true) {
        long start = System.currentTimeMillis();

        try {
          Thread.sleep(random.nextInt(2));
        } catch (InterruptedException e) {
        }

        long end = System.currentTimeMillis();

        metrics.addSample(end - start);
      }
    }
  }

  public static class Metrics {

    private long count = 0;
    private volatile double average = 0.0;
    // race condition
    public synchronized void addSample(long sample) {
      double currentSum = average * count;
      count++;
      average = (currentSum + sample) / count;
    }
    //getter, setter 는 원자적임 , but long,double 은 Volitale 이 있어야
    //원자성 보장
    public double getAverage() {
      return average;
    }
  }
}


