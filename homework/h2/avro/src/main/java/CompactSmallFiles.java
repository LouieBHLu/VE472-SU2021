import student.avro.Student;
import org.apache.avro.Schema;
import org.apache.avro.file.CodecFactory;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class CompactSmallFiles {
    private final ArrayList<File> Files;

    public CompactSmallFiles(){
        Files = new ArrayList<>();
    }

    public void serialize(String file_path, String schema_path, String output_path) throws IOException {
        File dir = new File(file_path);
        File[] input = dir.listFiles();
        assert input != null;
        for(File file:input){
            if(!file.isDirectory()) Files.add(file);
        }

        DatumWriter<Student> datumWriter = new SpecificDatumWriter<>(Student.class);
        DataFileWriter<Student> dataFileWriter = new DataFileWriter<>(datumWriter);
        dataFileWriter.setCodec(CodecFactory.snappyCodec());

        Schema schema = new Schema.Parser().parse(new File(schema_path));
        File output_file = new File(output_path);
        dataFileWriter.create(schema, output_file);

        for(File file:Files){
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line;
            StringBuilder info = new StringBuilder();
            while((line = br.readLine()) != null){
                line = line.trim();
                info.append(line).append("\n");
            }
            // Calculate the bytes and SHA of current file
            String SHA = DigestUtils.shaHex(String.valueOf(info));
            ByteBuffer byteBuffer = ByteBuffer.wrap(String.valueOf(info).getBytes());

            Student student = Student.newBuilder()
                    .setFilename(file.getName())
                    .setFilecontent(byteBuffer)
                    .setChecksum(SHA)
                    .build();
            dataFileWriter.append(student);
            br.close();
        }
        dataFileWriter.close();
    }
}