import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class ex1{
    private final static IntWritable grade = new IntWritable(1);
    private final static Text ID = new Text();

    public static class Map extends Mapper<Object, Text, Text, IntWritable>{
        @Override
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            StringTokenizer itr = new StringTokenizer(value.toString(), ",");
            itr.nextToken();
            ID.set(itr.nextToken());
            int g = Integer.parseInt(itr.nextToken());
            grade.set(g);
            context.write(ID, grade);
        }
    }

    public static class Reduce extends Reducer<Text, IntWritable, Text, IntWritable>{
        @Override
        // The context should contain the ID and highest grade.
        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int max = 0;
            for(IntWritable value:values) if(value.get() > max) max = value.get();
            context.write(key,new IntWritable(max));
        }
    }

    public static int run(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration conf = new Configuration();
        Job job = new Job(conf, "ex1");
        job.setJarByClass(ex1.class);
        job.setMapperClass(Map.class);
        job.setCombinerClass(Reduce.class);
        job.setReducerClass(Reduce.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        return(job.waitForCompletion(true) ? 0 : 1);
    }

    public static void main(String[] args) throws Exception {
        if(args.length != 2) System.err.println("Wrong usage! Enter again!\nUsage: ex1 <INPUT_PATH> <OUTPUT_PATH>");

        long start_time = System.currentTimeMillis();
        int result = run(args);
        long end_time = System.currentTimeMillis();
        System.out.println(end_time - start_time);
        System.exit(result);
    }
}
