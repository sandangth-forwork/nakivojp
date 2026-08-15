package sanlab.itv.nakivojpslave.repository.queue;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import sanlab.itv.nakivojpshared.eventrequest.JobResultEvent;

@Component
@RequiredArgsConstructor
public class JobResultQueueRepository {

    private static final String JOB_RESULT_OUT_CHANNEL = "sendJobResult-out-0";
    private final StreamBridge streamBridge;

    public void enqueue(JobResultEvent event) {
        var message = MessageBuilder
            .withPayload(event)
            .build();
        streamBridge.send(JOB_RESULT_OUT_CHANNEL, message);

    }
}
