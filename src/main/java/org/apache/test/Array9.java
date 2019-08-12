package org.apache.test;

public class Array9 {

  public static void main(String[] args) {
    int gcd = gcd(122, 144);
    System.out.println(gcd);
  }

  private static int gcd(int a,int b) {
    int till=Math.min(a,b);
    int last=1;
    for(int i=2;i<till;i++){
      if(a%i==0 && b%i==0){
        last=i;
      }
    }
    return last;
  }
}
