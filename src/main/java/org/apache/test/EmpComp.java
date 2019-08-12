package org.apache.test;

public class EmpComp<EmpComp> implements MyCompare {

  @Override public String myValue(String v) {

    return new String("EmpComp===="+v);
  }
}
