import com.twitter.chill.protobuf.ProtobufSerializer;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import io.growing.collector.tunnel.protocol.EventDto;
public class Hello {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(3);
        env.getConfig().registerTypeWithKryoSerializer(EventDto.class, ProtobufSerializer.class);
    }
}
