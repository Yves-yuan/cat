package serde;

import io.growing.collector.tunnel.protocol.EventDto;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

public class ProtoEventDeserializationSchema implements DeserializationSchema<EventDto> {
    @Override
    public EventDto deserialize(byte[] bytes) throws IOException {
        return EventDto.parseFrom(bytes);
    }

    @Override
    public boolean isEndOfStream(EventDto eventDto) {
        return false;
    }

    @Override
    public TypeInformation<EventDto> getProducedType() {
        return TypeInformation.of(new TypeHint<EventDto>() {
        });
    }
}
