package sanlab.itv.nakivojpslaveowner.repository.queue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;
import sanlab.itv.nakivojpshared.constant.EJobType;
import sanlab.itv.nakivojpshared.eventrequest.JobProcessingEvent;

@Component
@RequiredArgsConstructor
public class SyncExternalDataQueueRepository implements JobQueueRepository {

    @Getter
    private final StreamBridge streamBridge;

    @Override
    public EJobType getType() {
        return EJobType.SYNC_EXTERNAL_DATA;
    }

    @Override
    public String getOutChannel() {
        return "syncExternalData-out-0";
    }
}
