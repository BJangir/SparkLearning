package org.apache.test;

import java.util.Scanner;

public class Array3 {

  public static void main(String[] args) {
   /* Scanner scanner = new Scanner(System.in);
    int testcasees = scanner.nextInt();
    for(int i=0;i<testcasees;i++) {
      int size = scanner.nextInt();
      String[] input = new String[size];
      for (int j = 0; j < size; j++) {
        input[j] = scanner.next();
      }
      String result = getResult(input);
      System.out.println(result);
    }*/

   /*int[] arr=new int[]{768,822,696,713,672,902,591,832,739,58,
       617,791,641,680,336,7,973,99,96,320,455,224,290,761,906,127,124,
       507,814,771,239,95,221,845,367,535,227,395,364,739,845,591,551,160,624,
       948,386,218,273,540,248,386,497};*/
   int[] arr=new int[]{891,885,814,442,128,180,785,538,871,562,582,166,803,733,333,855,760,848,378,463,11,820,151,378,942,837,721,300,113,760,957,391,153,49,15,45,919,151,102,296,822,732,502,246,962,58,511,929,806,174,138,670,97,504,422,676,519,301,490,263,55,264,644,890,251
   };


    //9534330
    String[] aa = convertToStringArray(arr);

    // Arrays.sort(arr);
    //Arrays.sort(aa, Collections.<String>reverseOrder());
    String result = getResult(aa);
    System.out.println(result);

 /*   for(int s:arr){
      System.out.println(s);
    }*/
    /*System.out.println("=========");
    for(String s1:aa){
      System.out.println(s1);
    }*/

  }

  private static String getResult(String[] aa) {
    StringBuffer stringBuffer=new StringBuffer();
    int compl=0;
    for(int j=0;j<aa.length;j++){
      compl++;
      String max="";
      int index=0;
      for(int i=0;i<aa.length;i++){
        if(aa[i].isEmpty()){
          continue;
        }
        compl++;
          if(max.isEmpty()){
            max=aa[i];
            index=i;
            continue;
          }
          String current=aa[i];
          int s1Lenth=max.length();
          int s2Lenth=current.length();
          String temp="";
        if(s1Lenth>s2Lenth){
            //temp = giveCorrectMaxData(max, current);
          temp = giveCorrectMaxData_1(max, current);
        } else {
           // temp = giveCorrectMaxData(current, max);
          temp = giveCorrectMaxData_1(current, max);
          }
          if(!temp.equalsIgnoreCase(max)){
            index=i;
            max=temp;
          }
      }

      stringBuffer.append(aa[index]);
      aa[index]="";

    }
    System.out.println("Compl: "+compl);
   return  stringBuffer.toString();

  }

  private static String giveCorrectMaxData_1(String o1, String o2) {
    String opt1=o1+o2;
    String opt2=o2+o1;

    long res=Long.parseLong(opt2)-Long.parseLong(opt1);
    if(res>0){
      return o2;
    }
    else {
      return o1;
    }
  }

  private static String giveCorrectMaxData(String o1, String o2) {
     int o1Lenth= o1.length();
     int o2Lenth=o2.length();
     int i=0;
     for (;i<o2Lenth;i++){
       char o1Char=o1.charAt(i);
       char o2Char=o2.charAt(i);
       if(o1Char>o2Char){
        return o1;
       }
       else if(o2Char>o1Char){
         return o2;
       }
     }
     if(i==(o1Lenth)){
       return o1;
     }

    char o1FCha=o1.charAt(0);
    for(int j=i;j<o1Lenth;j++){
      char o2FCha=o1.charAt(j);
      if(o1FCha==o2FCha){
        continue;
      }
      if(o1FCha>o2FCha){
        return o2;
      }
      return o1;
    }

     return o1;
  }

  private static String[] convertToStringArray(int[] arr) {
    String[] a = new String[arr.length];
    for (int i = 0; i < arr.length; i++) {
      a[i] = arr[i] + "";
    }
    return a;
  }
}
