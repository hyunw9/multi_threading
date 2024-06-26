package advancedLocking.reentrantLock;


import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {

  /*
   * getQueuedThreads() - 락을 얻기위해 대기중인 스레드를 반환
   * getOwner() - 현재 락을 가진 스레드를 반환
   * isHeldByCurrentThread() - 락이 현재 스레드에 의해 점유중인지 질의. 있으면 참 반환
   * isLocked() 락이 다른 스레드에 의해 잠겼는지 질의 . 잠겼으면 참 반환
   *
   * TryLock () - 락 획득시 참 리턴, 못 얻을 시 , False 반환 . if 문으로 제어 가능
   * - 스레드를 막지 않는다.
   */

  public static class PricesContainer {

    private Lock lockObject = new ReentrantLock();

    private double bitcoinPrice;
    private double etherPrice;
    private double litecoinPrice;
    private double bitcoinCashPrice;
    private double ripplePrice;

    public Lock getLockObject() {
      return lockObject;
    }

    public void setLockObject(Lock lockObject) {
      this.lockObject = lockObject;
    }

    public double getBitcoinPrice() {
      return bitcoinPrice;
    }

    public void setBitcoinPrice(double bitcoinPrice) {
      this.bitcoinPrice = bitcoinPrice;
    }

    public double getEtherPrice() {
      return etherPrice;
    }

    public void setEtherPrice(double etherPrice) {
      this.etherPrice = etherPrice;
    }

    public double getLitecoinPrice() {
      return litecoinPrice;
    }

    public void setLitecoinPrice(double litecoinPrice) {
      this.litecoinPrice = litecoinPrice;
    }

    public double getBitcoinCashPrice() {
      return bitcoinCashPrice;
    }

    public void setBitcoinCashPrice(double bitcoinCashPrice) {
      this.bitcoinCashPrice = bitcoinCashPrice;
    }

    public double getRipplePrice() {
      return ripplePrice;
    }

    public void setRipplePrice(double ripplePrice) {
      this.ripplePrice = ripplePrice;
    }
  }

  public static class PriceUpdate extends Thread {

    private PricesContainer pricesContainer;
    private Random random = new Random();

    public PriceUpdate(PricesContainer pricesContainer) {
      this.pricesContainer = pricesContainer;
    }

    @Override
    public void run() {

      while (true) {
        pricesContainer.getLockObject().lock();

        try {
          Thread.sleep(1000); //network call
          pricesContainer.setBitcoinPrice(random.nextInt(20000));
          pricesContainer.setEtherPrice(random.nextInt(2000));
          pricesContainer.setLitecoinPrice(random.nextInt(500));
          pricesContainer.setBitcoinCashPrice(random.nextInt(5000));
          pricesContainer.setRipplePrice(random.nextDouble());
        } catch (InterruptedException e) {

        } finally {
          pricesContainer.getLockObject().unlock();
        }
      }
    }
  }

}
