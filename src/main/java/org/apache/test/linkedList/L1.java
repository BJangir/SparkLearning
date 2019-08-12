package org.apache.test.linkedList;

import java.nio.ByteBuffer;

/**
 * Created by Administrator on 8/25/2018.
 */
public class L1 {

  public static void main(String[] args) {
    int b=123;
    byte g=100;
    ByteBuffer buffer=ByteBuffer.allocate(5);
    buffer.putInt(b);
    buffer.put(g);
    System.out.println(buffer);
    ByteBuffer wrap = ByteBuffer.wrap(buffer.array());
    System.out.println(wrap.get());
    System.out.println(wrap.get());
  }
}
