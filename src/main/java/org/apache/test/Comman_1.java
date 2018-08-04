package org.apache.test;

import java.util.BitSet;

/**
 * Created by Administrator on 7/22/2018.
 */
public class Comman_1 extends Comman_2{

  public static void main(String[] args) {
    System.out.println("ss"+Integer.toBinaryString(8).length());
    String s ="10001";
    BitSet bitSet = BitSet.valueOf(new long[] { Long.parseLong(s) });
    System.out.println(bitSet);

    BitSet set=new BitSet();
    set.set(5);
    System.out.println(set);
    Comman_1 com=new Comman_1();
    com.myconnon();
  }

  public void myconnon(){
    System.out.println("I am in MyCommmon1");
  }

  public  int foo(int a) {
    System.out.println("Test.foo() called ");
    return 0;
  }
  public void foo() { // Compiler Error: cannot redefine foo()
    System.out.println("Test.foo(int) called ");
  }

}
class Comman_2{
  public void myconnon(){
    System.out.println("I am in MyCommmon2");
  }
}
