package org.apache.test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 7/22/2018.
 */
public class Basic {

  public static void main(String[] args) {
    //primeNo
//    printPrimeNo();
//    isPrimeNo(5);
//    printTenWithoutLoop(1);
//    reverStringSpecialNoChange();
    reverStringSpecialNoChange_1();
  }

  private static void reverStringSpecialNoChange_1() {
//    List<String> k=new ArrayList<>(5);
//    k.add("bab");
//    k.add("lal");
//    k.add(4,"La");
//    System.out.println(k);
    String str="Ab,c,de!$";
    int lenth=str.length();
    int counter=0;
    char[] chars = str.toCharArray();
    List<Character> sp=new ArrayList<Character>();
    sp.add('$');
    sp.add(',');
    sp.add('!');
    List<Character> resultList=new ArrayList<>(str.length());
    List<Integer> spList=new ArrayList<>();
    for(int i=lenth-1;i>=0;i--){
      if((sp.contains(str.charAt(i)))){
        spList.add(i);
      }
      else {
        resultList.add(str.charAt(i));
      }
    }
    if(resultList.size()!=str.length()){
      for(int i=resultList.size();i<str.length();i++){
        resultList.add(' ');
      }
    }

    for(int i=0;i<spList.size();i++){
      resultList.add(spList.get(i),str.charAt(spList.get(i)));
    }
    System.out.println("----------"+resultList);

  }

  private static void reverStringSpecialNoChange() {
    String str="a,b$c";
    //ed,c,bA!$
    //c$b,a
    String output="";
    List<Character> sp=new ArrayList<Character>();
    sp.add('$');
    sp.add(',');
    sp.add('!');
    //c,b$a
    // get
    int length=str.length();
    List<Integer> spList=new ArrayList<>();
    char[] result=new char[str.length()];
    List<Character> resultList=new ArrayList<>();
     for(int i=length-1;i>=0;i--){
      if(sp.contains(str.charAt(i))){
        spList.add(i);
      }
      else {
        resultList.add(str.charAt(i));
      }
     }

     List<Character> finakList=new ArrayList<>();
    int j=0;
     for(int i=0;i<str.length();i++){
         if(spList.contains(i)){
           finakList.add(str.charAt(i));
         }
         else {
           finakList.add(resultList.get(j));
           j++;
         }
     }

    System.out.println(finakList.toString());




  }

  private static void printTenWithoutLoop(int n) {
    if(n<=10){
      System.out.println(n);
      printTenWithoutLoop(n+1);
    }
  }

  private static void isPrimeNo(int n) {
    boolean isPrime=true;
    for(int i=2;i<=n/2;i++){
      if(n%i==0){
        isPrime=false;
        break;
      }
    }
    System.out.println("No. "+n+" is "+isPrime);

  }

  private static void printPrimeNo() {
    int num=10;
    for(int  i=2;i<=num;i++){
      int count=0;
      for(int j=2;j<=i/2;j++){
        if(i%j==0){
          count++;
          break;
        }
      }
 if(count==0){
   System.out.println(i);
 }

    }
  }
}
