package org.apache.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class JdbcQueryRun {

  private static String driverName = "org.apache.hive.jdbc.HiveDriver";
  private static Properties properties= new Properties();
  private static Map<String,String> mandatoryListAndMap=new HashMap<>();
  Connection con=null;
  public static void main(String[] args) {
    JdbcQueryRun run=new JdbcQueryRun();

    mandatoryListAndMap.put("url","");
    mandatoryListAndMap.put("queryPath","");
    mandatoryListAndMap.put("resultPath","");

    run.loadConfig();
    run.checkMandaoryConf();
    run.prepareConnection();
    List<String> strings = run.loadQueries(null);
    run.executeQueries(strings);

  }

  public void executeQueries(List<String> strings) {
    int repeat = Integer.parseInt(properties.getProperty("repeat", "1"));
    PrintWriter resultWriter = getResultWriterWithHeader(repeat);
    try {
      int quid = 0;
      for (String query : strings) {
        resultWriter.append(quid + " ,");
        for (int j = 0; j < repeat; j++) {
          long startTime = System.currentTimeMillis();
          Statement stmt = con.createStatement();
          ResultSet res = stmt.executeQuery(query);
          if(j==0){
            processResult(res, true, quid + "");
          }else {
            processResult(res, false, quid + "");
          }
          long endTime = System.currentTimeMillis();
          resultWriter.append((endTime - startTime) + ",");
          resultWriter.append("\n");
          resultWriter.flush();
        }
        quid++;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    finally {
      resultWriter.close();
    }

  }

  private void processResult( ResultSet res,boolean print,String qID) throws SQLException {
    ResultSetMetaData metaData = res.getMetaData();
    int columnCount = metaData.getColumnCount();
    int totalRows = 0;
    if (print) {
      for (int i = 0; i < columnCount; i++) {
        System.out.print(metaData.getColumnName(i+1) + " , ");
      }
      System.out.println();
    }
    if (print) {
      while (res.next()) {
        for (int i = 0; i < columnCount; i++) {
          System.out.print(res.getString(i + 1) + " , ");
        }
        System.out.println();
        totalRows++;
      }
    } else {
      while (res.next()) {
        totalRows++;
      }
    }
    System.out.println();
    System.out.println(qID + "," + totalRows + " records");
    System.out.println("===========End===="+qID);
  }

  private PrintWriter getResultWriterWithHeader(int repeat) {
    PrintWriter resultWriter = getResultWriter();
    resultWriter.append("ID").append(",");
    for(int i=0;i<repeat;i++){
      resultWriter.append("Time(ms)").append(",");
    }
    resultWriter.append("\n");
    return resultWriter;
  }

  private PrintWriter getResultWriter() {
    try {
      return new PrintWriter(properties.getProperty("resultPath"));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }

  private void checkMandaoryConf() {
    Iterator<String> iterator = mandatoryListAndMap.keySet().iterator();
    while (iterator.hasNext()){
      String key = iterator.next();
      String property = properties.getProperty(key);
      if(property==null || property.isEmpty()){
        System.out.println("Property "+key+" should not be defined");
        System.exit(1);
      }
    }

  }

  public List<String> loadQueries(String path) {
    List<String> queries=new ArrayList<>();
    try {
      if (path == null) {
        path = properties.getProperty("queryPath");
      }
      BufferedReader reader = new BufferedReader(new FileReader(path));
      String line;
      StringBuffer query=new StringBuffer();
      while ((line=reader.readLine())!=null){
        line=line.trim();
        if(line.endsWith(";")){
          line=line.trim().substring(0,line.length()-1);
          query.append(line).append(" ");
          queries.add(query.toString());
          query=new StringBuffer();
        } else {
          query.append(line).append(" ");
        }

      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return queries;
  }

  private void loadConfig() {
    String conf=System.getProperty("conf.path");
    if(conf==null){
      System.out.println("Please provide the conf.path in Env");
      System.exit(1);
    }
    try {
      properties.load(new FileReader(conf));
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private void prepareConnection() {
    try {
      Class.forName(driverName);
      con = DriverManager.getConnection(properties.getProperty("url"));
      //Statement stmt = con.createStatement();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
