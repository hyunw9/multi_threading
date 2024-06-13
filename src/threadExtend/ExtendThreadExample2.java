package threadExtend;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExtendThreadExample2 {

  public static final int MAX_PASSWORD = 9999;

  public static void main(String[] args) {
    Random random = new Random();
    Vault vault = new Vault(random.nextInt(MAX_PASSWORD));

    List<Thread> threads = new ArrayList<>();

    threads.add(new AscendingHackerThread(vault));
    threads.add(new DescendingHackerThread(vault));
    threads.add(new PoliceThread());

    for(Thread thread : threads) {
      thread.start();
    }
  }

  private static class Vault{

    private final int pwd;

    public Vault(int password) {
      this.pwd = password;
    }

    public boolean isCorrectPassword(int password) {
      try{
        Thread.sleep(5);

      } catch (InterruptedException e) {
      }
      return pwd == password;
    }
  }

  public static abstract class HackerThread extends Thread{

    protected Vault vault;

    public HackerThread(Vault vault) {
      this.vault = vault;
      this.setName(this.getClass().getSimpleName());
      this.setPriority(Thread.MAX_PRIORITY);
    }
    @Override
    public void start(){
      System.out.println("스레드 시작 " + this.getName());
      super.start();
    }
  }

  private static class AscendingHackerThread extends HackerThread{

    public AscendingHackerThread(Vault vault) {
      super(vault);
    }

    @Override
    public void run(){
      for(int guess = 0 ; guess< MAX_PASSWORD; guess++){
        if(vault.isCorrectPassword(guess)){
          System.out.println(this.getName() + " 가 비밀번호를 맞췄습니다. " + guess );
          System.exit(0);
        }
      }
    }
  }

  public static class DescendingHackerThread extends HackerThread{

    public DescendingHackerThread(Vault vault) {
      super(vault);
    }

    @Override
    public void run(){
      for(int guess = MAX_PASSWORD; guess >0 ; guess--){
        if(vault.isCorrectPassword(guess)){
          System.out.println(this.getName() + "가 비밀번호를 맞췄습니다. ");
          System.exit(0);
        }
      }
    }
  }

  public static class PoliceThread extends Thread{

    @Override
    public void run(){
      for(int i = 10; i >0; i--){
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        System.out.println(i);
      }
      System.out.println("게임오버 해커가 잡혔습니다.");
      System.exit(0);
    }
  }
}
