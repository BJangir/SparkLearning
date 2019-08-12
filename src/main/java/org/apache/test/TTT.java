package org.apache.test;

public class TTT {

  public static void main(String[] args) {


    String sss=myTest("babu",1);
    System.out.println(sss);


  }

  private static String myTest(String babu,int i) {
    i++;
    if(babu.equalsIgnoreCase("babu14")){
      return "HOOOOO14"+myTest(babu+"->"+i,i);
    }
    if(i<10){
      return babu+i+" aa"+myTest("babu",i);
    }
    return "";
  }

}
