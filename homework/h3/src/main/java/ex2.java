import org.apache.avro.Schema;
import org.apache.avro.mapred.AvroKey;
import org.apache.avro.mapred.AvroValue;
import org.apache.avro.mapreduce.AvroJob;
import org.apache.avro.mapreduce.AvroKeyInputFormat;
import org.apache.avro.mapreduce.AvroKeyValueOutputFormat;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import student.avro.Student;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.io.Text;
import java.io.IOException;

public class ex2 extends Configured implements Tool {
    private final static IntWritable grade = new IntWritable(1);
    private final static Text ID = new Text();

    public static class Map extends Mapper<AvroKey<Student>, NullWritable, Text, IntWritable>{
        @Override
        public void map(AvroKey<Student> key, NullWritable value, Context context) throws IOException, InterruptedException {
            String data = new String(key.datum().getFilecontent().array());
            String[] lines = data.split("\n");

            for(String line:lines){
//                 DEBUG
//                System.out.println(line);
//                 DEBUG
                String[] word = line.split(",");
                ID.set(word[1]);
                grade.set(Integer.parseInt(word[2]));
                context.write(ID, grade);
            }
        }
    }

    public static class Reduce extends Reducer<Text, IntWritable, AvroKey<CharSequence>, AvroValue<Integer>>{
        @Override
        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int max = 0;
            CharSequence output_key = key.toString();

            for (IntWritable value:values) if(value.get() > max) max = value.get();
            context.write(new AvroKey<>(output_key), new AvroValue<>(max));
        }
    }

    @Override
    public int run(String[] strings) throws Exception {
        Job job = new Job();
        job.setJarByClass(ex2.class);
        job.setJobName("ex2");

        job.setMapperClass(ex2.Map.class);
        job.setReducerClass(ex2.Reduce.class);

        job.setInputFormatClass(AvroKeyInputFormat.class);
        AvroJob.setInputKeySchema(job, Student.getClassSchema());

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputFormatClass(AvroKeyValueOutputFormat.class);
        AvroJob.setOutputKeySchema(job,Schema.create(Schema.Type.STRING));
        AvroJob.setOutputValueSchema(job,Schema.create(Schema.Type.INT));

        FileInputFormat.setInputPaths(job, new Path(strings[0]));
        FileOutputFormat.setOutputPath(job, new Path(strings[1]));

        return(job.waitForCompletion(true) ? 0 : 1);
    }

    public static void main(String[] args) throws Exception {
        if(args.length != 2) System.err.println("Wrong usage! Enter again!\nUsage: ex2 <INPUT_PATH> <OUTPUT_PATH>");

        long start_time = System.currentTimeMillis();
        int result = ToolRunner.run(new ex2(), args);
        long end_time = System.currentTimeMillis();
        System.out.println(end_time - start_time);
        System.exit(result);
    }
}