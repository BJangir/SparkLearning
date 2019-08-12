package org.apache.test;

import java.util.Scanner;

public class Array8 {

  //Equilibrium point
  public static void main(String[] args) {

    /*Scanner scanner = new Scanner(System.in);
    int testcasees = scanner.nextInt();
    for(int i=0;i<testcasees;i++) {
      int size = scanner.nextInt();
      int[] input = new int[size];
      for (int j = 0; j < size; j++) {
        input[j] = scanner.nextInt();
      }
      int result=getResult(input);
      System.out.println(result);
    }*/

    long time=System.nanoTime();
    int[] aa = {33 ,33 ,26 ,7 ,19 ,23, 8, 43};
    int i = getResult(aa);
    System.out.println(i);


  }


  private static int getResult(int[] aa) {
    if(aa.length==1){
      return 1;
    }
    //int rightSum=getRightSum(aa,0);
    int sum=0;
    for (int r =  0; r < aa.length; r++) {
      sum = sum + aa[r];
    }
    int leftSum=0;
    for(int i=1;i<aa.length;i++){
      leftSum=leftSum+aa[i-1];
      int rightSum=(sum-aa[i]);
      if(rightSum%2==0){
        if(leftSum*2==rightSum){
          return i+1;
        }
      }

    }
return -1;
  }

  private static int getLeftSum(int[] aa, int i) {
    int sum = 0;
    for (int l = 0; l < i; l++) {
      sum = sum + aa[l];
    }
    return sum;
  }

  private static int getRightSum(int[] aa, int i) {
    int sum = 0;
    for (int r = i + 1; r < aa.length; r++) {
      sum = sum + aa[r];
    }
    return sum;
  }

}
