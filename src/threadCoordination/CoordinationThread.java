package threadCoordination;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CoordinationThread {

  public static void main(String[] args) throws InterruptedException {
    List<Long> inputNumbers = Arrays.asList(10000000000000L, 3435L, 35435L , 2324L, 4656L, 23L, 5556L);

    List<FactorialThread> threads = new ArrayList<>();

    for(long i : inputNumbers) {
      threads.add(new FactorialThread(i));
    }

    for(Thread thread : threads) {
      //Main 종료시 종료되도록
      thread.setDaemon(true);

      thread.start();
    }

    for(Thread thread : threads) {
      //한 계산당 2 초가 넘지 않도록.
      //넘을시 반환
      thread.join(2000);
    }

    for(int i = 0 ; i < inputNumbers.size(); i++){
      FactorialThread factorialThread = threads.get(i);
      if(factorialThread.isFinished()){
        System.out.println("팩토리얼 "+ inputNumbers.get(i)+ " is"+ factorialThread.getResult());
      }else{
        System.out.println("계산식 " + inputNumbers.get(i)+ " is"+ " 아직 실행중");
      }
    }
  }

  public static class FactorialThread extends Thread{

    private long inputNumber;
    private BigInteger result = BigInteger.ZERO;
    private boolean isFinished = false;

    public FactorialThread(long inputNumber) {
      this.inputNumber = inputNumber;
    }

    @Override
    public void run(){
      this.result = factorial(inputNumber);
      this.isFinished = true;
    }

    public BigInteger factorial(long number){

      BigInteger tempResult = BigInteger.ONE;

      for(long i = number ; i > 0 ; i--){
        tempResult = tempResult.multiply(new BigInteger(Long.toString(i)));
      }
      return tempResult;
    }

    public boolean isFinished(){
      return this.isFinished;
    }

    public BigInteger getResult(){
      return result;
    }
  }

}
