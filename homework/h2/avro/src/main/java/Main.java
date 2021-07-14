import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String file_path = "../small_files";
        String output_dir = "../extract_files";
        String compact_path = "../compact.avro";
        String schema_path = "src/main/avro/student.avsc";

        CompactSmallFiles compactSmallFiles = new CompactSmallFiles();
        compactSmallFiles.serialize(file_path, schema_path, compact_path);

        ExtractSmallFiles extractSmallFiles = new ExtractSmallFiles();
        extractSmallFiles.deserialize(compact_path, output_dir);
    }
}