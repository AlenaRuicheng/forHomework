package kpi;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import util.ClearOutput;

import java.io.IOException;

/**
 * Created by Ruicheng on 2018/1/24.
 */
public class MRForPV {
    public static class MyMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
        Text outKey = new Text();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            KPIBean kpi = KPIBean.getKPI(value.toString());
            if (!kpi.getState().equals("200")) {
                return;
            }
            outKey.set(kpi.getPv());
            context.write(outKey, NullWritable.get());
        }
    }

    public static class MyReducer extends Reducer<Text, NullWritable, Text, IntWritable> {
        IntWritable outValue = new IntWritable();

        @Override
        protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (NullWritable i : values) {
                sum++;
            }
            outValue.set(sum);
            context.write(key, outValue);
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Job job = Job.getInstance();
        job.setMapperClass(MyMapper.class);
        job.setReducerClass(MyReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        Path path = new Path("D:\\BigData\\outcome\\kpi\\pv");
        FileInputFormat.setInputPaths(job, new Path("D:\\BigData\\sample\\homework\\UsersAndBrowsers"));
        FileOutputFormat.setOutputPath(job, path);
        ClearOutput.delete(path);
        job.waitForCompletion(true);
    }
}
