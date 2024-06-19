package raceCondition;

public class ConditionMain {

  public static void main(String[] args) {
    InventoryCounter inventoryCounter = new InventoryCounter();
    IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter);
  }

  public static class IncrementingThread extends Thread {

    private InventoryCounter inventoryCounter1;

    public IncrementingThread(InventoryCounter inventoryCounter) {
      this.inventoryCounter1 = inventoryCounter;
    }

    @Override
    public void run() {
      for (int i = 0; i < 10000; i++) {
        inventoryCounter1.increment();
      }
    }
  }

  public static class DecrementingThread extends Thread {

    private InventoryCounter inventoryCounter;

    public DecrementingThread(InventoryCounter inventoryCounter) {
      this.inventoryCounter = inventoryCounter;
    }

    @Override
    public void run() {
      for (int i = 0; i < 10000; i++) {
        inventoryCounter.decrement();
      }
    }
  }

  private static class InventoryCounter {

    Object lock = new Object();
    private int items = 0;

    public void increment() {
      synchronized (this.lock) {
        this.items++;
      }
    }

    public void decrement() {
      Object object = new Object();
      synchronized (this.lock) {
        this.items--;
      }
    }

    public synchronized int getItems() {
      return items;
    }
  }

}
