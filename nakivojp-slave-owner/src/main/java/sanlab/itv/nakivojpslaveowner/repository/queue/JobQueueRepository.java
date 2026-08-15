package sanlab.itv.nakivojpslaveowner.repository.queue;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import sanlab.itv.nakivojpshared.constant.EJobType;
import sanlab.itv.nakivojpshared.eventrequest.JobProcessingEvent;

public interface JobQueueRepository {

    EJobType getType();
    String getOutChannel();
    StreamBridge getStreamBridge();

    default void enqueue(JobProcessingEvent event) {
        var message = MessageBuilder
            .withPayload(event)
            .build();
        getStreamBridge().send(getOutChannel(), message);
    }

}
