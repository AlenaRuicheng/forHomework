package util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;

/**
 * Created by asus on 2018/1/18.
 */
public class ClearOutput {
    public static void delete(Path path) {
        try {
            FileSystem fileSystem = FileSystem.get(new Configuration());
            if (fileSystem.exists(path)) {
                fileSystem.delete(path, true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
