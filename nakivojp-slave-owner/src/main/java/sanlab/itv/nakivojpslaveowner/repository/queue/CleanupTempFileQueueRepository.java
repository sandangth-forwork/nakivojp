package sanlab.itv.nakivojpslaveowner.repository.queue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;
import sanlab.itv.nakivojpshared.constant.EJobType;

@Component
@RequiredArgsConstructor
public class CleanupTempFileQueueRepository implements JobQueueRepository {

    @Getter
    private final StreamBridge streamBridge;

    @Override
    public EJobType getType() {
        return EJobType.CLEANUP_TEMP_FILES;
    }

    @Override
    public String getOutChannel() {
        return "cleanupTempFile-out-0";
    }

}
