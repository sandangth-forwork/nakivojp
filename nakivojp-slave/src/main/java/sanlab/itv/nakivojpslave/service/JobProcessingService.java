package sanlab.itv.nakivojpslave.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sanlab.itv.nakivojpshared.eventrequest.JobProcessingEvent;
import sanlab.itv.nakivojpslave.annotation.NakivoJpJobHandler;
import sanlab.itv.nakivojpslave.exception.JobProcessIngException;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobProcessingService {

    private static final String FAILED_MESSAGE_PATTERN = "%s failed";
    private static final long DELAY_TIME_MS = 500L;

    @NakivoJpJobHandler(jobName = "Sending email job")
    public void sendEmail(JobProcessingEvent event) {
        process("Sending email job", event);
    }

    @NakivoJpJobHandler(jobName = "Sending SMS job")
    public void sendSms(JobProcessingEvent event) {
        process("Sending SMS job", event);
    }

    @NakivoJpJobHandler(jobName = "Cleaning up temp file job")
    public void cleanupTempFile(JobProcessingEvent event) {
        process("Cleaning up temp file job", event);
    }

    @NakivoJpJobHandler(jobName = "Processing video job")
    public void processVideo(JobProcessingEvent event) {
        process("Processing video job", event);
    }

    @NakivoJpJobHandler(jobName = "Scraping website job")
    public void scrapeWebsite(JobProcessingEvent event) {
        process("Scraping website job", event);
    }

    @NakivoJpJobHandler(jobName = "Syncing data job")
    public void syncExternalData(JobProcessingEvent event) {
        process("Syncing data job", event);
    }

    private void process(String jobDescription, JobProcessingEvent event) {
        try {
            Thread.sleep(DELAY_TIME_MS);
            log.info("Job ID: {}", event.jobId());
            log.info("Job Payload {}", event.payload());
            checkPayload(jobDescription, event.payload());
            Thread.sleep(DELAY_TIME_MS);
        } catch (InterruptedException ex) {
            log.warn(ex.getMessage());
        }
    }

    private void checkPayload(String jobDescription, JsonNode payload) {
        if (payload != null && payload.path("fail").asBoolean(false)) {
            throw new JobProcessIngException(FAILED_MESSAGE_PATTERN.formatted(jobDescription));
        }
    }

}
