package org.apache.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Array1 {

  public static void main(String[] args) {
/*    Scanner scanner = new Scanner(System.in);
    int testcasees = scanner.nextInt();
    for(int i=0;i<testcasees;i++){
      int size = scanner.nextInt();
      int[] input=new int[size];
      for(int j=0;j<size;j++){
        input[j]=scanner.nextInt();
      }
      String result = getResultString(input);
      System.out.println(result);*/
    /*String maxValueByString = getMaxValueByString(15, 153);
    System.out.println(maxValueByString);*/
      int[] arr=new int[]{624,421,145,969,736,916,626,535,43,12,680,153,245,296,819,397,693,816,992,34,670,398,554,548,826,211,663,212,809,378,762,626,336,869,996,777,768,440,875,332,557,302,873,561,95,985,756,790,408,16,194,770,681,456,856,507,964,503,677,109,250,332,845,639,809,998,652,850,204,732,532,15,420,776,10,181,930,224,55,261,738,546,318,526,201,257
      };
    List<String> strings = converToStringArray(arr);
    String result = getResult(strings);
    //String result = getResultString(arr);
      System.out.println(result);
    }
    private static List<String> converToStringArray(int[] arr){
      List<String> vv=new ArrayList<>();
      for (int a:arr){
        vv.add(a+"");
      }
      return vv;
    }

    private static  String getResult(List<String> myArr){

      Collections.sort(myArr,new CustomComp());
      StringBuffer stringBuffer=new StringBuffer();
      for(String s:myArr){
        stringBuffer.append(s);
      }
      return stringBuffer.toString();

    }
   private static class CustomComp implements Comparator<String>{

      @Override public int compare(String o1, String o2) {
        return -1*(Integer.compare(Integer.parseInt(o1+o2),Integer.parseInt(o1+o2+1)));
      }
    }

  private static String getResultString(int[] arr) {
    short highestDegre = getHighestDe(arr);
    int lenth=arr.length;
    boolean[] done=new boolean[arr.length];
    StringBuffer stringBuffer=new StringBuffer();
    int index=0;
    int maxIndex=0;
    int max=0;
    while (lenth!=0) {
      if (!done[index]) {
        int v  =  (arr[index]);
        short highestDeForElement = getHighestDeForElement(v);
        int digits = highestDegre - highestDeForElement;
        /*int newValue = getMaxValue(v, highestDeForElement, digits,max);*/
        int newValue=Integer.parseInt(getMaxValueByString(v,max));
        if(newValue!=max){
          max=newValue;
          maxIndex=index;
        }
      }
      index++;
      if (index == arr.length) {
        done[maxIndex]=true;
        stringBuffer.append(arr[maxIndex]);
        max=0;
        maxIndex=0;
        index = 0;
        lenth--;
      }
    }
return stringBuffer.toString();
  }

  private static String getMaxValueByString(int v, int max) {
    String s1=v+"";
    String s2=max+"";
    String[] re=new String[]{s1,s2};
    String[] re1=new String[]{s1,s2};
    Arrays.sort(re,Collections.reverseOrder());
    if(re[0].equalsIgnoreCase(re1[0])){
      return s1;
    }
    else{
      return s2;

    }




  }

  private static int getMaxValue(int v, short highestDeForElement, int digits,int max) {
    try{
      if(max==0){
        return v;
      }
      short maxElementDe = getHighestDeForElement(max);
      int digitsMax = (highestDeForElement+digits) - maxElementDe;
      int multi_max=1;
      int multi=1;
      if(digits!=0 || digitsMax!=0){

        if(maxElementDe<highestDeForElement){
          int t=v;
          v=max;
          max=t;
          short tHD=highestDeForElement;
          highestDeForElement=maxElementDe;
          maxElementDe=tHD;
          int tDegDre=digits;
          digits=digitsMax;
          digitsMax=tDegDre;

        }

        if(digitsMax!=0){
          multi_max=(int) Math.pow(10,digitsMax);
          max=max*multi_max;
        }
        if(digits!=0){
          multi=(int) Math.pow(10,digits);
          v=v*multi;
        }

        String valueString=v+"";
        String maxString=max+"";
        for (int i=0;i<highestDeForElement;i++){
          byte vbyte= Byte.parseByte(valueString.charAt(i)+"");
          byte mbyte= Byte.parseByte(maxString.charAt(i)+"");
          if(vbyte!=mbyte){
            if (vbyte > mbyte) {
              return (v / multi);
            } else {
              return (max / multi_max);
            }
          }
        }
        if(highestDeForElement<maxElementDe){
          byte vbyte= Byte.parseByte(valueString.charAt(highestDeForElement-1)+"");
          for(int i=0;i<digits;i++){
            byte mbyte= Byte.parseByte(maxString.charAt(highestDeForElement+i)+"");
            if(vbyte>mbyte){
              return (v/multi);
            }
            if(mbyte>vbyte){
              return (max/multi_max);
            }
          }
          return (max/multi_max);
        }
      }

      if(v>max){
        return v;
      } else {
        return max;
      }
    } catch (Exception e){
      return v;
    }


  }

  private static  short getHighestDeForElement(int v){
    short currectUnit=0;
    while (true){
      try {
        String.valueOf(v).charAt(currectUnit);
        currectUnit++;
      } catch (Exception e){
        break;
      }
    }
    return currectUnit;
  }
  private static short getHighestDe(int[] arr) {
    short maxorder=0;
    for (int i=0;i<arr.length;i++){
      int v = arr[i];
      short currectUnit = getHighestDeForElement(v);
      if(currectUnit>maxorder){
        maxorder=currectUnit;
      }
    }
    return maxorder;
  }

  private static void t1(int[] arr) {
    boolean[] all=new boolean[arr.length];
    StringBuffer stringBuffer=new StringBuffer();
    int max=0;
    int  maxindex=0;
    int currectUnit=0;
    boolean unitlef=true;
    while (unitlef){
      int exCount=0;
      for (int i=0;i<arr.length;i++){
        if(!all[i]){
          int v = arr[i];
          try {
            v = Integer.parseInt(String.valueOf(v).charAt(currectUnit)+"");
            if(max>v){
              max=v;
              maxindex=i;
            }
          } catch (Exception e){
            exCount++;
          }
        }

      }
      if(exCount==arr.length){
        unitlef=false;
      }
      stringBuffer.append(arr[maxindex]);
      all[maxindex]=true;

    }

  }
}
