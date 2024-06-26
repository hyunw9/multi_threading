package reference;

import java.util.concurrent.atomic.AtomicReference;

public class Main {

  public static void main(String[] args) {
    String oldName = "old name";
    String newName = "new name";

    AtomicReference<String> atomicReference = new AtomicReference<>(oldName);
//    atomicReference.set("custom");
    if(atomicReference.compareAndSet(oldName,newName)){
      //compareAndSet 호출시, Reference 안의 내용은 oldName과 같은지 비교 후
      // new Name 으로 변경한다.
      System.out.println(atomicReference.get());
    }else{
      //반약 false 일경우, 값이 다른 경우.
      System.out.println("아무일도 없음");
    }
  }

}
