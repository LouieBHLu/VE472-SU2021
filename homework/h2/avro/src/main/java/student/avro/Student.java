/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package student.avro;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class Student extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -7965578945735138795L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Student\",\"namespace\":\"student.avro\",\"fields\":[{\"name\":\"filename\",\"type\":\"string\"},{\"name\":\"filecontent\",\"type\":\"bytes\"},{\"name\":\"checksum\",\"type\":\"string\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<Student> ENCODER =
      new BinaryMessageEncoder<Student>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<Student> DECODER =
      new BinaryMessageDecoder<Student>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<Student> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<Student> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<Student> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<Student>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this Student to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a Student from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a Student instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static Student fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public java.lang.CharSequence filename;
  @Deprecated public java.nio.ByteBuffer filecontent;
  @Deprecated public java.lang.CharSequence checksum;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public Student() {}

  /**
   * All-args constructor.
   * @param filename The new value for filename
   * @param filecontent The new value for filecontent
   * @param checksum The new value for checksum
   */
  public Student(java.lang.CharSequence filename, java.nio.ByteBuffer filecontent, java.lang.CharSequence checksum) {
    this.filename = filename;
    this.filecontent = filecontent;
    this.checksum = checksum;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return filename;
    case 1: return filecontent;
    case 2: return checksum;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: filename = (java.lang.CharSequence)value$; break;
    case 1: filecontent = (java.nio.ByteBuffer)value$; break;
    case 2: checksum = (java.lang.CharSequence)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'filename' field.
   * @return The value of the 'filename' field.
   */
  public java.lang.CharSequence getFilename() {
    return filename;
  }


  /**
   * Sets the value of the 'filename' field.
   * @param value the value to set.
   */
  public void setFilename(java.lang.CharSequence value) {
    this.filename = value;
  }

  /**
   * Gets the value of the 'filecontent' field.
   * @return The value of the 'filecontent' field.
   */
  public java.nio.ByteBuffer getFilecontent() {
    return filecontent;
  }


  /**
   * Sets the value of the 'filecontent' field.
   * @param value the value to set.
   */
  public void setFilecontent(java.nio.ByteBuffer value) {
    this.filecontent = value;
  }

  /**
   * Gets the value of the 'checksum' field.
   * @return The value of the 'checksum' field.
   */
  public java.lang.CharSequence getChecksum() {
    return checksum;
  }


  /**
   * Sets the value of the 'checksum' field.
   * @param value the value to set.
   */
  public void setChecksum(java.lang.CharSequence value) {
    this.checksum = value;
  }

  /**
   * Creates a new Student RecordBuilder.
   * @return A new Student RecordBuilder
   */
  public static student.avro.Student.Builder newBuilder() {
    return new student.avro.Student.Builder();
  }

  /**
   * Creates a new Student RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new Student RecordBuilder
   */
  public static student.avro.Student.Builder newBuilder(student.avro.Student.Builder other) {
    if (other == null) {
      return new student.avro.Student.Builder();
    } else {
      return new student.avro.Student.Builder(other);
    }
  }

  /**
   * Creates a new Student RecordBuilder by copying an existing Student instance.
   * @param other The existing instance to copy.
   * @return A new Student RecordBuilder
   */
  public static student.avro.Student.Builder newBuilder(student.avro.Student other) {
    if (other == null) {
      return new student.avro.Student.Builder();
    } else {
      return new student.avro.Student.Builder(other);
    }
  }

  /**
   * RecordBuilder for Student instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Student>
    implements org.apache.avro.data.RecordBuilder<Student> {

    private java.lang.CharSequence filename;
    private java.nio.ByteBuffer filecontent;
    private java.lang.CharSequence checksum;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(student.avro.Student.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.filename)) {
        this.filename = data().deepCopy(fields()[0].schema(), other.filename);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.filecontent)) {
        this.filecontent = data().deepCopy(fields()[1].schema(), other.filecontent);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.checksum)) {
        this.checksum = data().deepCopy(fields()[2].schema(), other.checksum);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
    }

    /**
     * Creates a Builder by copying an existing Student instance
     * @param other The existing instance to copy.
     */
    private Builder(student.avro.Student other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.filename)) {
        this.filename = data().deepCopy(fields()[0].schema(), other.filename);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.filecontent)) {
        this.filecontent = data().deepCopy(fields()[1].schema(), other.filecontent);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.checksum)) {
        this.checksum = data().deepCopy(fields()[2].schema(), other.checksum);
        fieldSetFlags()[2] = true;
      }
    }

    /**
      * Gets the value of the 'filename' field.
      * @return The value.
      */
    public java.lang.CharSequence getFilename() {
      return filename;
    }


    /**
      * Sets the value of the 'filename' field.
      * @param value The value of 'filename'.
      * @return This builder.
      */
    public student.avro.Student.Builder setFilename(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.filename = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'filename' field has been set.
      * @return True if the 'filename' field has been set, false otherwise.
      */
    public boolean hasFilename() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'filename' field.
      * @return This builder.
      */
    public student.avro.Student.Builder clearFilename() {
      filename = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'filecontent' field.
      * @return The value.
      */
    public java.nio.ByteBuffer getFilecontent() {
      return filecontent;
    }


    /**
      * Sets the value of the 'filecontent' field.
      * @param value The value of 'filecontent'.
      * @return This builder.
      */
    public student.avro.Student.Builder setFilecontent(java.nio.ByteBuffer value) {
      validate(fields()[1], value);
      this.filecontent = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'filecontent' field has been set.
      * @return True if the 'filecontent' field has been set, false otherwise.
      */
    public boolean hasFilecontent() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'filecontent' field.
      * @return This builder.
      */
    public student.avro.Student.Builder clearFilecontent() {
      filecontent = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'checksum' field.
      * @return The value.
      */
    public java.lang.CharSequence getChecksum() {
      return checksum;
    }


    /**
      * Sets the value of the 'checksum' field.
      * @param value The value of 'checksum'.
      * @return This builder.
      */
    public student.avro.Student.Builder setChecksum(java.lang.CharSequence value) {
      validate(fields()[2], value);
      this.checksum = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'checksum' field has been set.
      * @return True if the 'checksum' field has been set, false otherwise.
      */
    public boolean hasChecksum() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'checksum' field.
      * @return This builder.
      */
    public student.avro.Student.Builder clearChecksum() {
      checksum = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Student build() {
      try {
        Student record = new Student();
        record.filename = fieldSetFlags()[0] ? this.filename : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.filecontent = fieldSetFlags()[1] ? this.filecontent : (java.nio.ByteBuffer) defaultValue(fields()[1]);
        record.checksum = fieldSetFlags()[2] ? this.checksum : (java.lang.CharSequence) defaultValue(fields()[2]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<Student>
    WRITER$ = (org.apache.avro.io.DatumWriter<Student>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<Student>
    READER$ = (org.apache.avro.io.DatumReader<Student>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeString(this.filename);

    out.writeBytes(this.filecontent);

    out.writeString(this.checksum);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.filename = in.readString(this.filename instanceof Utf8 ? (Utf8)this.filename : null);

      this.filecontent = in.readBytes(this.filecontent);

      this.checksum = in.readString(this.checksum instanceof Utf8 ? (Utf8)this.checksum : null);

    } else {
      for (int i = 0; i < 3; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.filename = in.readString(this.filename instanceof Utf8 ? (Utf8)this.filename : null);
          break;

        case 1:
          this.filecontent = in.readBytes(this.filecontent);
          break;

        case 2:
          this.checksum = in.readString(this.checksum instanceof Utf8 ? (Utf8)this.checksum : null);
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










