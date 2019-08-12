package org.apache.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Array4 {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int testcasees = scanner.nextInt();
    for(int i=0;i<testcasees;i++) {
      int size = scanner.nextInt();
      int[] input = new int[size];
      for (int j = 0; j < size; j++) {
        input[j] = scanner.nextInt();
      }
      String result = getResult(input);
      System.out.println(result);
    }
    //int[] input=new int[]{5 ,5 ,4 ,6 ,4};
    /*int[] input=new int[]{9 ,9 ,9 ,2, 5};
    String result = getResult(input);
    System.out.println(result);
*/
  }

  private static String getResult( int[] input) {
    StringBuffer stringBuffer=new StringBuffer();
    Map<Integer, Integer> integerIntegerMap = converToMap(input);
    Set<Map.Entry<Integer, Integer>> entries = integerIntegerMap.entrySet();
    List<Map.Entry<Integer, Integer>> values=new ArrayList(entries);
    Collections.sort(values,new MyEntityCompar());
    for(Map.Entry<Integer, Integer> v:values){
      for(int i=0;i<v.getValue();i++){
        stringBuffer.append(v.getKey()).append(" ");
      }
    }
    return stringBuffer.toString();

  }

  private static  Map<Integer,Integer>  converToMap(int[] input) {
    Map<Integer,Integer> integerIntegerMap=new HashMap<>();
    for( int s:input){
      if(integerIntegerMap.containsKey(s)){
        Integer integer = integerIntegerMap.get(s);
        integerIntegerMap.put(s,++integer);
      }
      else {
        integerIntegerMap.put(s,1);
      }
    }
return integerIntegerMap;
  }
  static class MyEntityCompar implements Comparator<Map.Entry<Integer, Integer>>{

    @Override public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {

      int comparResult = -1 * o1.getValue().compareTo(o2.getValue());
      if(comparResult==0){
        return o1.getKey().compareTo(o2.getKey());
      }

      return comparResult;
    }
  }

}
