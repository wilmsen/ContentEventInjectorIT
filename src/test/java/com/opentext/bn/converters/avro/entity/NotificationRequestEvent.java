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
public class NotificationRequestEvent extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -2232762444191784325L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"NotificationRequestEvent\",\"namespace\":\"com.opentext.bn.converters.avro.entity\",\"fields\":[{\"name\":\"notificationId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"notificationType\",\"type\":{\"type\":\"enum\",\"name\":\"NotificationType\",\"symbols\":[\"DEFAULT_BUSINESS_POC\",\"DEFAULT_SERVICE_POC\",\"TRADING_PARTNER_POC\",\"CONTACT_ID\"]}},{\"name\":\"contactIds\",\"type\":[\"null\",{\"type\":\"array\",\"items\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}],\"default\":null},{\"name\":\"requestTimeStamp\",\"type\":\"long\"},{\"name\":\"requestorType\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"requestorId\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"category\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"transactionId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"taskId\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"destinations\",\"type\":[\"null\",{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"Destination\",\"fields\":[{\"name\":\"type\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"destinationKey\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}}],\"default\":null},{\"name\":\"notificationData\",\"type\":[\"null\",{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"NotificationData\",\"fields\":[{\"name\":\"key\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"value\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}}],\"default\":null}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<NotificationRequestEvent> ENCODER =
      new BinaryMessageEncoder<NotificationRequestEvent>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<NotificationRequestEvent> DECODER =
      new BinaryMessageDecoder<NotificationRequestEvent>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<NotificationRequestEvent> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<NotificationRequestEvent> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<NotificationRequestEvent>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this NotificationRequestEvent to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a NotificationRequestEvent from a ByteBuffer. */
  public static NotificationRequestEvent fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

   private java.lang.String notificationId;
   private com.opentext.bn.converters.avro.entity.NotificationType notificationType;
   private java.util.List<java.lang.String> contactIds;
   private long requestTimeStamp;
   private java.lang.String requestorType;
   private java.lang.String requestorId;
   private java.lang.String category;
   private java.lang.String transactionId;
   private java.lang.String taskId;
   private java.util.List<com.opentext.bn.converters.avro.entity.Destination> destinations;
   private java.util.List<com.opentext.bn.converters.avro.entity.NotificationData> notificationData;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public NotificationRequestEvent() {}

  /**
   * All-args constructor.
   * @param notificationId The new value for notificationId
   * @param notificationType The new value for notificationType
   * @param contactIds The new value for contactIds
   * @param requestTimeStamp The new value for requestTimeStamp
   * @param requestorType The new value for requestorType
   * @param requestorId The new value for requestorId
   * @param category The new value for category
   * @param transactionId The new value for transactionId
   * @param taskId The new value for taskId
   * @param destinations The new value for destinations
   * @param notificationData The new value for notificationData
   */
  public NotificationRequestEvent(java.lang.String notificationId, com.opentext.bn.converters.avro.entity.NotificationType notificationType, java.util.List<java.lang.String> contactIds, java.lang.Long requestTimeStamp, java.lang.String requestorType, java.lang.String requestorId, java.lang.String category, java.lang.String transactionId, java.lang.String taskId, java.util.List<com.opentext.bn.converters.avro.entity.Destination> destinations, java.util.List<com.opentext.bn.converters.avro.entity.NotificationData> notificationData) {
    this.notificationId = notificationId;
    this.notificationType = notificationType;
    this.contactIds = contactIds;
    this.requestTimeStamp = requestTimeStamp;
    this.requestorType = requestorType;
    this.requestorId = requestorId;
    this.category = category;
    this.transactionId = transactionId;
    this.taskId = taskId;
    this.destinations = destinations;
    this.notificationData = notificationData;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return notificationId;
    case 1: return notificationType;
    case 2: return contactIds;
    case 3: return requestTimeStamp;
    case 4: return requestorType;
    case 5: return requestorId;
    case 6: return category;
    case 7: return transactionId;
    case 8: return taskId;
    case 9: return destinations;
    case 10: return notificationData;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: notificationId = (java.lang.String)value$; break;
    case 1: notificationType = (com.opentext.bn.converters.avro.entity.NotificationType)value$; break;
    case 2: contactIds = (java.util.List<java.lang.String>)value$; break;
    case 3: requestTimeStamp = (java.lang.Long)value$; break;
    case 4: requestorType = (java.lang.String)value$; break;
    case 5: requestorId = (java.lang.String)value$; break;
    case 6: category = (java.lang.String)value$; break;
    case 7: transactionId = (java.lang.String)value$; break;
    case 8: taskId = (java.lang.String)value$; break;
    case 9: destinations = (java.util.List<com.opentext.bn.converters.avro.entity.Destination>)value$; break;
    case 10: notificationData = (java.util.List<com.opentext.bn.converters.avro.entity.NotificationData>)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'notificationId' field.
   * @return The value of the 'notificationId' field.
   */
  public java.lang.String getNotificationId() {
    return notificationId;
  }


  /**
   * Gets the value of the 'notificationType' field.
   * @return The value of the 'notificationType' field.
   */
  public com.opentext.bn.converters.avro.entity.NotificationType getNotificationType() {
    return notificationType;
  }


  /**
   * Gets the value of the 'contactIds' field.
   * @return The value of the 'contactIds' field.
   */
  public java.util.List<java.lang.String> getContactIds() {
    return contactIds;
  }


  /**
   * Gets the value of the 'requestTimeStamp' field.
   * @return The value of the 'requestTimeStamp' field.
   */
  public java.lang.Long getRequestTimeStamp() {
    return requestTimeStamp;
  }


  /**
   * Gets the value of the 'requestorType' field.
   * @return The value of the 'requestorType' field.
   */
  public java.lang.String getRequestorType() {
    return requestorType;
  }


  /**
   * Gets the value of the 'requestorId' field.
   * @return The value of the 'requestorId' field.
   */
  public java.lang.String getRequestorId() {
    return requestorId;
  }


  /**
   * Gets the value of the 'category' field.
   * @return The value of the 'category' field.
   */
  public java.lang.String getCategory() {
    return category;
  }


  /**
   * Gets the value of the 'transactionId' field.
   * @return The value of the 'transactionId' field.
   */
  public java.lang.String getTransactionId() {
    return transactionId;
  }


  /**
   * Gets the value of the 'taskId' field.
   * @return The value of the 'taskId' field.
   */
  public java.lang.String getTaskId() {
    return taskId;
  }


  /**
   * Gets the value of the 'destinations' field.
   * @return The value of the 'destinations' field.
   */
  public java.util.List<com.opentext.bn.converters.avro.entity.Destination> getDestinations() {
    return destinations;
  }


  /**
   * Gets the value of the 'notificationData' field.
   * @return The value of the 'notificationData' field.
   */
  public java.util.List<com.opentext.bn.converters.avro.entity.NotificationData> getNotificationData() {
    return notificationData;
  }


  /**
   * Creates a new NotificationRequestEvent RecordBuilder.
   * @return A new NotificationRequestEvent RecordBuilder
   */
  public static com.opentext.bn.converters.avro.entity.NotificationRequestEvent.Builder newBuilder() {
    return new com.opentext.bn.converters.avro.entity.NotificationRequestEvent.Builder();
  }

  /**
   * Creates a new NotificationRequestEvent RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new NotificationRequestEvent RecordBuilder
   */
  public static com.opentext.bn.converters.avro.entity.NotificationRequestEvent.Builder newBuilder(com.opentext.bn.converters.avro.entity.NotificationRequestEvent.Builder other) {
    return new com.opentext.bn.converters.avro.entity.NotificationRequestEvent.Builder(other);
  }

  /**
   * Creates a new NotificationRequestEvent RecordBuilder by copying an existing NotificationRequestEvent instance.
   * @param other The existing instance to copy.
   * @return A new NotificationRequestEvent RecordBuilder
   */
  public static com.opentext.bn.converters.avro.entity.NotificationRequestEvent.Builder newBuilder(com.opentext.bn.converters.avro.entity.NotificationRequestEvent other) {
    return new com.opentext.bn.converters.avro.entity.NotificationRequestEvent.Builder(other);
  }

  /**
   * RecordBuilder for NotificationRequestEvent instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<NotificationRequestEvent>
    implements org.apache.avro.data.RecordBuilder<NotificationRequestEvent> {

    private java.lang.String notificationId;
    private com.opentext.bn.converters.avro.entity.NotificationType notificationType;
    private java.util.List<java.lang.String> contactIds;
    private long requestTimeStamp;
    private java.lang.String requestorType;
    private java.lang.String requestorId;
    private java.lang.String category;
    private java.lang.String transactionId;
    private java.lang.String taskId;
    private java.util.List<com.opentext.bn.converters.avro.entity.Destination> destinations;
    private java.util.List<com.opentext.bn.converters.avro.entity.NotificationData> notificationData;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.opentext.bn.converters.avro.entity.NotificationRequestEvent.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.notificationId)) {
        this.notificationId = data().deepCopy(fields()[0].schema(), other.notificationId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.notificationType)) {
        this.notificationType = data().deepCopy(fields()[1].schema(), other.notificationType);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.contactIds)) {
        this.contactIds = data().deepCopy(fields()[2].schema(), other.contactIds);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.requestTimeStamp)) {
        this.requestTimeStamp = data().deepCopy(fields()[3].schema(), other.requestTimeStamp);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.requestorType)) {
        this.requestorType = data().deepCopy(fields()[4].schema(), other.requestorType);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.requestorId)) {
        this.requestorId = data().deepCopy(fields()[5].schema(), other.requestorId);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.category)) {
        this.category = data().deepCopy(fields()[6].schema(), other.category);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.transactionId)) {
        this.transactionId = data().deepCopy(fields()[7].schema(), other.transactionId);
        fieldSetFlags()[7] = true;
      }
      if (isValidValue(fields()[8], other.taskId)) {
        this.taskId = data().deepCopy(fields()[8].schema(), other.taskId);
        fieldSetFlags()[8] = true;
      }
      if (isValidValue(fields()[9], other.destinations)) {
        this.destinations = data().deepCopy(fields()[9].schema(), other.destinations);
        fieldSetFlags()[9] = true;
      }
      if (isValidValue(fields()[10], other.notificationData)) {
        this.notificationData = data().deepCopy(fields()[10].schema(), other.notificationData);
        fieldSetFlags()[10] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing NotificationRequestEvent instance
     * @param other The existing instance to copy.
     */
    private Builder(com.opentext.bn.converters.avro.entity.NotificationRequestEvent other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.notificationId)) {
        this.notificationId = data().deepCopy(fields()[0].schema(), other.notificationId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.notificationType)) {
        this.notificationType = data().deepCopy(fields()[1].schema(), other.notificationType);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.contactIds)) {
        this.contactIds = data().deepCopy(fields()[2].schema(), other.contactIds);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.requestTimeStamp)) {
        this.requestTimeStamp = data().deepCopy(fields()[3].schema(), other.requestTimeStamp);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.requestorType)) {
        this.requestorType = data().deepCopy(fields()[4].schema(), other.requestorType);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.requestorId)) {
        this.requestorId = data().deepCopy(fields()[5].schema(), other.requestorId);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.category)) {
        this.category = data().deepCopy(fields()[6].schema(), other.category);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.transactionId)) {
        this.transactionId = data().deepCopy(fields()[7].schema(), other.transactionId);
        fieldSetFlags()[7] = true;
      }
      if (isValidValue(fields()[8], other.taskId)) {
        this.taskId = data().deepCopy(fields()[8].schema(), other.taskId);
        fieldSetFlags()[8] = true;
      }
      if (isValidValue(fields()[9], other.destinations)) {
        this.destinations = data().deepCopy(fields()[9].schema(), other.destinations);
        fieldSetFlags()[9] = true;
      }
      if (isValidValue(fields()[10], other.notificationData)) {
        this.notificationData = data().deepCopy(fields()[10].schema(), other.notificationData);
        fieldSetFlags()[10] = true;
      }
    }

    /**
      * Gets the value of the 'notificationId' field.
      * @return The value.
      */
    public java.lang.String getNotificationId() {
      return notificationId;
    }

    /**
      * Sets the value of the 'notificationId' field.
      * @param value The value of 'notificationId'.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.NotificationRequestEvent.Builder setNotificationId(java.lang.String value) {
      validate(fields()[0], value);
      this.notificationId = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'notificationId' field has been set.
      * @return True if the 'notificationId' field has been set, false otherwise.
      */
    public boolean hasNotificationId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'notificationId' field.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.NotificationRequestEvent.Builder clearNotificationId() {
      notificationId = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'notificationType' field.
      * @return The value.
      */
    public com.opentext.bn.converters.avro.entity.NotificationType getNotificationType() {
      return notificationType;
    }

    /**
      * Sets the value of the 'notificationType' field.
      * @param value The value of 'notificationType'.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.NotificationRequestEvent.Builder setNotificationType(com.opentext.bn.converters.avro.entity.NotificationType value) {
      validate(fields()[1], value);
      this.notificationType = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'notificationType' field has been set.
      * @return True if the 'notificationType' field has been set, false otherwise.
      */
    public boolean hasNotificationType() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'notificationType' field.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.NotificationRequestEvent.Builder clearNotificationType() {
      notificationType = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'contactIds' field.
      * @return The value.
      */
    public java.util.List<java.lang.String> getContactIds() {
      return contactIds;
    }

    /**
      * Sets the value of the 'contactIds' field.
      * @param value The value of 'contactIds'.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.NotificationRequestEvent.Builder setContactIds(java.util.List<java.lang.String> value) {
      validate(fields()[2], value);
      this.contactIds = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'contactIds' field has been set.
      * @return True if the 'contactIds' field has been set, false otherwise.
      */
    public boolean hasContactIds() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'contactIds' field.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.NotificationRequestEvent.Builder clearContactIds() {
      contactIds = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'requestTimeStamp' field.
      * @return The value.
      */
    public java.lang.Long getRequestTimeStamp() {
      return requestTimeStamp;
    }

    /**
      * Sets the value of the 'requestTimeStamp' field.
      * @param value The value of 'requestTimeStamp'.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.NotificationRequestEvent.Builder setRequestTimeStamp(long value) {
      validate(fields()[3], value);
      this.requestTimeStamp = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'requestTimeStamp' field has been set.
      * @return True if the 'requestTimeStamp' field has been set, false otherwise.
      */
    public boolean hasRequestTimeStamp() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'requestTimeStamp' field.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.NotificationRequestEvent.Builder clearRequestTimeStamp() {
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'requestorType' field.
      * @return The value.
      */
    public java.lang.String getRequestorType() {
      return requestorType;
    }

    /**
      * Sets the value of the 'requestorType' field.
      * @param value The value of 'requestorType'.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.NotificationRequestEvent.Builder setRequestorType(java.lang.String value) {
      validate(fields()[4], value);
      this.requestorType = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'requestorType' field has been set.
      * @return True if the 'requestorType' field has been set, false otherwise.
      */
    public boolean hasRequestorType() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'requestorType' field.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.NotificationRequestEvent.Builder clearRequestorType() {
      requestorType = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
      * Gets the value of the 'requestorId' field.
      * @return The value.
      */
    public java.lang.String getRequestorId() {
      return requestorId;
    }

    /**
      * Sets the value of the 'requestorId' field.
      * @param value The value of 'requestorId'.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.NotificationRequestEvent.Builder setRequestorId(java.lang.String value) {
      validate(fields()[5], value);
      this.requestorId = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'requestorId' field has been set.
      * @return True if the 'requestorId' field has been set, false otherwise.
      */
    public boolean hasRequestorId() {
      return fieldSetFlags()[5];
    }


    /**
      * Clears the value of the 'requestorId' field.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.NotificationRequestEvent.Builder clearRequestorId() {
      requestorId = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    /**
      * Gets the value of the 'category' field.
      * @return The value.
      */
    public java.lang.String getCategory() {
      return category;
    }

    /**
      * Sets the value of the 'category' field.
      * @param value The value of 'category'.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.NotificationRequestEvent.Builder setCategory(java.lang.String value) {
      validate(fields()[6], value);
      this.category = value;
      fieldSetFlags()[6] = true;
      return this;
    }

    /**
      * Checks whether the 'category' field has been set.
      * @return True if the 'category' field has been set, false otherwise.
      */
    public boolean hasCategory() {
      return fieldSetFlags()[6];
    }


    /**
      * Clears the value of the 'category' field.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.NotificationRequestEvent.Builder clearCategory() {
      category = null;
      fieldSetFlags()[6] = false;
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
    public com.opentext.bn.converters.avro.entity.NotificationRequestEvent.Builder setTransactionId(java.lang.String value) {
      validate(fields()[7], value);
      this.transactionId = value;
      fieldSetFlags()[7] = true;
      return this;
    }

    /**
      * Checks whether the 'transactionId' field has been set.
      * @return True if the 'transactionId' field has been set, false otherwise.
      */
    public boolean hasTransactionId() {
      return fieldSetFlags()[7];
    }


    /**
      * Clears the value of the 'transactionId' field.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.NotificationRequestEvent.Builder clearTransactionId() {
      transactionId = null;
      fieldSetFlags()[7] = false;
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
    public com.opentext.bn.converters.avro.entity.NotificationRequestEvent.Builder setTaskId(java.lang.String value) {
      validate(fields()[8], value);
      this.taskId = value;
      fieldSetFlags()[8] = true;
      return this;
    }

    /**
      * Checks whether the 'taskId' field has been set.
      * @return True if the 'taskId' field has been set, false otherwise.
      */
    public boolean hasTaskId() {
      return fieldSetFlags()[8];
    }


    /**
      * Clears the value of the 'taskId' field.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.NotificationRequestEvent.Builder clearTaskId() {
      taskId = null;
      fieldSetFlags()[8] = false;
      return this;
    }

    /**
      * Gets the value of the 'destinations' field.
      * @return The value.
      */
    public java.util.List<com.opentext.bn.converters.avro.entity.Destination> getDestinations() {
      return destinations;
    }

    /**
      * Sets the value of the 'destinations' field.
      * @param value The value of 'destinations'.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.NotificationRequestEvent.Builder setDestinations(java.util.List<com.opentext.bn.converters.avro.entity.Destination> value) {
      validate(fields()[9], value);
      this.destinations = value;
      fieldSetFlags()[9] = true;
      return this;
    }

    /**
      * Checks whether the 'destinations' field has been set.
      * @return True if the 'destinations' field has been set, false otherwise.
      */
    public boolean hasDestinations() {
      return fieldSetFlags()[9];
    }


    /**
      * Clears the value of the 'destinations' field.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.NotificationRequestEvent.Builder clearDestinations() {
      destinations = null;
      fieldSetFlags()[9] = false;
      return this;
    }

    /**
      * Gets the value of the 'notificationData' field.
      * @return The value.
      */
    public java.util.List<com.opentext.bn.converters.avro.entity.NotificationData> getNotificationData() {
      return notificationData;
    }

    /**
      * Sets the value of the 'notificationData' field.
      * @param value The value of 'notificationData'.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.NotificationRequestEvent.Builder setNotificationData(java.util.List<com.opentext.bn.converters.avro.entity.NotificationData> value) {
      validate(fields()[10], value);
      this.notificationData = value;
      fieldSetFlags()[10] = true;
      return this;
    }

    /**
      * Checks whether the 'notificationData' field has been set.
      * @return True if the 'notificationData' field has been set, false otherwise.
      */
    public boolean hasNotificationData() {
      return fieldSetFlags()[10];
    }


    /**
      * Clears the value of the 'notificationData' field.
      * @return This builder.
      */
    public com.opentext.bn.converters.avro.entity.NotificationRequestEvent.Builder clearNotificationData() {
      notificationData = null;
      fieldSetFlags()[10] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public NotificationRequestEvent build() {
      try {
        NotificationRequestEvent record = new NotificationRequestEvent();
        record.notificationId = fieldSetFlags()[0] ? this.notificationId : (java.lang.String) defaultValue(fields()[0]);
        record.notificationType = fieldSetFlags()[1] ? this.notificationType : (com.opentext.bn.converters.avro.entity.NotificationType) defaultValue(fields()[1]);
        record.contactIds = fieldSetFlags()[2] ? this.contactIds : (java.util.List<java.lang.String>) defaultValue(fields()[2]);
        record.requestTimeStamp = fieldSetFlags()[3] ? this.requestTimeStamp : (java.lang.Long) defaultValue(fields()[3]);
        record.requestorType = fieldSetFlags()[4] ? this.requestorType : (java.lang.String) defaultValue(fields()[4]);
        record.requestorId = fieldSetFlags()[5] ? this.requestorId : (java.lang.String) defaultValue(fields()[5]);
        record.category = fieldSetFlags()[6] ? this.category : (java.lang.String) defaultValue(fields()[6]);
        record.transactionId = fieldSetFlags()[7] ? this.transactionId : (java.lang.String) defaultValue(fields()[7]);
        record.taskId = fieldSetFlags()[8] ? this.taskId : (java.lang.String) defaultValue(fields()[8]);
        record.destinations = fieldSetFlags()[9] ? this.destinations : (java.util.List<com.opentext.bn.converters.avro.entity.Destination>) defaultValue(fields()[9]);
        record.notificationData = fieldSetFlags()[10] ? this.notificationData : (java.util.List<com.opentext.bn.converters.avro.entity.NotificationData>) defaultValue(fields()[10]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<NotificationRequestEvent>
    WRITER$ = (org.apache.avro.io.DatumWriter<NotificationRequestEvent>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<NotificationRequestEvent>
    READER$ = (org.apache.avro.io.DatumReader<NotificationRequestEvent>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
