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
public class TaskDetailsEvent extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -2480082210780733843L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TaskDetailsEvent\",\"namespace\":\"com.opentext.bn.converters.avro.entity\",\"fields\":[{\"name\":\"processId\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"processingStages\",\"type\":[\"null\",{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"ProcessingStages\",\"fields\":[{\"name\":\"stageDetails\",\"type\":[\"null\",{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"ProcessingParam\",\"fields\":[{\"name\":\"key\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"value\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}}],\"default\":null},{\"name\":\"stageEndTimestamp\",\"type\":\"long\"},{\"name\":\"stageId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"stageName\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"stageStartTimestamp\",\"type\":\"long\"},{\"name\":\"errorInfo\",\"type\":[\"null\",{\"type\":\"record\",\"name\":\"ErrorInfo\",\"fields\":[{\"name\":\"errorCode\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"externalErrorMessage\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"internalErrorMessage\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"isPayloadRelated\",\"type\":\"boolean\"}]}],\"default\":null},{\"name\":\"contentId\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null}]}}],\"default\":null},{\"name\":\"taskId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"tasksDetails\",\"type\":[\"null\",{\"type\":\"array\",\"items\":\"ProcessingParam\"}],\"default\":null},{\"name\":\"transactionId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"payloadRef\",\"type\":[\"null\",{\"type\":\"record\",\"name\":\"PayloadRef\",\"fields\":[{\"name\":\"payloadId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"payloadType\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}],\"default\":null},{\"name\":\"transactionCategory\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"isCascadeTransactionCategory\",\"type\":[\"null\",\"boolean\"],\"default\":true}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<TaskDetailsEvent> ENCODER =
      new BinaryMessageEncoder<TaskDetailsEvent>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<TaskDetailsEvent> DECODER =
      new BinaryMessageDecoder<TaskDetailsEvent>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<TaskDetailsEvent> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<TaskDetailsEvent> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<TaskDetailsEvent>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this TaskDetailsEvent to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a TaskDetailsEvent from a ByteBuffer. */
  public static TaskDetailsEvent fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

   private java.lang.String processId;
   private java.util.List<com.opentext.bn.converters.avro.entity.ProcessingStages> processingStages;
   private java.lang.String taskId;
   private java.util.List<com.opentext.bn.converters.avro.entity.ProcessingParam> tasksDetails;
   private java.lang.String transactionId;
   private com.opentext.bn.converters.avro.entity.PayloadRef payloadRef;
   private java.lang.String transactionCategory;
   private java.lang.Boolean isCascadeTransactionCategory;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public TaskDetailsEvent() {}

  /**
   * All-args constructor.
   * @param processId The new value for processId
   * @param processingStages The new value for processingStages
   * @param taskId The new value for taskId
   * @param tasksDetails The new value for tasksDetails
   * @param transactionId The new value for transactionId
   * @param payloadRef The new value for payloadRef
   * @param transactionCategory The new value for transactionCategory
   * @param isCascadeTransactionCategory The new value for isCascadeTransactionCategory
   */
  public TaskDetailsEvent(java.lang.String processId, java.util.List<com.opentext.bn.converters.avro.entity.ProcessingStages> processingStages, java.lang.String taskId, java.util.List<com.opentext.bn.converters.avro.entity.ProcessingParam> tasksDetails, java.lang.String transactionId, com.opentext.bn.converters.avro.entity.PayloadRef payloadRef, java.lang.String transactionCategory, java.lang.Boolean isCascadeTransactionCategory) {
    this.processId = processId;
    this.processingStages = processingStages;
    this.taskId = taskId;
    this.tasksDetails = tasksDetails;
    this.transactionId = transactionId;
    this.payloadRef = payloadRef;
    this.transactionCategory = transactionCategory;
    this.isCascadeTransactionCategory = isCascadeTransactionCategory;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return processId;
    case 1: return processingStages;
    case 2: return taskId;
    case 3: return tasksDetails;
    case 4: return transactionId;
    case 5: return payloadRef;
    case 6: return transactionCategory;
    case 7: return isCascadeTransactionCategory;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: processId = (java.lang.String)value$; break;
    case 1: processingStages = (java.util.List<com.opentext.bn.converters.avro.entity.ProcessingStages>)value$; break;
    case 2: taskId = (java.lang.String)value$; break;
    case 3: tasksDetails = (java.util.List<com.opentext.bn.converters.avro.entity.ProcessingParam>)value$; break;
    case 4: transactionId = (java.lang.String)value$; break;
    case 5: payloadRef = (com.opentext.bn.converters.avro.entity.PayloadRef)value$; break;
    case 6: transactionCategory = (java.lang.String)value$; break;
    case 7: isCascadeTransactionCategory = (java.lang.Boolean)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'processId' field.
   * @return The value of the 'processId' field.
   */
  public java.lang.String getProcessId() {
    return processId;
  }


  /**
   * Gets the value of the 'processingStages' field.
   * @return The value of the 'processingStages' field.
   */
  public java.util.List<com.opentext.bn.converters.avro.entity.ProcessingStages> getProcessingStages() {
    return processingStages;
  }


  /**
   * Gets the value of the 'taskId' field.
   * @return The value of the 'taskId' field.
   */
  public java.lang.String getTaskId() {
    return taskId;
  }


  /**
   * Gets the value of the 'tasksDetails' field.
   * @return The value of the 'tasksDetails' field.
   */
  public java.util.List<com.opentext.bn.converters.avro.entity.ProcessingParam> getTasksDetails() {
    return tasksDetails;
  }


  /**
   * Gets the value of the 'transactionId' field.
   * @return The value of the 'transactionId' field.
   */
  public java.lang.String getTransactionId() {
    return transactionId;
  }


  /**
   * Gets the value of the 'payloadRef' field.
   * @return The value of the 'payloadRef' field.
   */
  public com.opentext.bn.converters.avro.entity.PayloadRef getPayloadRef() {
    return payloadRef;
  }


  /**
   * Gets the value of the 'transactionCategory' field.
   * @return The value of the 'transactionCategory' field.
   */
  public java.lang.String getTransactionCategory() {
    return transactionCategory;
  }


  /**
   * Gets the value of the 'isCascadeTransactionCategory' field.
   * @return The value of the 'isCascadeTransactionCategory' field.
   */
  public java.lang.Boolean getIsCascadeTransactionCategory() {
    return isCascadeTransactionCategory;
  }


  /**
   * Creates a new TaskDetailsEvent RecordBuilder.
   * @return A new TaskDetailsEvent RecordBuilder
   */
  public static com.opentext.bn.converters.avro.entity.TaskDetailsEvent.Builder newBuilder() {
    return new com.opentext.bn.converters.avro.entity.TaskDetailsEvent.Builder();
  }

  /**
   * Creates a new TaskDetailsEvent RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new TaskDetailsEvent RecordBuilder
   */
  public static com.opentext.bn.converters.avro.entity.TaskDetailsEvent.Builder newBuilder(com.opentext.bn.converters.avro.entity.TaskDetailsEvent.Builder other) {
    return new com.opentext.bn.converters.avro.entity.TaskDetailsEvent.Builder(other);
  }

  /**
   * Creates a new TaskDetailsEvent RecordBuilder by copying an existing TaskDetailsEvent instance.
   * @param other The existing instance to copy.
   * @return A new TaskDetailsEvent RecordBuilder
   */
  public static com.opentext.bn.converters.avro.entity.TaskDetailsEvent.Builder newBuilder(com.opentext.bn.converters.avro.entity.TaskDetailsEvent other) {
    return new com.opentext.bn.converters.avro.entity.TaskDetailsEvent.Builder(other);
  }

  /**
   * RecordBuilder for TaskDetailsEvent instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<TaskDetailsEvent>
    implements org.apache.avro.data.RecordBuilder<TaskDetailsEvent> {

    private java.lang.String processId;
    private java.util.List<com.opentext.bn.converters.avro.entity.ProcessingStages> processingStages;
    private java.lang.String taskId;
    private java.util.List<com.opentext.bn.converters.avro.entity.ProcessingParam> tasksDetails;
    private java.lang.String transactionId;
    private com.opentext.bn.converters.avro.entity.PayloadRef payloadRef;
    private com.opentext.bn.converters.avro.entity.PayloadRef.Builder payloadRefBuilder;
    private java.lang.String transactionCategory;
    private java.lang.Boolean isCascadeTransactionCategory;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.opentext.bn.converters.avro.entity.TaskDetailsEvent.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.processId)) {
        this.processId = data().deepCopy(fields()[0].schema(), other.processId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.processingStages)) {
        this.processingStages = data().deepCopy(fields()[1].schema(), other.processingStages);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.taskId)) {
        this.taskId = data().deepCopy(fields()[2].schema(), other.taskId);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.tasksDetails)) {
        this.tasksDetails = data().deepCopy(fields()[3].schema(), other.tasksDetails);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.transactionId)) {
        this.transactionId = data().deepCopy(fields()[4].schema(), other.transactionId);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.payloadRef)) {
        this.payloadRef = data().deepCopy(fields()[5].schema(), other.payloadRef);
        fieldSetFlags()[5] = true;
      }
      if (other.hasPayloadRefBuilder()) {
        this.payloadRefBuilder = com.opentext.bn.converters.avro.entity.PayloadRef.newBuilder(other.getPayloadRefBuilder());
      }
      if (isValidValue(fields()[6], other.transactionCategory)) {
        this.transactionCategory = data().deepCopy(fields()[6].schema(), other.transactionCategory);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.isCascadeTransactionCategory)) {
        this.isCascadeTransactionCategory = data().deepCopy(fields()[7].schema(), other.isCascadeTransactionCategory);
        fieldSetFlags()[7] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing TaskDetailsEvent instance
     * @param other The existing instance to copy.
     */
    private Builder(com.opentext.bn.converters.avro.entity.TaskDetailsEvent other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.processId)) {
        this.processId = data().deepCopy(fields()[0].schema(), other.processId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.processingStages)) {
        this.processingStages = data().deepCopy(fields()[1].schema(), other.processingStages);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.taskId)) {
        this.taskId = data().deepCopy(fields()[2].schema(), other.taskId);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.tasksDetails)) {
        this.tasksDetails = data().deepCopy(fields()[3].schema(), other.tasksDetails);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.transactionId)) {
        this.transactionId = data().deepCopy(fields()[4].schema(), other.transactionId);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.payloadRef)) {
        this.payloadRef = data().deepCopy(fields()[5].schema(), other.payloadRef);
        fieldSetFlags()[5] = true;
      }
      this.payloadRefBuilder = null;
      if (isValidValue(fields()[6], other.transactionCategory)) {
        this.transactionCategory = data().deepCopy(fields()[6].schema(), other.transactionCategory);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.isCascadeTransactionCategory)) {
        this.isCascadeTransactionCategory = data().deepCopy(fields()[7].schema(), other.isCascadeTransactionCategory);
        fieldSetFlags()[7] = true;
      }
    }

    /**
      * Gets the value of the 'processId' field.
      * @return The value.
      */
    public java.lang.String getProcessId() {
      return processId;
    }

    /**
      * Sets the value of the 'processId' field.
      * @param value The value of 'processId'.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.TaskDetailsEvent.Builder setProcessId(java.lang.String value) {
      validate(fields()[0], value);
      this.processId = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'processId' field has been set.
      * @return True if the 'processId' field has been set, false otherwise.
      */
    public boolean hasProcessId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'processId' field.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.TaskDetailsEvent.Builder clearProcessId() {
      processId = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'processingStages' field.
      * @return The value.
      */
    public java.util.List<com.opentext.bn.converters.avro.entity.ProcessingStages> getProcessingStages() {
      return processingStages;
    }

    /**
      * Sets the value of the 'processingStages' field.
      * @param value The value of 'processingStages'.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.TaskDetailsEvent.Builder setProcessingStages(java.util.List<com.opentext.bn.converters.avro.entity.ProcessingStages> value) {
      validate(fields()[1], value);
      this.processingStages = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'processingStages' field has been set.
      * @return True if the 'processingStages' field has been set, false otherwise.
      */
    public boolean hasProcessingStages() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'processingStages' field.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.TaskDetailsEvent.Builder clearProcessingStages() {
      processingStages = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'taskId' field.
      * @return The value.
      */
    public java.lang.String getTaskId() {
      return taskId;
    }

    /**
      * Sets the value of the 'taskId' field.
      * @param value The value of 'taskId'.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.TaskDetailsEvent.Builder setTaskId(java.lang.String value) {
      validate(fields()[2], value);
      this.taskId = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'taskId' field has been set.
      * @return True if the 'taskId' field has been set, false otherwise.
      */
    public boolean hasTaskId() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'taskId' field.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.TaskDetailsEvent.Builder clearTaskId() {
      taskId = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'tasksDetails' field.
      * @return The value.
      */
    public java.util.List<com.opentext.bn.converters.avro.entity.ProcessingParam> getTasksDetails() {
      return tasksDetails;
    }

    /**
      * Sets the value of the 'tasksDetails' field.
      * @param value The value of 'tasksDetails'.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.TaskDetailsEvent.Builder setTasksDetails(java.util.List<com.opentext.bn.converters.avro.entity.ProcessingParam> value) {
      validate(fields()[3], value);
      this.tasksDetails = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'tasksDetails' field has been set.
      * @return True if the 'tasksDetails' field has been set, false otherwise.
      */
    public boolean hasTasksDetails() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'tasksDetails' field.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.TaskDetailsEvent.Builder clearTasksDetails() {
      tasksDetails = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'transactionId' field.
      * @return The value.
      */
    public java.lang.String getTransactionId() {
      return transactionId;
    }

    /**
      * Sets the value of the 'transactionId' field.
      * @param value The value of 'transactionId'.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.TaskDetailsEvent.Builder setTransactionId(java.lang.String value) {
      validate(fields()[4], value);
      this.transactionId = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'transactionId' field has been set.
      * @return True if the 'transactionId' field has been set, false otherwise.
      */
    public boolean hasTransactionId() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'transactionId' field.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.TaskDetailsEvent.Builder clearTransactionId() {
      transactionId = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
      * Gets the value of the 'payloadRef' field.
      * @return The value.
      */
    public com.opentext.bn.converters.avro.entity.PayloadRef getPayloadRef() {
      return payloadRef;
    }

    /**
      * Sets the value of the 'payloadRef' field.
      * @param value The value of 'payloadRef'.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.TaskDetailsEvent.Builder setPayloadRef(com.opentext.bn.converters.avro.entity.PayloadRef value) {
      validate(fields()[5], value);
      this.payloadRefBuilder = null;
      this.payloadRef = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'payloadRef' field has been set.
      * @return True if the 'payloadRef' field has been set, false otherwise.
      */
    public boolean hasPayloadRef() {
      return fieldSetFlags()[5];
    }

    /**
     * Gets the Builder instance for the 'payloadRef' field and creates one if it doesn't exist yet.
     * @return This builder.
     */
    public com.opentext.bn.converters.avro.entity.PayloadRef.Builder getPayloadRefBuilder() {
      if (payloadRefBuilder == null) {
        if (hasPayloadRef()) {
          setPayloadRefBuilder(com.opentext.bn.converters.avro.entity.PayloadRef.newBuilder(payloadRef));
        } else {
          setPayloadRefBuilder(com.opentext.bn.converters.avro.entity.PayloadRef.newBuilder());
        }
      }
      return payloadRefBuilder;
    }

    /**
     * Sets the Builder instance for the 'payloadRef' field
     * @param value The builder instance that must be set.
     * @return This builder.
     */
    public com.opentext.bn.converters.avro.entity.TaskDetailsEvent.Builder setPayloadRefBuilder(com.opentext.bn.converters.avro.entity.PayloadRef.Builder value) {
      clearPayloadRef();
      payloadRefBuilder = value;
      return this;
    }

    /**
     * Checks whether the 'payloadRef' field has an active Builder instance
     * @return True if the 'payloadRef' field has an active Builder instance
     */
    public boolean hasPayloadRefBuilder() {
      return payloadRefBuilder != null;
    }

    /**
      * Clears the value of the 'payloadRef' field.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.TaskDetailsEvent.Builder clearPayloadRef() {
      payloadRef = null;
      payloadRefBuilder = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    /**
      * Gets the value of the 'transactionCategory' field.
      * @return The value.
      */
    public java.lang.String getTransactionCategory() {
      return transactionCategory;
    }

    /**
      * Sets the value of the 'transactionCategory' field.
      * @param value The value of 'transactionCategory'.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.TaskDetailsEvent.Builder setTransactionCategory(java.lang.String value) {
      validate(fields()[6], value);
      this.transactionCategory = value;
      fieldSetFlags()[6] = true;
      return this;
    }

    /**
      * Checks whether the 'transactionCategory' field has been set.
      * @return True if the 'transactionCategory' field has been set, false otherwise.
      */
    public boolean hasTransactionCategory() {
      return fieldSetFlags()[6];
    }


    /**
      * Clears the value of the 'transactionCategory' field.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.TaskDetailsEvent.Builder clearTransactionCategory() {
      transactionCategory = null;
      fieldSetFlags()[6] = false;
      return this;
    }

    /**
      * Gets the value of the 'isCascadeTransactionCategory' field.
      * @return The value.
      */
    public java.lang.Boolean getIsCascadeTransactionCategory() {
      return isCascadeTransactionCategory;
    }

    /**
      * Sets the value of the 'isCascadeTransactionCategory' field.
      * @param value The value of 'isCascadeTransactionCategory'.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.TaskDetailsEvent.Builder setIsCascadeTransactionCategory(java.lang.Boolean value) {
      validate(fields()[7], value);
      this.isCascadeTransactionCategory = value;
      fieldSetFlags()[7] = true;
      return this;
    }

    /**
      * Checks whether the 'isCascadeTransactionCategory' field has been set.
      * @return True if the 'isCascadeTransactionCategory' field has been set, false otherwise.
      */
    public boolean hasIsCascadeTransactionCategory() {
      return fieldSetFlags()[7];
    }


    /**
      * Clears the value of the 'isCascadeTransactionCategory' field.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.TaskDetailsEvent.Builder clearIsCascadeTransactionCategory() {
      isCascadeTransactionCategory = null;
      fieldSetFlags()[7] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public TaskDetailsEvent build() {
      try {
        TaskDetailsEvent record = new TaskDetailsEvent();
        record.processId = fieldSetFlags()[0] ? this.processId : (java.lang.String) defaultValue(fields()[0]);
        record.processingStages = fieldSetFlags()[1] ? this.processingStages : (java.util.List<com.opentext.bn.converters.avro.entity.ProcessingStages>) defaultValue(fields()[1]);
        record.taskId = fieldSetFlags()[2] ? this.taskId : (java.lang.String) defaultValue(fields()[2]);
        record.tasksDetails = fieldSetFlags()[3] ? this.tasksDetails : (java.util.List<com.opentext.bn.converters.avro.entity.ProcessingParam>) defaultValue(fields()[3]);
        record.transactionId = fieldSetFlags()[4] ? this.transactionId : (java.lang.String) defaultValue(fields()[4]);
        if (payloadRefBuilder != null) {
          record.payloadRef = this.payloadRefBuilder.build();
        } else {
          record.payloadRef = fieldSetFlags()[5] ? this.payloadRef : (com.opentext.bn.converters.avro.entity.PayloadRef) defaultValue(fields()[5]);
        }
        record.transactionCategory = fieldSetFlags()[6] ? this.transactionCategory : (java.lang.String) defaultValue(fields()[6]);
        record.isCascadeTransactionCategory = fieldSetFlags()[7] ? this.isCascadeTransactionCategory : (java.lang.Boolean) defaultValue(fields()[7]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<TaskDetailsEvent>
    WRITER$ = (org.apache.avro.io.DatumWriter<TaskDetailsEvent>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<TaskDetailsEvent>
    READER$ = (org.apache.avro.io.DatumReader<TaskDetailsEvent>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
