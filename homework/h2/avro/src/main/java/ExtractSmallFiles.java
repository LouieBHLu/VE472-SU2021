import student.avro.Student;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.io.DatumReader;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class ExtractSmallFiles {
    public ExtractSmallFiles(){}

    public void deserialize(String avro_file, String output_dir) throws IOException {
        File dir = new File(output_dir);
        if(!dir.mkdir()) System.out.println("Dir already exists.");

        DatumReader<Student> datumReader = new SpecificDatumReader<Student>(Student.class);
        File input = new File(avro_file);
        DataFileReader<Student> dataFileReader = new DataFileReader<Student>(input, datumReader);
        Student student;

        while(dataFileReader.hasNext()){
            student = dataFileReader.next();
            String info = new String(student.getFilecontent().array());
            String SHA = DigestUtils.shaHex(info);
            if(!(student.getChecksum().toString().equals(SHA))) System.err.println("The content does not match the original one!");

            // Generate small file
            File f = new File(output_dir + "/" + student.getFilename());
            BufferedWriter out = new BufferedWriter(new FileWriter(f));
            out.write(info);
            out.flush();
            out.close();
        }
    }
}