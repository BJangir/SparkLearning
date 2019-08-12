package org.apache.test;

import java.io.IOException;

public class MyServerClient {

  public static void main(String[] args) throws IOException {
    OKServer okServer=new OKServer();
    Emp emp=new Emp();
    emp.setName("babu");
    emp.setMname("lal");
    emp.setAge(23);
    Dep dep=new Dep();
        dep.setName("Dep1");
        dep.setMname("NDp1");

    MyServer client = okServer.getClient();
    String babu = client.test(emp,dep);
    System.out.println("Value is - "+babu);

  }
}
