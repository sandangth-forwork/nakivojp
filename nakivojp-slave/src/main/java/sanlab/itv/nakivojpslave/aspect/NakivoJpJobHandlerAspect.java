package sanlab.itv.nakivojpslave.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Component;
import sanlab.itv.nakivojpshared.constant.EJobStatus;
import sanlab.itv.nakivojpshared.eventrequest.JobProcessingEvent;
import sanlab.itv.nakivojpshared.eventrequest.JobResultEvent;
import sanlab.itv.nakivojpslave.annotation.NakivoJpJobHandler;
import sanlab.itv.nakivojpslave.exception.JobProcessIngException;
import sanlab.itv.nakivojpslave.repository.queue.JobResultQueueRepository;

import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class NakivoJpJobHandlerAspect {

    private static final String HIGHLIGHT_STR = " ####################### ";
    private static final int MAX_ATTEMPT = 3;

    private final JobResultQueueRepository jobResultQueueRepository;

    @Around("@annotation(jobHandler)")
    public Object handleJob(ProceedingJoinPoint joinPoint, NakivoJpJobHandler jobHandler) throws Throwable {
        log.info("{} {} started {}", HIGHLIGHT_STR, jobHandler.jobName(), HIGHLIGHT_STR);
        var eventOpt = extractEvent(joinPoint);
        int attempt = currentAttempt();
        try {
            Object result = joinPoint.proceed();
            if (eventOpt.isEmpty()) return result;
            publishCompleted(eventOpt.get());
            return result;
        } catch (JobProcessIngException ex) {
            if (attempt >= MAX_ATTEMPT) {
                log.error("Job {} FAILED after {} attempts: {}", jobHandler.jobName(), attempt, ex.getMessage());
                eventOpt.ifPresent(inner -> publishFailed(inner, ex.getMessage()));
                return null;
            }
            log.error("Job {} attempt {} failed, retrying: {}", jobHandler.jobName(), attempt, ex.getMessage());
            throw ex;
        } finally {
            log.info("{} {} ended {}", HIGHLIGHT_STR, jobHandler.jobName(), HIGHLIGHT_STR);
        }
    }

    private Optional<JobProcessingEvent> extractEvent(ProceedingJoinPoint joinPoint) {
        for (Object argument : joinPoint.getArgs()) {
            if (argument instanceof JobProcessingEvent event) {
                return Optional.of(event);
            }
        }
        return Optional.empty();
    }

    private int currentAttempt() {
        RetryContext context = RetrySynchronizationManager.getContext();
        return context == null ? 1 : context.getRetryCount() + 1;
    }

    private void publishCompleted(JobProcessingEvent event) {
        JobResultEvent result = JobResultEvent.builder()
            .jobId(event.jobId())
            .status(EJobStatus.COMPLETED.name())
            .build();
        jobResultQueueRepository.enqueue(result);
    }

    private void publishFailed(JobProcessingEvent event, String errorMessage) {
        JobResultEvent result = JobResultEvent.builder()
            .jobId(event.jobId())
            .status(EJobStatus.FAILED.name())
            .errorMessage(errorMessage)
            .build();
        jobResultQueueRepository.enqueue(result);
    }

}
