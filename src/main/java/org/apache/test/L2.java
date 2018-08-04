package org.apache.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.calcite.plan.Strong;


/**
 * Created by Administrator on 4/14/2018.
 */
public class L2 {
  public static void main(String[] args) {

    String mystring="xyyyyyyyyz";
    char x='x';
    char y='z';
    int mindistance=0;

    /* mindistance = getMindistance(mystring, x, y);
    mindistance = getMindistance(mystring, x, mindistance);
*/

      int[] arr=new int[]  {3, 5, 4, 2, 6, 5, 6, 6, 5, 4, 8, 3};
      int a=3;
      int b=6;
    mindistance= getlength(arr, a, b);


    System.out.println("Min distance is : "+(mindistance)+" ---"+Math.abs(1-9));


  }

  private static int getlength(int[] arr,int a,int b) {
    int start=-1;
    int end=-1;
    int min=-1;
  for(int i=0;i<arr.length;i++)
  {
    for(int j=i+1;j<arr.length;j++){
      if(arr[i]==a && arr[j]==b || (arr[j]==a && arr[i]==b) && min> Math.abs(i-j)){
        min=Math.abs(i-j);
      }
    }
  }
  return min;

  }


  private static int getMindistance(String mystring, char x, int mindistance) {
    int start=-1;
    int end=-1;
    for(int i=0;i<mystring.length();i++){

      if((start >-1) && (end>-1)){
        int localmin = end - start;
        if(mindistance==-1){
          mindistance=localmin;
        }
        if(localmin<=mindistance){
          mindistance=localmin;
        }
        if(mindistance ==1){
          break;
        }
      }
      if(mystring.charAt(i) == x){
        start=i;
      }

    }
    return mindistance;
  }

  private static int getMindistance(String mystring, char x, char y) {
    System.out.println(mystring.indexOf(y)-mystring.indexOf(x));

    int start=-1;
    int end =mystring.indexOf(y);
    System.out.println(end);
    int mindistance=-1;

    for (int i=end;i<0;i--){
      if(mystring.charAt(i) == x){
        start=i;
      }
    }
    mindistance=end-start;

    StringBuffer stringBuffer=new StringBuffer(mystring.substring(0,mystring.indexOf(y)));
    start=stringBuffer.reverse().indexOf(x+"");
    System.out.println("Wwww"+start);

    char[] chars = mystring.toCharArray();
    Arrays.sort(chars);
    int startnew=-1;
    int endnew=-1;
    for(int i=0;i<chars.length;i++){
      if(startnew >-1 && endnew >-1){
        break;
      }
      if(chars[i]==x){
startnew=i;
      }
      if(chars[i]==y){
        endnew=i;
      }

    }
    System.out.println("new"+(endnew-startnew));
    return mindistance;
  }
}
