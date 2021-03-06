// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: context.proto

package io.growing.collector.tunnel.protocol;

/**
 * Protobuf type {@code io.growing.tunnel.protocol.ScreenDto}
 */
public final class ScreenDto extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:io.growing.tunnel.protocol.ScreenDto)
    ScreenDtoOrBuilder {
private static final long serialVersionUID = 0L;
  // Use ScreenDto.newBuilder() to construct.
  private ScreenDto(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ScreenDto() {
    orientation_ = 0;
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new ScreenDto();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private ScreenDto(
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
          case 8: {

            width_ = input.readInt32();
            break;
          }
          case 16: {

            height_ = input.readInt32();
            break;
          }
          case 24: {

            density_ = input.readInt32();
            break;
          }
          case 32: {
            int rawValue = input.readEnum();

            orientation_ = rawValue;
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
    return io.growing.collector.tunnel.protocol.ContextProtocol.internal_static_io_growing_tunnel_protocol_ScreenDto_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return io.growing.collector.tunnel.protocol.ContextProtocol.internal_static_io_growing_tunnel_protocol_ScreenDto_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            io.growing.collector.tunnel.protocol.ScreenDto.class, io.growing.collector.tunnel.protocol.ScreenDto.Builder.class);
  }

  /**
   * Protobuf enum {@code io.growing.tunnel.protocol.ScreenDto.Orientation}
   */
  public enum Orientation
      implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <code>PORTRAIT = 0;</code>
     */
    PORTRAIT(0),
    /**
     * <code>LANDSCAPE = 1;</code>
     */
    LANDSCAPE(1),
    UNRECOGNIZED(-1),
    ;

    /**
     * <code>PORTRAIT = 0;</code>
     */
    public static final int PORTRAIT_VALUE = 0;
    /**
     * <code>LANDSCAPE = 1;</code>
     */
    public static final int LANDSCAPE_VALUE = 1;


    public final int getNumber() {
      if (this == UNRECOGNIZED) {
        throw new java.lang.IllegalArgumentException(
            "Can't get the number of an unknown enum value.");
      }
      return value;
    }

    /**
     * @param value The numeric wire value of the corresponding enum entry.
     * @return The enum associated with the given numeric wire value.
     * @deprecated Use {@link #forNumber(int)} instead.
     */
    @java.lang.Deprecated
    public static Orientation valueOf(int value) {
      return forNumber(value);
    }

    /**
     * @param value The numeric wire value of the corresponding enum entry.
     * @return The enum associated with the given numeric wire value.
     */
    public static Orientation forNumber(int value) {
      switch (value) {
        case 0: return PORTRAIT;
        case 1: return LANDSCAPE;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<Orientation>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static final com.google.protobuf.Internal.EnumLiteMap<
        Orientation> internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<Orientation>() {
            public Orientation findValueByNumber(int number) {
              return Orientation.forNumber(number);
            }
          };

    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      if (this == UNRECOGNIZED) {
        throw new java.lang.IllegalStateException(
            "Can't get the descriptor of an unrecognized enum value.");
      }
      return getDescriptor().getValues().get(ordinal());
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return io.growing.collector.tunnel.protocol.ScreenDto.getDescriptor().getEnumTypes().get(0);
    }

    private static final Orientation[] VALUES = values();

    public static Orientation valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      if (desc.getIndex() == -1) {
        return UNRECOGNIZED;
      }
      return VALUES[desc.getIndex()];
    }

    private final int value;

    private Orientation(int value) {
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:io.growing.tunnel.protocol.ScreenDto.Orientation)
  }

  public static final int WIDTH_FIELD_NUMBER = 1;
  private int width_;
  /**
   * <code>int32 width = 1;</code>
   * @return The width.
   */
  @java.lang.Override
  public int getWidth() {
    return width_;
  }

  public static final int HEIGHT_FIELD_NUMBER = 2;
  private int height_;
  /**
   * <code>int32 height = 2;</code>
   * @return The height.
   */
  @java.lang.Override
  public int getHeight() {
    return height_;
  }

  public static final int DENSITY_FIELD_NUMBER = 3;
  private int density_;
  /**
   * <code>int32 density = 3;</code>
   * @return The density.
   */
  @java.lang.Override
  public int getDensity() {
    return density_;
  }

  public static final int ORIENTATION_FIELD_NUMBER = 4;
  private int orientation_;
  /**
   * <code>.io.growing.tunnel.protocol.ScreenDto.Orientation orientation = 4;</code>
   * @return The enum numeric value on the wire for orientation.
   */
  @java.lang.Override public int getOrientationValue() {
    return orientation_;
  }
  /**
   * <code>.io.growing.tunnel.protocol.ScreenDto.Orientation orientation = 4;</code>
   * @return The orientation.
   */
  @java.lang.Override public io.growing.collector.tunnel.protocol.ScreenDto.Orientation getOrientation() {
    @SuppressWarnings("deprecation")
    io.growing.collector.tunnel.protocol.ScreenDto.Orientation result = io.growing.collector.tunnel.protocol.ScreenDto.Orientation.valueOf(orientation_);
    return result == null ? io.growing.collector.tunnel.protocol.ScreenDto.Orientation.UNRECOGNIZED : result;
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
    if (width_ != 0) {
      output.writeInt32(1, width_);
    }
    if (height_ != 0) {
      output.writeInt32(2, height_);
    }
    if (density_ != 0) {
      output.writeInt32(3, density_);
    }
    if (orientation_ != io.growing.collector.tunnel.protocol.ScreenDto.Orientation.PORTRAIT.getNumber()) {
      output.writeEnum(4, orientation_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (width_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, width_);
    }
    if (height_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(2, height_);
    }
    if (density_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(3, density_);
    }
    if (orientation_ != io.growing.collector.tunnel.protocol.ScreenDto.Orientation.PORTRAIT.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(4, orientation_);
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
    if (!(obj instanceof io.growing.collector.tunnel.protocol.ScreenDto)) {
      return super.equals(obj);
    }
    io.growing.collector.tunnel.protocol.ScreenDto other = (io.growing.collector.tunnel.protocol.ScreenDto) obj;

    if (getWidth()
        != other.getWidth()) return false;
    if (getHeight()
        != other.getHeight()) return false;
    if (getDensity()
        != other.getDensity()) return false;
    if (orientation_ != other.orientation_) return false;
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
    hash = (37 * hash) + WIDTH_FIELD_NUMBER;
    hash = (53 * hash) + getWidth();
    hash = (37 * hash) + HEIGHT_FIELD_NUMBER;
    hash = (53 * hash) + getHeight();
    hash = (37 * hash) + DENSITY_FIELD_NUMBER;
    hash = (53 * hash) + getDensity();
    hash = (37 * hash) + ORIENTATION_FIELD_NUMBER;
    hash = (53 * hash) + orientation_;
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static io.growing.collector.tunnel.protocol.ScreenDto parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.growing.collector.tunnel.protocol.ScreenDto parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.growing.collector.tunnel.protocol.ScreenDto parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.growing.collector.tunnel.protocol.ScreenDto parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.growing.collector.tunnel.protocol.ScreenDto parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.growing.collector.tunnel.protocol.ScreenDto parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.growing.collector.tunnel.protocol.ScreenDto parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static io.growing.collector.tunnel.protocol.ScreenDto parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.growing.collector.tunnel.protocol.ScreenDto parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static io.growing.collector.tunnel.protocol.ScreenDto parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.growing.collector.tunnel.protocol.ScreenDto parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static io.growing.collector.tunnel.protocol.ScreenDto parseFrom(
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
  public static Builder newBuilder(io.growing.collector.tunnel.protocol.ScreenDto prototype) {
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
   * Protobuf type {@code io.growing.tunnel.protocol.ScreenDto}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:io.growing.tunnel.protocol.ScreenDto)
      io.growing.collector.tunnel.protocol.ScreenDtoOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return io.growing.collector.tunnel.protocol.ContextProtocol.internal_static_io_growing_tunnel_protocol_ScreenDto_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return io.growing.collector.tunnel.protocol.ContextProtocol.internal_static_io_growing_tunnel_protocol_ScreenDto_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              io.growing.collector.tunnel.protocol.ScreenDto.class, io.growing.collector.tunnel.protocol.ScreenDto.Builder.class);
    }

    // Construct using io.growing.collector.tunnel.protocol.ScreenDto.newBuilder()
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
      width_ = 0;

      height_ = 0;

      density_ = 0;

      orientation_ = 0;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return io.growing.collector.tunnel.protocol.ContextProtocol.internal_static_io_growing_tunnel_protocol_ScreenDto_descriptor;
    }

    @java.lang.Override
    public io.growing.collector.tunnel.protocol.ScreenDto getDefaultInstanceForType() {
      return io.growing.collector.tunnel.protocol.ScreenDto.getDefaultInstance();
    }

    @java.lang.Override
    public io.growing.collector.tunnel.protocol.ScreenDto build() {
      io.growing.collector.tunnel.protocol.ScreenDto result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public io.growing.collector.tunnel.protocol.ScreenDto buildPartial() {
      io.growing.collector.tunnel.protocol.ScreenDto result = new io.growing.collector.tunnel.protocol.ScreenDto(this);
      result.width_ = width_;
      result.height_ = height_;
      result.density_ = density_;
      result.orientation_ = orientation_;
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
      if (other instanceof io.growing.collector.tunnel.protocol.ScreenDto) {
        return mergeFrom((io.growing.collector.tunnel.protocol.ScreenDto)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(io.growing.collector.tunnel.protocol.ScreenDto other) {
      if (other == io.growing.collector.tunnel.protocol.ScreenDto.getDefaultInstance()) return this;
      if (other.getWidth() != 0) {
        setWidth(other.getWidth());
      }
      if (other.getHeight() != 0) {
        setHeight(other.getHeight());
      }
      if (other.getDensity() != 0) {
        setDensity(other.getDensity());
      }
      if (other.orientation_ != 0) {
        setOrientationValue(other.getOrientationValue());
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
      io.growing.collector.tunnel.protocol.ScreenDto parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (io.growing.collector.tunnel.protocol.ScreenDto) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int width_ ;
    /**
     * <code>int32 width = 1;</code>
     * @return The width.
     */
    @java.lang.Override
    public int getWidth() {
      return width_;
    }
    /**
     * <code>int32 width = 1;</code>
     * @param value The width to set.
     * @return This builder for chaining.
     */
    public Builder setWidth(int value) {
      
      width_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 width = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearWidth() {
      
      width_ = 0;
      onChanged();
      return this;
    }

    private int height_ ;
    /**
     * <code>int32 height = 2;</code>
     * @return The height.
     */
    @java.lang.Override
    public int getHeight() {
      return height_;
    }
    /**
     * <code>int32 height = 2;</code>
     * @param value The height to set.
     * @return This builder for chaining.
     */
    public Builder setHeight(int value) {
      
      height_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 height = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearHeight() {
      
      height_ = 0;
      onChanged();
      return this;
    }

    private int density_ ;
    /**
     * <code>int32 density = 3;</code>
     * @return The density.
     */
    @java.lang.Override
    public int getDensity() {
      return density_;
    }
    /**
     * <code>int32 density = 3;</code>
     * @param value The density to set.
     * @return This builder for chaining.
     */
    public Builder setDensity(int value) {
      
      density_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 density = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearDensity() {
      
      density_ = 0;
      onChanged();
      return this;
    }

    private int orientation_ = 0;
    /**
     * <code>.io.growing.tunnel.protocol.ScreenDto.Orientation orientation = 4;</code>
     * @return The enum numeric value on the wire for orientation.
     */
    @java.lang.Override public int getOrientationValue() {
      return orientation_;
    }
    /**
     * <code>.io.growing.tunnel.protocol.ScreenDto.Orientation orientation = 4;</code>
     * @param value The enum numeric value on the wire for orientation to set.
     * @return This builder for chaining.
     */
    public Builder setOrientationValue(int value) {
      
      orientation_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>.io.growing.tunnel.protocol.ScreenDto.Orientation orientation = 4;</code>
     * @return The orientation.
     */
    @java.lang.Override
    public io.growing.collector.tunnel.protocol.ScreenDto.Orientation getOrientation() {
      @SuppressWarnings("deprecation")
      io.growing.collector.tunnel.protocol.ScreenDto.Orientation result = io.growing.collector.tunnel.protocol.ScreenDto.Orientation.valueOf(orientation_);
      return result == null ? io.growing.collector.tunnel.protocol.ScreenDto.Orientation.UNRECOGNIZED : result;
    }
    /**
     * <code>.io.growing.tunnel.protocol.ScreenDto.Orientation orientation = 4;</code>
     * @param value The orientation to set.
     * @return This builder for chaining.
     */
    public Builder setOrientation(io.growing.collector.tunnel.protocol.ScreenDto.Orientation value) {
      if (value == null) {
        throw new NullPointerException();
      }
      
      orientation_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <code>.io.growing.tunnel.protocol.ScreenDto.Orientation orientation = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearOrientation() {
      
      orientation_ = 0;
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


    // @@protoc_insertion_point(builder_scope:io.growing.tunnel.protocol.ScreenDto)
  }

  // @@protoc_insertion_point(class_scope:io.growing.tunnel.protocol.ScreenDto)
  private static final io.growing.collector.tunnel.protocol.ScreenDto DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new io.growing.collector.tunnel.protocol.ScreenDto();
  }

  public static io.growing.collector.tunnel.protocol.ScreenDto getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ScreenDto>
      PARSER = new com.google.protobuf.AbstractParser<ScreenDto>() {
    @java.lang.Override
    public ScreenDto parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new ScreenDto(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<ScreenDto> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ScreenDto> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public io.growing.collector.tunnel.protocol.ScreenDto getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

