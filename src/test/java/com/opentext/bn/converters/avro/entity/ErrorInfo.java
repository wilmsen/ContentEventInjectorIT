/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.opentext.bn.converters.avro.entity;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class ErrorInfo extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -1412351019387902108L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"ErrorInfo\",\"namespace\":\"com.opentext.bn.converters.avro.entity\",\"fields\":[{\"name\":\"errorCode\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"externalErrorMessage\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"internalErrorMessage\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"isPayloadRelated\",\"type\":\"boolean\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<ErrorInfo> ENCODER =
      new BinaryMessageEncoder<ErrorInfo>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<ErrorInfo> DECODER =
      new BinaryMessageDecoder<ErrorInfo>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<ErrorInfo> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<ErrorInfo> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<ErrorInfo>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this ErrorInfo to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a ErrorInfo from a ByteBuffer. */
  public static ErrorInfo fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

   private java.lang.String errorCode;
   private java.lang.String externalErrorMessage;
   private java.lang.String internalErrorMessage;
   private boolean isPayloadRelated;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public ErrorInfo() {}

  /**
   * All-args constructor.
   * @param errorCode The new value for errorCode
   * @param externalErrorMessage The new value for externalErrorMessage
   * @param internalErrorMessage The new value for internalErrorMessage
   * @param isPayloadRelated The new value for isPayloadRelated
   */
  public ErrorInfo(java.lang.String errorCode, java.lang.String externalErrorMessage, java.lang.String internalErrorMessage, java.lang.Boolean isPayloadRelated) {
    this.errorCode = errorCode;
    this.externalErrorMessage = externalErrorMessage;
    this.internalErrorMessage = internalErrorMessage;
    this.isPayloadRelated = isPayloadRelated;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return errorCode;
    case 1: return externalErrorMessage;
    case 2: return internalErrorMessage;
    case 3: return isPayloadRelated;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: errorCode = (java.lang.String)value$; break;
    case 1: externalErrorMessage = (java.lang.String)value$; break;
    case 2: internalErrorMessage = (java.lang.String)value$; break;
    case 3: isPayloadRelated = (java.lang.Boolean)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'errorCode' field.
   * @return The value of the 'errorCode' field.
   */
  public java.lang.String getErrorCode() {
    return errorCode;
  }


  /**
   * Gets the value of the 'externalErrorMessage' field.
   * @return The value of the 'externalErrorMessage' field.
   */
  public java.lang.String getExternalErrorMessage() {
    return externalErrorMessage;
  }


  /**
   * Gets the value of the 'internalErrorMessage' field.
   * @return The value of the 'internalErrorMessage' field.
   */
  public java.lang.String getInternalErrorMessage() {
    return internalErrorMessage;
  }


  /**
   * Gets the value of the 'isPayloadRelated' field.
   * @return The value of the 'isPayloadRelated' field.
   */
  public java.lang.Boolean getIsPayloadRelated() {
    return isPayloadRelated;
  }


  /**
   * Creates a new ErrorInfo RecordBuilder.
   * @return A new ErrorInfo RecordBuilder
   */
  public static com.opentext.bn.converters.avro.entity.ErrorInfo.Builder newBuilder() {
    return new com.opentext.bn.converters.avro.entity.ErrorInfo.Builder();
  }

  /**
   * Creates a new ErrorInfo RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new ErrorInfo RecordBuilder
   */
  public static com.opentext.bn.converters.avro.entity.ErrorInfo.Builder newBuilder(com.opentext.bn.converters.avro.entity.ErrorInfo.Builder other) {
    return new com.opentext.bn.converters.avro.entity.ErrorInfo.Builder(other);
  }

  /**
   * Creates a new ErrorInfo RecordBuilder by copying an existing ErrorInfo instance.
   * @param other The existing instance to copy.
   * @return A new ErrorInfo RecordBuilder
   */
  public static com.opentext.bn.converters.avro.entity.ErrorInfo.Builder newBuilder(com.opentext.bn.converters.avro.entity.ErrorInfo other) {
    return new com.opentext.bn.converters.avro.entity.ErrorInfo.Builder(other);
  }

  /**
   * RecordBuilder for ErrorInfo instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<ErrorInfo>
    implements org.apache.avro.data.RecordBuilder<ErrorInfo> {

    private java.lang.String errorCode;
    private java.lang.String externalErrorMessage;
    private java.lang.String internalErrorMessage;
    private boolean isPayloadRelated;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.opentext.bn.converters.avro.entity.ErrorInfo.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.errorCode)) {
        this.errorCode = data().deepCopy(fields()[0].schema(), other.errorCode);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.externalErrorMessage)) {
        this.externalErrorMessage = data().deepCopy(fields()[1].schema(), other.externalErrorMessage);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.internalErrorMessage)) {
        this.internalErrorMessage = data().deepCopy(fields()[2].schema(), other.internalErrorMessage);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.isPayloadRelated)) {
        this.isPayloadRelated = data().deepCopy(fields()[3].schema(), other.isPayloadRelated);
        fieldSetFlags()[3] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing ErrorInfo instance
     * @param other The existing instance to copy.
     */
    private Builder(com.opentext.bn.converters.avro.entity.ErrorInfo other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.errorCode)) {
        this.errorCode = data().deepCopy(fields()[0].schema(), other.errorCode);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.externalErrorMessage)) {
        this.externalErrorMessage = data().deepCopy(fields()[1].schema(), other.externalErrorMessage);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.internalErrorMessage)) {
        this.internalErrorMessage = data().deepCopy(fields()[2].schema(), other.internalErrorMessage);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.isPayloadRelated)) {
        this.isPayloadRelated = data().deepCopy(fields()[3].schema(), other.isPayloadRelated);
        fieldSetFlags()[3] = true;
      }
    }

    /**
      * Gets the value of the 'errorCode' field.
      * @return The value.
      */
    public java.lang.String getErrorCode() {
      return errorCode;
    }

    /**
      * Sets the value of the 'errorCode' field.
      * @param value The value of 'errorCode'.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.ErrorInfo.Builder setErrorCode(java.lang.String value) {
      validate(fields()[0], value);
      this.errorCode = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'errorCode' field has been set.
      * @return True if the 'errorCode' field has been set, false otherwise.
      */
    public boolean hasErrorCode() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'errorCode' field.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.ErrorInfo.Builder clearErrorCode() {
      errorCode = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'externalErrorMessage' field.
      * @return The value.
      */
    public java.lang.String getExternalErrorMessage() {
      return externalErrorMessage;
    }

    /**
      * Sets the value of the 'externalErrorMessage' field.
      * @param value The value of 'externalErrorMessage'.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.ErrorInfo.Builder setExternalErrorMessage(java.lang.String value) {
      validate(fields()[1], value);
      this.externalErrorMessage = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'externalErrorMessage' field has been set.
      * @return True if the 'externalErrorMessage' field has been set, false otherwise.
      */
    public boolean hasExternalErrorMessage() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'externalErrorMessage' field.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.ErrorInfo.Builder clearExternalErrorMessage() {
      externalErrorMessage = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'internalErrorMessage' field.
      * @return The value.
      */
    public java.lang.String getInternalErrorMessage() {
      return internalErrorMessage;
    }

    /**
      * Sets the value of the 'internalErrorMessage' field.
      * @param value The value of 'internalErrorMessage'.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.ErrorInfo.Builder setInternalErrorMessage(java.lang.String value) {
      validate(fields()[2], value);
      this.internalErrorMessage = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'internalErrorMessage' field has been set.
      * @return True if the 'internalErrorMessage' field has been set, false otherwise.
      */
    public boolean hasInternalErrorMessage() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'internalErrorMessage' field.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.ErrorInfo.Builder clearInternalErrorMessage() {
      internalErrorMessage = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'isPayloadRelated' field.
      * @return The value.
      */
    public java.lang.Boolean getIsPayloadRelated() {
      return isPayloadRelated;
    }

    /**
      * Sets the value of the 'isPayloadRelated' field.
      * @param value The value of 'isPayloadRelated'.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.ErrorInfo.Builder setIsPayloadRelated(boolean value) {
      validate(fields()[3], value);
      this.isPayloadRelated = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'isPayloadRelated' field has been set.
      * @return True if the 'isPayloadRelated' field has been set, false otherwise.
      */
    public boolean hasIsPayloadRelated() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'isPayloadRelated' field.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.ErrorInfo.Builder clearIsPayloadRelated() {
      fieldSetFlags()[3] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ErrorInfo build() {
      try {
        ErrorInfo record = new ErrorInfo();
        record.errorCode = fieldSetFlags()[0] ? this.errorCode : (java.lang.String) defaultValue(fields()[0]);
        record.externalErrorMessage = fieldSetFlags()[1] ? this.externalErrorMessage : (java.lang.String) defaultValue(fields()[1]);
        record.internalErrorMessage = fieldSetFlags()[2] ? this.internalErrorMessage : (java.lang.String) defaultValue(fields()[2]);
        record.isPayloadRelated = fieldSetFlags()[3] ? this.isPayloadRelated : (java.lang.Boolean) defaultValue(fields()[3]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<ErrorInfo>
    WRITER$ = (org.apache.avro.io.DatumWriter<ErrorInfo>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<ErrorInfo>
    READER$ = (org.apache.avro.io.DatumReader<ErrorInfo>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
