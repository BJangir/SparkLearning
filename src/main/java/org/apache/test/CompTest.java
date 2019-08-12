package org.apache.test;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Administrator on 9/5/2018.
 */
public class CompTest {
  public static void main(String[] args) {
    Emp[] emps=new Emp[10];
    emps[0]=new Emp(1,"n1");
    emps[1]=new Emp(10,"n1");
    emps[2]=new Emp(5,"n1");
    emps[3]=new Emp(9,"n1");
    emps[4]=new Emp(1,"n1");
    emps[5]=new Emp(16,"n1");
    emps[6]=new Emp(70,"n1");
    emps[7]=new Emp(20,"n1");
    emps[8]=new Emp(5,"n1");
    emps[9]=new Emp(7,"n1");

    Arrays.sort(emps,new EICompa());
    System.out.println("Default Sorting of Employees list:\n"+Arrays.toString(emps));
  }
}
