/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package student.avro;  
@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class Student extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Student\",\"namespace\":\"student.avro\",\"fields\":[{\"name\":\"filename\",\"type\":\"string\"},{\"name\":\"filecontent\",\"type\":\"bytes\"},{\"name\":\"checksum\",\"type\":\"string\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public java.lang.CharSequence filename;
  @Deprecated public java.nio.ByteBuffer filecontent;
  @Deprecated public java.lang.CharSequence checksum;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use {@link \#newBuilder()}. 
   */
  public Student() {}

  /**
   * All-args constructor.
   */
  public Student(java.lang.CharSequence filename, java.nio.ByteBuffer filecontent, java.lang.CharSequence checksum) {
    this.filename = filename;
    this.filecontent = filecontent;
    this.checksum = checksum;
  }

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

  /** Creates a new Student RecordBuilder */
  public static student.avro.Student.Builder newBuilder() {
    return new student.avro.Student.Builder();
  }
  
  /** Creates a new Student RecordBuilder by copying an existing Builder */
  public static student.avro.Student.Builder newBuilder(student.avro.Student.Builder other) {
    return new student.avro.Student.Builder(other);
  }
  
  /** Creates a new Student RecordBuilder by copying an existing Student instance */
  public static student.avro.Student.Builder newBuilder(student.avro.Student other) {
    return new student.avro.Student.Builder(other);
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
      super(student.avro.Student.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(student.avro.Student.Builder other) {
      super(other);
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
    
    /** Creates a Builder by copying an existing Student instance */
    private Builder(student.avro.Student other) {
            super(student.avro.Student.SCHEMA$);
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

    /** Gets the value of the 'filename' field */
    public java.lang.CharSequence getFilename() {
      return filename;
    }
    
    /** Sets the value of the 'filename' field */
    public student.avro.Student.Builder setFilename(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.filename = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'filename' field has been set */
    public boolean hasFilename() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'filename' field */
    public student.avro.Student.Builder clearFilename() {
      filename = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'filecontent' field */
    public java.nio.ByteBuffer getFilecontent() {
      return filecontent;
    }
    
    /** Sets the value of the 'filecontent' field */
    public student.avro.Student.Builder setFilecontent(java.nio.ByteBuffer value) {
      validate(fields()[1], value);
      this.filecontent = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'filecontent' field has been set */
    public boolean hasFilecontent() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'filecontent' field */
    public student.avro.Student.Builder clearFilecontent() {
      filecontent = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'checksum' field */
    public java.lang.CharSequence getChecksum() {
      return checksum;
    }
    
    /** Sets the value of the 'checksum' field */
    public student.avro.Student.Builder setChecksum(java.lang.CharSequence value) {
      validate(fields()[2], value);
      this.checksum = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'checksum' field has been set */
    public boolean hasChecksum() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'checksum' field */
    public student.avro.Student.Builder clearChecksum() {
      checksum = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    public Student build() {
      try {
        Student record = new Student();
        record.filename = fieldSetFlags()[0] ? this.filename : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.filecontent = fieldSetFlags()[1] ? this.filecontent : (java.nio.ByteBuffer) defaultValue(fields()[1]);
        record.checksum = fieldSetFlags()[2] ? this.checksum : (java.lang.CharSequence) defaultValue(fields()[2]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
