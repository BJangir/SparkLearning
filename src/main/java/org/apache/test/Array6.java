package org.apache.test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

import org.apache.spark.sql.sources.In;

public class Array6 {

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

    int[] arr=new int[]{8 ,8 ,2 ,4 ,5 ,5 ,1};
    int result=getResult(arr,0,arr.length);
    System.out.println(result);



  }

  private static int getResult(int[] arr,int start,int end){
    int lenth = arr.length;
    int max = -1;
    int scnMax = -1;
    int total=-1;
    for (int i = start; i < end; i++) {
      if(i==start){
        scnMax=arr[i];
        max=arr[i];
        continue;
      }

      if (arr[i] > max) {
        scnMax=max;
        max = arr[i];
        int r=geTotalTilNow(arr,i,scnMax);
        total=total+r;
        scnMax=max;
        return total+getResult(arr,i,arr.length);
      }
      if(scnMax==max){
        scnMax=arr[i];
      }
      if(arr[i]>scnMax){
        scnMax=arr[i];
      }
    }
    if(total==-1){
      int r=geTotalTilNow(arr,end-1,scnMax);
      return r;
    }
    return total;
  }

  private static int geTotalTilNow(int[] arr, int i,int scnMax) {
    int sum=0;
    for(int j=0;j<=i;j++){
      int r1=scnMax-arr[j];
      if(r1>0){
        sum=sum+r1;
      }
    }
    return sum>0?sum:0;
  }

}
