package org.apache.test;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 7/22/2018.
 */
public class Basic {

  public static void main(String[] args) throws IOException {
    //primeNo
//    printPrimeNo();
//    isPrimeNo(5);
//    printTenWithoutLoop(1);
//    reverStringSpecialNoChange();
//    reverStringSpecialNoChange_2();
//    findLargestElemtinAray();
   // pritOccurence_1();
    //lcs();
   // logClaculate();
    WriterFileWithByte();
    //sorting();
  }

  private static void sorting() {

  }

  private static void WriterFileWithByte() throws IOException {
    File file=new File("D:/data/customFile_string.txt");
    OutputStream outputStream=new FileOutputStream(file);
    String s="babu";
    /*byte[] bytes1 = s.getBytes();
    byte[] bytes=new byte[32];
    outputStream.write(bytes1);
    outputStream.close();*/
    int j=Integer.MAX_VALUE;
    int i=0x10;
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(bos);
    dos.writeInt(j);
    dos.flush();
    byte[] bytes = bos.toByteArray();
   // outputStream.write(bytes);

    ByteBuffer allocate = ByteBuffer.allocate(8);
    allocate.putInt(j);
    allocate.putInt(j);
    byte[] array = allocate.array();
    outputStream.write(array);


    //change int to byteArray
    System.out.println(Integer.toBinaryString(256)+" --> "+(256&0xff));

    // Byte Array to Int
    ByteBuffer wrap = ByteBuffer.wrap(array);
    int anInt = wrap.getInt();

    System.out.println(anInt+"->"+wrap.getInt());

    //file Content
    byte fileContent[] = new byte[(int)file.length()];
    InputStream inputStream=new FileInputStream(file);
    int read = inputStream.read(fileContent);
    System.out.println(read);
    System.out.println(new String(fileContent));

    //
    int x=128;
    int y=30;
    byte a=3;

    System.out.println(Integer.toBinaryString(x)+" -->"+(x&0xff) +" -->"+(byte)x+" -->"+Integer.toBinaryString(0xFF)+"-->"+(Integer.toBinaryString(~x)+"-->")+"-->"+((~x)+1));
    System.out.println(Integer.toBinaryString(y)+"-->"+(y&0xff) +"-->"+(byte)y+"-->"+y);
    System.out.println(Integer.parseUnsignedInt("1010101",2));
    System.out.println(x + "->"+(~x)+"-->"+Integer.toBinaryString(x)+"-->"+Integer.toBinaryString(~x)+"-->"+Integer.parseInt("01001101",2));
    System.out.println(a+"-->"+(int)a+"-->"+Integer.toBinaryString(a&0xFF)+"-->"+(int)(a&0xFF)+"--> "+Integer.toBinaryString(a)+"-->"+(a^2));

    for( int ii=0;ii<5;ii++){
      System.out.println(ii+"-->"+Integer.toBinaryString(2)+"-->"+Integer.toBinaryString(ii)+"-->"+Integer.toBinaryString((2^ii))+"-->"+(2^ii)+"-->"+(~(-7)));
    }
  }

  private static void logClaculate() {
    for(int i=0;i<=10;i++){
      System.out.println(i+"----"+Math.log10(i)+"---"+Math.log10(2) +" Divide "+Math.log10(i)/Math.log10(2));
    }
  }

  private static void lcs() {
    String s1="abcdefghi";
    String  s2="ecdgi";
    char[] ch1 = s1.toCharArray();
    char[] ch2 = s2.toCharArray();
    String result="";
    for(int k=0;k<ch2.length;k++) {
      String temp = "";
      int lastMatch=-1;
      for (int i = k; i < ch2.length; i++) {
        for (int j = 0; j < ch1.length; j++) {
          if (ch2[i] == ch1[j] && (lastMatch==-1 || j > lastMatch)) {
            temp = temp + ch2[i];
            lastMatch=j;
            break;
          }
        }
      }
      if(temp.length()>=result.length()){
        System.out.println("SubString is "+temp +" size is "+temp.length());
        result=temp;
      }
    }
    System.out.println("LCS is "+result +" size "+result.length());




  }

  private static void pritOccurence() {
    String str="apple";
    for( int i=0;i<str.length();i++){
      int counter=0;
      boolean alreadycount=false;
      for(int k=0;k<i;k++){
        if(str.charAt(k)==str.charAt(i)){
          alreadycount=true;
          break;
        }
      }
 if(alreadycount){
        continue;
 }
      for(int j=i;j<str.length();j++){
        if(str.charAt(i)==str.charAt(j)){
          counter++;
        }
      }
      System.out.println(str.charAt(i)+" ---> "+counter);
    }
  }

  private static void pritOccurence_1() {
    String str="apple";
    int[] count=new int[256];
     int max=0;
    for( int i=0;i<str.length();i++){
      int counter=0;
      int mychar = (int) str.charAt(i);
      count[mychar]=count[mychar]+1;
      if(mychar > max){
        max=mychar;
      }
    }
    for(int i=0;i<=max;i++){
      if(count[i]>0){
        System.out.println((char) i +"-->"+count[i]);
      }
    }

  }

  private static void findLargestElemtinAray() {

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
  private static void reverStringSpecialNoChange_2() {
    //    List<String> k=new ArrayList<>(5);
    //    k.add("bab");
    //    k.add("lal");
    //    k.add(4,"La");
    //    System.out.println(k);
    String str="Ab,c,de!$";
    int lenth=str.length();
    int counter=0;
    char[] chars = str.toCharArray();
     int i=0;
     int j=chars.length-1;
    for( ;i<lenth/2;){

      if(Character.isLetter(chars[i]) && Character.isLetter(chars[j])){
        //swap
        char a=chars[i];
        chars[i]=chars[j];
        chars[j]=a;
        i++;
        j--;
      }
      if(Character.isLetter(chars[i]) && !Character.isLetter(chars[j])){
        j--;
      }
      if(!Character.isLetter(chars[i]) && Character.isLetter(chars[j])){
        i++;
      }

      if(!Character.isLetter(chars[i]) && !Character.isLetter(chars[j])){
        i++;
        j--;
      }

    }

    System.out.println("----------"+new String(chars));

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

  private void longestPalindromicSeq(String str){


  }
}
