package sanlab.itv.nakivojpslaveowner.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import sanlab.itv.nakivojpshared.constant.EJobStatus;
import sanlab.itv.nakivojpshared.eventrequest.JobResultEvent;
import sanlab.itv.nakivojpslaveowner.repository.rds.JobRepository;

import java.util.function.Consumer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobResultConsumer {

    private final JobRepository jobRepository;

    @Bean
    Consumer<Message<JobResultEvent>> sendJobResultHandler() {
        return message -> {
            var event = message.getPayload();
            log.info("Job ID {}, status {}", event.jobId(), event.status());
            jobRepository.updateResult(event.jobId(), EJobStatus.fromStr(event.status()), event.errorMessage());
        } ;
    }

}
