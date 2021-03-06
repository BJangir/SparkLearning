package org.apache.test;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class MRTest {

  public static void main(String[] args)
      throws IOException, ClassNotFoundException, InterruptedException {

    Configuration c=new Configuration();
    c.set("fs.default.name","file:///");
    c.set("mapreduce.jobtracker.staging.root.dir","file:///D:/tmp");
    Path input=new Path("file:///D:/data/mr.csv");
    Path output=new Path("file:///D:/data/mrout");

    if(args.length>0){
      String[] files=new GenericOptionsParser(c,args).getRemainingArgs();
       input=new Path(files[0]);
        output=new Path(files[1]);
    }
    Job j=new Job(c,"wordcount");
    j.setJarByClass(MRTest.class);
    j.setMapperClass(MapForWordCount.class);
    j.setReducerClass(ReduceForWordCount.class);
    j.setOutputKeyClass(Text.class);
    j.setOutputValueClass(IntWritable.class);
    FileInputFormat.addInputPath(j, input);
    FileOutputFormat.setOutputPath(j, output);
    System.exit(j.waitForCompletion(true)?0:1);


  }

  class  MapForWordCount extends Mapper<LongWritable,Text,Text, IntWritable> {

    @Override protected void map(LongWritable key, Text value, Context context)
        throws IOException, InterruptedException {
      String line = value.toString();
      String[] words=line.split(",");
      for(String word: words )
      {
        Text outputKey = new Text(word.toUpperCase().trim());
        IntWritable outputValue = new IntWritable(1);
        context.write(outputKey, outputValue);
      }
    }
  }

  class ReduceForWordCount extends Reducer<Text,IntWritable,Text,IntWritable>{
    @Override protected void reduce(Text key, Iterable<IntWritable> values, Context context)
        throws IOException, InterruptedException {
      int sum = 0;
      for(IntWritable value : values)
      {
        sum += value.get();
      }
      context.write(key, new IntWritable(sum));


    }
  }

}


