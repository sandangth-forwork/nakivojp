package sanlab.itv.nakivojpslaveowner.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import sanlab.itv.nakivojpshared.eventrequest.JobResultEvent;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class JobResultConsumer {

    @Bean
    Consumer<Message<JobResultEvent>> sendJobResultHandler() {
        return message -> {
            var event = message.getPayload();
            log.info("Job ID {}, status {}", event.jobId(), event.status());
        } ;
    }

}
