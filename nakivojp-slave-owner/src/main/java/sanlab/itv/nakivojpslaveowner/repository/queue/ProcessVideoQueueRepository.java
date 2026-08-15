package sanlab.itv.nakivojpslaveowner.repository.queue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;
import sanlab.itv.nakivojpshared.constant.EJobType;

@Component
@RequiredArgsConstructor
public class ProcessVideoQueueRepository implements JobQueueRepository {

    @Getter
    private final StreamBridge streamBridge;

    @Override
    public EJobType getType() {
        return EJobType.PROCESS_VIDEO;
    }

    @Override
    public String getOutChannel() {
        return "processVideo-out-0";
    }
}
