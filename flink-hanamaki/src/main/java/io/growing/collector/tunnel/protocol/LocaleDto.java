// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: context.proto

package io.growing.collector.tunnel.protocol;

/**
 * Protobuf type {@code io.growing.tunnel.protocol.LocaleDto}
 */
public final class LocaleDto extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:io.growing.tunnel.protocol.LocaleDto)
    LocaleDtoOrBuilder {
private static final long serialVersionUID = 0L;
  // Use LocaleDto.newBuilder() to construct.
  private LocaleDto(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private LocaleDto() {
    language_ = "";
    timezone_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new LocaleDto();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private LocaleDto(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            language_ = s;
            break;
          }
          case 18: {
            java.lang.String s = input.readStringRequireUtf8();

            timezone_ = s;
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return io.growing.collector.tunnel.protocol.ContextProtocol.internal_static_io_growing_tunnel_protocol_LocaleDto_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return io.growing.collector.tunnel.protocol.ContextProtocol.internal_static_io_growing_tunnel_protocol_LocaleDto_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            io.growing.collector.tunnel.protocol.LocaleDto.class, io.growing.collector.tunnel.protocol.LocaleDto.Builder.class);
  }

  public static final int LANGUAGE_FIELD_NUMBER = 1;
  private volatile java.lang.Object language_;
  /**
   * <code>string language = 1;</code>
   * @return The language.
   */
  @java.lang.Override
  public java.lang.String getLanguage() {
    java.lang.Object ref = language_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      language_ = s;
      return s;
    }
  }
  /**
   * <code>string language = 1;</code>
   * @return The bytes for language.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getLanguageBytes() {
    java.lang.Object ref = language_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      language_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int TIMEZONE_FIELD_NUMBER = 2;
  private volatile java.lang.Object timezone_;
  /**
   * <code>string timezone = 2;</code>
   * @return The timezone.
   */
  @java.lang.Override
  public java.lang.String getTimezone() {
    java.lang.Object ref = timezone_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      timezone_ = s;
      return s;
    }
  }
  /**
   * <code>string timezone = 2;</code>
   * @return The bytes for timezone.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getTimezoneBytes() {
    java.lang.Object ref = timezone_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      timezone_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!getLanguageBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, language_);
    }
    if (!getTimezoneBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, timezone_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getLanguageBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, language_);
    }
    if (!getTimezoneBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, timezone_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof io.growing.collector.tunnel.protocol.LocaleDto)) {
      return super.equals(obj);
    }
    io.growing.collector.tunnel.protocol.LocaleDto other = (io.growing.collector.tunnel.protocol.LocaleDto) obj;

    if (!getLanguage()
        .equals(other.getLanguage())) return false;
    if (!getTimezone()
        .equals(other.getTimezone())) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + LANGUAGE_FIELD_NUMBER;
    hash = (53 * hash) + getLanguage().hashCode();
    hash = (37 * hash) + TIMEZONE_FIELD_NUMBER;
    hash = (53 * hash) + getTimezone().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static io.growing.collector.tunnel.protocol.LocaleDto parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.growing.collector.tunnel.protocol.LocaleDto parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.growing.collector.tunnel.protocol.LocaleDto parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.growing.collector.tunnel.protocol.LocaleDto parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.growing.collector.tunnel.protocol.LocaleDto parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.growing.collector.tunnel.protocol.LocaleDto parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.growing.collector.tunnel.protocol.LocaleDto parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static io.growing.collector.tunnel.protocol.LocaleDto parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.growing.collector.tunnel.protocol.LocaleDto parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static io.growing.collector.tunnel.protocol.LocaleDto parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.growing.collector.tunnel.protocol.LocaleDto parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static io.growing.collector.tunnel.protocol.LocaleDto parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(io.growing.collector.tunnel.protocol.LocaleDto prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code io.growing.tunnel.protocol.LocaleDto}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:io.growing.tunnel.protocol.LocaleDto)
      io.growing.collector.tunnel.protocol.LocaleDtoOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return io.growing.collector.tunnel.protocol.ContextProtocol.internal_static_io_growing_tunnel_protocol_LocaleDto_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return io.growing.collector.tunnel.protocol.ContextProtocol.internal_static_io_growing_tunnel_protocol_LocaleDto_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              io.growing.collector.tunnel.protocol.LocaleDto.class, io.growing.collector.tunnel.protocol.LocaleDto.Builder.class);
    }

    // Construct using io.growing.collector.tunnel.protocol.LocaleDto.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      language_ = "";

      timezone_ = "";

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return io.growing.collector.tunnel.protocol.ContextProtocol.internal_static_io_growing_tunnel_protocol_LocaleDto_descriptor;
    }

    @java.lang.Override
    public io.growing.collector.tunnel.protocol.LocaleDto getDefaultInstanceForType() {
      return io.growing.collector.tunnel.protocol.LocaleDto.getDefaultInstance();
    }

    @java.lang.Override
    public io.growing.collector.tunnel.protocol.LocaleDto build() {
      io.growing.collector.tunnel.protocol.LocaleDto result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public io.growing.collector.tunnel.protocol.LocaleDto buildPartial() {
      io.growing.collector.tunnel.protocol.LocaleDto result = new io.growing.collector.tunnel.protocol.LocaleDto(this);
      result.language_ = language_;
      result.timezone_ = timezone_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof io.growing.collector.tunnel.protocol.LocaleDto) {
        return mergeFrom((io.growing.collector.tunnel.protocol.LocaleDto)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(io.growing.collector.tunnel.protocol.LocaleDto other) {
      if (other == io.growing.collector.tunnel.protocol.LocaleDto.getDefaultInstance()) return this;
      if (!other.getLanguage().isEmpty()) {
        language_ = other.language_;
        onChanged();
      }
      if (!other.getTimezone().isEmpty()) {
        timezone_ = other.timezone_;
        onChanged();
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      io.growing.collector.tunnel.protocol.LocaleDto parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (io.growing.collector.tunnel.protocol.LocaleDto) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object language_ = "";
    /**
     * <code>string language = 1;</code>
     * @return The language.
     */
    public java.lang.String getLanguage() {
      java.lang.Object ref = language_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        language_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string language = 1;</code>
     * @return The bytes for language.
     */
    public com.google.protobuf.ByteString
        getLanguageBytes() {
      java.lang.Object ref = language_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        language_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string language = 1;</code>
     * @param value The language to set.
     * @return This builder for chaining.
     */
    public Builder setLanguage(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      language_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string language = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearLanguage() {
      
      language_ = getDefaultInstance().getLanguage();
      onChanged();
      return this;
    }
    /**
     * <code>string language = 1;</code>
     * @param value The bytes for language to set.
     * @return This builder for chaining.
     */
    public Builder setLanguageBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      language_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object timezone_ = "";
    /**
     * <code>string timezone = 2;</code>
     * @return The timezone.
     */
    public java.lang.String getTimezone() {
      java.lang.Object ref = timezone_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        timezone_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string timezone = 2;</code>
     * @return The bytes for timezone.
     */
    public com.google.protobuf.ByteString
        getTimezoneBytes() {
      java.lang.Object ref = timezone_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        timezone_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string timezone = 2;</code>
     * @param value The timezone to set.
     * @return This builder for chaining.
     */
    public Builder setTimezone(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      timezone_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string timezone = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearTimezone() {
      
      timezone_ = getDefaultInstance().getTimezone();
      onChanged();
      return this;
    }
    /**
     * <code>string timezone = 2;</code>
     * @param value The bytes for timezone to set.
     * @return This builder for chaining.
     */
    public Builder setTimezoneBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      timezone_ = value;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:io.growing.tunnel.protocol.LocaleDto)
  }

  // @@protoc_insertion_point(class_scope:io.growing.tunnel.protocol.LocaleDto)
  private static final io.growing.collector.tunnel.protocol.LocaleDto DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new io.growing.collector.tunnel.protocol.LocaleDto();
  }

  public static io.growing.collector.tunnel.protocol.LocaleDto getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<LocaleDto>
      PARSER = new com.google.protobuf.AbstractParser<LocaleDto>() {
    @java.lang.Override
    public LocaleDto parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new LocaleDto(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<LocaleDto> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<LocaleDto> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public io.growing.collector.tunnel.protocol.LocaleDto getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

