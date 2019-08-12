package org.apache.test;

import java.util.Scanner;

public class TT {

  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);
    int testcasees = scanner.nextInt();
    for(int i=0;i<testcasees;i++){
      int v1 = scanner.nextInt();
      int v2 = scanner.nextInt();
      int rquiredBit = getRquiredBit(v1^v2);
    System.out.println(rquiredBit);
    }

    /*int a=32512;
    int rquiredBit = getRquiredBit(52^4);
    System.out.println(rquiredBit);*/

  }

  private static int getRquiredBit(int a) {
    if(a==0){
      return 0;
    }
    int look = 1;
    if (a % 2 == 0) {
      for (int i = 0; i < Integer.MAX_VALUE; i++) {
        if ((a & look) == look) {
          return ((i + 1));
        }
        look=look*2;
      }
    }

     return 1;
  }
}
