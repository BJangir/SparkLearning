package org.apache.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

public class DateTest {

  public static void main(String[] args) throws Exception {
     //getTimes();
     //getTimes1();
    String pathResult="D:/redbook/Insertinto_actual_next200.sql";
    List<String> timeCal = getTimeCal();
    Iterator<String> iterator = timeCal.iterator();
    String s1 = loadTemplateQuery("D:/redbook/Insertinto_template_next200.sql");
    String s1_copy=new String(s1);
    PrintWriter writer=new PrintWriter(pathResult);
    for(int j=1000;j<timeCal.size();){
      //replace caCAP_TIME
      for (int i=0;i<10;i++){
        s1=s1.replaceFirst("\\$",timeCal.get(j+i));
      }
      //replace PCAP_TIME
      for (int i=0;i<10;i++){
        String pcapTime=timeCal.get(j+i).replaceAll("-","").replaceAll(" ","").replaceAll(":","");
        s1=s1.replaceFirst("\\$",pcapTime);
      }

          writer.append(s1);
      s1=new String(s1_copy);
        j=j+10;
      }
      writer.close();





    /*List<String> strings = jdbcQueryRun.loadQueries("D:/redbook/selectCustomIntert.sql");
    for(String s:strings){
      System.out.println(s);
    }*/

  }

  private static String loadTemplateQuery(String path) throws Exception {
    BufferedReader reader = new BufferedReader(new FileReader(path));
    String line="";
    StringBuffer query=new StringBuffer();
    while ((line=reader.readLine())!=null){
      query.append(line);
      query.append("\n");
    }
  return query.toString();
  }

  private static List<String> getTimeCal() {
    String cap_time_format="YYYY-MM-dd HH:mm:ss";
    String p_captime_format="YYYYMMddHHmmss";


    SimpleDateFormat simpleDateFormat_p_capTime=new SimpleDateFormat(p_captime_format);
    SimpleDateFormat simpleDateFormat_capTime=new SimpleDateFormat(cap_time_format);

    Calendar calendar=new GregorianCalendar();
    calendar.set(2016,05,28,18,11,56);
    //System.out.println(dateFormat.format());


    int counter=2000;
    long interval=60*1000;
    long currentTime = calendar.getTime().getTime();
    String format_captime1 = simpleDateFormat_p_capTime.format(new Date(currentTime));
    System.out.println(format_captime1);
    List<String> allIds=new ArrayList<>();
    for(int i=0;i<counter;i++){
      currentTime = currentTime + interval;
      String format_p_captime = simpleDateFormat_p_capTime.format(new Date(currentTime));
      String format_captime = simpleDateFormat_capTime.format(new Date(currentTime));
      System.out.println(format_p_captime +" , "+format_captime +" ," +format_captime.replaceAll("-","").replaceAll(" ","").replaceAll(":",""));
      allIds.add(format_captime);
    }
    return allIds;
  }

  private static void getTimes1() throws ParseException {
    String value="2016-06-28 18:11:56";
    String cap_time_format="YYYY-MM-dd HH:mm:ss";
    SimpleDateFormat simpleDateFormat_capTime=new SimpleDateFormat(cap_time_format);

    SimpleDateFormat sdfLocal = new SimpleDateFormat(cap_time_format);
    simpleDateFormat_capTime.setTimeZone(TimeZone.getTimeZone("GMT"));

    sdfLocal.setTimeZone(TimeZone.getTimeZone("CST"));

    Date parse = simpleDateFormat_capTime.parse(value);
    long currentTime=parse.getTime();
    String format_captime1 = sdfLocal.format(new Date(currentTime));
    System.out.println(format_captime1);
    long interval=60*1000;
    for(int i=0;i<3;i++){
      currentTime = currentTime + interval;
      String format_captime = sdfLocal.format(new Date(currentTime));
      System.out.println(format_captime);
    }

  }

  private static void getTimes () throws ParseException {
      String p_captime_output="20160628181156";
      String p_captime_format="YYYYMMddHHmmss";
      String cap_time_format="YYYY-MM-dd HH:mm:ss";

      SimpleDateFormat simpleDateFormat_p_capTime=new SimpleDateFormat(p_captime_format);
      simpleDateFormat_p_capTime.setTimeZone(TimeZone.getTimeZone("CST"));
      SimpleDateFormat simpleDateFormat_capTime=new SimpleDateFormat(cap_time_format);
    simpleDateFormat_capTime.setTimeZone(TimeZone.getTimeZone("CST"));
      long interval=60*1000;
      int counter=10;
      long currentTime=0;
      long diff=86400000;
      Date parse = simpleDateFormat_p_capTime.parse(p_captime_output);
      currentTime = parse.getTime()+diff;
    String format_captime1 = simpleDateFormat_capTime.format(new Date(currentTime));
    System.out.println(format_captime1);
      for(int i=0;i<counter;i++){
        currentTime = currentTime + interval;
        String format_p_captime = simpleDateFormat_p_capTime.format(new Date(currentTime));
        String format_captime = simpleDateFormat_capTime.format(new Date(currentTime));
        System.out.println(format_p_captime +" , "+format_captime);
      }
  }
}
