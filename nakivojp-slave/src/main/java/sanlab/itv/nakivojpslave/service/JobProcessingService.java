package sanlab.itv.nakivojpslave.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sanlab.itv.nakivojpshared.constant.EJobStatus;
import sanlab.itv.nakivojpshared.eventrequest.JobProcessingEvent;
import sanlab.itv.nakivojpshared.eventrequest.JobResultEvent;
import sanlab.itv.nakivojpslave.repository.queue.JobResultQueueRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobProcessingService {

    private static final String HIGHLIGHT_STR = " ####################### ";
    private static final long DELAY_TIME_MS = 500L;

    private final JobResultQueueRepository jobResultQueueRepository;

    public void sendEmail(JobProcessingEvent event) {
        process("Sending email job", event);
    }

    public void sendSms(JobProcessingEvent event) {
        process("Sending SMS job", event);
    }

    public void cleanupTempFile(JobProcessingEvent event) {
        process("Cleaning up temp file job", event);
    }

    public void processVideo(JobProcessingEvent event) {
        process("Processing video job", event);
    }

    public void scrapeWebsite(JobProcessingEvent event) {
        process("Scraping website job", event);
    }

    public void syncExternalData(JobProcessingEvent event) {
        process("Syncing data job", event);
    }

    private void process(String jobDescription, JobProcessingEvent event) {
        log.info("{} {} started {}", HIGHLIGHT_STR, jobDescription, HIGHLIGHT_STR);
        try {
            Thread.sleep(DELAY_TIME_MS);
            log.info("Job ID: {}", event.jobId());
            log.info("Job Payload {}", event.payload());
            Thread.sleep(DELAY_TIME_MS);
        } catch (InterruptedException e) {
            log.warn(e.getMessage());
        }
        var outEvent = JobResultEvent.builder()
                .jobId(event.jobId())
                .status(EJobStatus.COMPLETED.name())
                .build();
        jobResultQueueRepository.enqueue(outEvent);
        log.info("{} {} ended {}", HIGHLIGHT_STR, jobDescription, HIGHLIGHT_STR);
    }

}
