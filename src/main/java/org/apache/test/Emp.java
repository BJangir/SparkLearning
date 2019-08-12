package org.apache.test;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;

import org.apache.hadoop.io.Writable;

public class Emp implements Writable,Data {
  String name;
  String mname;
  int age;

  public String getMname() {
    return mname;
  }

  public void setMname(String mname) {
    this.mname = mname;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  @Override public void write(DataOutput out) throws IOException {
    out.writeUTF(name);
    out.writeUTF(mname);
  }

  @Override public void readFields(DataInput in) throws IOException {
    name=in.readUTF();
    mname=in.readUTF();
    System.out.println(name);
  }
}
