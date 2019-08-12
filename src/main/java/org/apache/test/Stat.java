package org.apache.test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class Stat {
  public static void main(String[] args) throws IOException {
    Configuration c=new Configuration();
    c.set("fs.default.name","file:///");
    String base_path="D:/ss/av1/2/";
    File f=new File(base_path);
    String[] list = f.list();
for(String fil:list){
  //Path input=new Path("file:///D:/ss/csv/1/part-00000-bd14bc4d-4bc9-470c-85c9-e72e4544689b-c000");
  Path input=new Path("file:///"+base_path+fil);
  FileSystem fs = FileSystem.get(c);
  InputStream is = fs.open(input);
  while(is.read()!=-1){
    int s=is.read();
  }
  is.close();
  //OutputStream os = new BufferedOutputStream(new FileOutputStream("D:/ss/y.csv"));
}
    //IOUtils.copyBytes(is, os, c);

    List<FileSystem.Statistics> allStatistics = FileSystem.getAllStatistics();
    long sum=0;
    for(FileSystem.Statistics statistics:allStatistics){
      long v=statistics.getThreadStatistics().getBytesRead();
      System.out.println("Value is "+v);
      sum=sum+v;
    }
    System.out.println("Total is "+sum);


  }
}
