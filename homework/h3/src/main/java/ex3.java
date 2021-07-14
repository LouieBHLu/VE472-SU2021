import org.apache.avro.mapred.AvroKey;
import org.apache.avro.mapreduce.AvroJob;
import org.apache.avro.mapreduce.AvroKeyInputFormat;
import org.apache.avro.mapreduce.AvroKeyValueOutputFormat;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.util.bloom.BloomFilter;
import org.apache.hadoop.util.bloom.Key;
import org.apache.hadoop.util.hash.Hash;
import student.avro.Student;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.apache.hadoop.fs.Path;


public class ex3 extends Configured implements Tool {
    private final static IntWritable grade = new IntWritable(1);
    private final static Text ID = new Text();

    public static class Map extends Mapper<AvroKey<Student>, NullWritable, NullWritable, BloomFilter>{
        @Override
        public void map(AvroKey<Student> key, NullWritable value, Context context){
            BloomFilter bf = new BloomFilter(1000,5,Hash.MURMUR_HASH);
            bf.add(new Key("3".getBytes()));

            String data = new String(key.datum().getFilecontent().array());
            String[] lines = data.split("\n");

            for (String line:lines) {
                String[] word = line.split(",");
                String id = word[1];
                String last_bit = id.substring(id.length() - 1);
                if(bf.membershipTest(new Key(last_bit.getBytes()))){
                    ID.set(word[1]);
                    bf.add(new Key(ID.getBytes()));
                }
            }
        }
    }

    public static class Reduce extends Reducer<NullWritable, BloomFilter, AvroKey<CharSequence>, NullWritable>{
        @Override
        public void reduce(NullWritable key, Iterable<BloomFilter> values, Context context) throws IOException, InterruptedException {
            BloomFilter all = new BloomFilter(1000, 5, Hash.MURMUR_HASH);
            for(BloomFilter value:values) all.or(value);

            ByteArrayOutputStream b_tmp = new ByteArrayOutputStream();
            DataOutputStream d_tmp = new DataOutputStream(b_tmp);
            all.write(d_tmp);

            context.write(new AvroKey<>(b_tmp.toString()) , NullWritable.get());
//            DEBUG
//            System.out.println(b_tmp.toString() + NullWritable.get());
//            DEBUG
            d_tmp.close();
        }
    }

    @Override
    public int run(String[] strings) throws Exception {
        Job job = new Job(getConf());
        job.setJarByClass(ex3.class);
        job.setJobName("ex3");

        FileInputFormat.setInputPaths(job, new Path(strings[0]));
        FileOutputFormat.setOutputPath(job, new Path(strings[1]));

        job.setMapperClass(ex3.Map.class);
        job.setReducerClass(ex3.Reduce.class);

        job.setInputFormatClass(AvroKeyInputFormat.class);
        AvroJob.setInputKeySchema(job, Student.getClassSchema());

        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(BloomFilter.class);

        job.setOutputFormatClass(AvroKeyValueOutputFormat.class);

        return(job.waitForCompletion(true) ? 0 : 1);
    }

    public static void main(String[] args) throws Exception {
        if(args.length != 2) System.err.println("Wrong usage! Enter again!\nUsage: ex2 <INPUT_PATH> <OUTPUT_PATH>");

        long start_time = System.currentTimeMillis();
        int result = ToolRunner.run(new ex3(), args);
        long end_time = System.currentTimeMillis();
        System.out.println(end_time - start_time);
        System.exit(result);
    }
}
