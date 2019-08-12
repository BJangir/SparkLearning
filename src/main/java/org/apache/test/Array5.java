package org.apache.test;

import java.util.Arrays;
import java.util.Scanner;

public class Array5 {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int testcasees = scanner.nextInt();
    for(int i=0;i<testcasees;i++) {
      int size = scanner.nextInt();
      int[] input = new int[size];
      for (int j = 0; j < size; j++) {
        input[j] = scanner.nextInt();
      }
      int kthElement= scanner.nextInt();
      int result=getKthSmallest(input,kthElement);
      System.out.println(result);
    }


    /*int[] arr=new int[]{7, 10, 4 ,20 ,15};
    int kthElement=4;
    int result=getKthSmallest(arr,kthElement);
    System.out.println(result);*/



  }

  private static int getKthSmallest(int[] arr,int index) {
    Arrays.sort(arr);
    return arr[index-1];
  }
}
