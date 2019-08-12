package org.apache.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 8/26/2018.
 */
public class SplitFiles {

  static  String  dest="D:/tpch_data/tpch/5gb_200K/";
  public static void main(String[] args) throws IOException {
    String basePath="D:/tpch_data/tpch/5gb/";
    List<String> tables=new ArrayList<>();
    /*tables.add("customer");
    tables.add("lineitem");
    tables.add("nation");
    tables.add("orders");
    tables.add("part");
    tables.add("region");
    tables.add("supplier");*/
    tables.add("partsupp");

    for(int i=0;i<tables.size();i++){
   splitFileIn200K(basePath,tables.get(i));
   }

  }

  private static void splitFileIn200K(String basePath, String s) throws IOException {
   File sFile=new File(basePath+"/"+s+".tbl")   ;
   String dPath=dest+"/"+s;
    File dFile=new File(dPath);
    if(!dFile.exists()){
      dFile.mkdirs();
    }

    BufferedReader reader=new BufferedReader(new FileReader(sFile));
    String line="";
    int count=0;
    int fileIndex=0;
    List<String> temList=new ArrayList<>();
    while((line=reader.readLine())!=null){
      count++;
      temList.add(line);
      if(count==200000){
        count=0;
        fileIndex++;
        write2LFile(temList,dFile,fileIndex);
        temList.clear();
      }
    }

    if(temList.size()>0){
      write2LFile(temList,dFile,++fileIndex);
    }
    System.out.println("Written for "+s+" to "+dFile.toString()+" total Split are "+fileIndex);
    reader.close();


  }

  private static void write2LFile(List<String> temList, File dFile ,int fileIndex)
      throws FileNotFoundException {
    PrintWriter writer=new PrintWriter(dFile+"/"+fileIndex+".tbl");
   int length= temList.size();
    for(int i=0;i<length;i++){
      writer.write(temList.get(i));
      if(i==length-1){
        continue;
      }
      writer.write("\n");
    }
    writer.close();
  }
}
