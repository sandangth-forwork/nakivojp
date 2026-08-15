package sanlab.itv.nakivojpslave.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import sanlab.itv.nakivojpshared.eventrequest.JobProcessingEvent;
import sanlab.itv.nakivojpslave.service.JobProcessingService;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class JobConsumer {

    private final JobProcessingService jobProcessingService;

    @Bean
    public Consumer<Message<JobProcessingEvent>> sendEmailJobHandler() {
        return message -> {
            jobProcessingService.sendEmail(message.getPayload());
        };
    }

    @Bean
    public Consumer<Message<JobProcessingEvent>> sendSmsJobHandler() {
        return message -> {
            jobProcessingService.sendSms(message.getPayload());
        };
    }

    @Bean
    public Consumer<Message<JobProcessingEvent>> syncExternalDataJobHandler() {
        return message -> {
            jobProcessingService.syncExternalData(message.getPayload());
        };
    }

    @Bean
    public Consumer<Message<JobProcessingEvent>> scrapeWebsiteJobHandler() {
        return message -> {
            jobProcessingService.scrapeWebsite(message.getPayload());
        };
    }

    @Bean
    public Consumer<Message<JobProcessingEvent>> processVideoJobHandler() {
        return message -> {
            jobProcessingService.processVideo(message.getPayload());
        };
    }

    @Bean
    public Consumer<Message<JobProcessingEvent>> cleanupTempFileJobHandler() {
        return message -> {
            jobProcessingService.cleanupTempFile(message.getPayload());
        };
    }

}
