package sanlab.itv.nakivojpslaveowner.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sanlab.itv.nakivojpshared.constant.EJobStatus;
import sanlab.itv.nakivojpshared.constant.EJobType;
import sanlab.itv.nakivojpshared.eventrequest.JobProcessingEvent;
import sanlab.itv.nakivojpslaveowner.model.Job;
import sanlab.itv.nakivojpslaveowner.repository.queue.JobQueueRepository;
import sanlab.itv.nakivojpslaveowner.repository.rds.JobRepository;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static sanlab.itv.nakivojpshared.constant.EJobType.UNKNOWN;

@Service
public class JobProcessingService {

    private final JobRepository jobRepository;
    private final Map<EJobType, JobQueueRepository> jobQueueRepositoryMap;

    public JobProcessingService(JobRepository jobRepository, List<JobQueueRepository> jobQueueRepositoryList) {
        this.jobRepository = jobRepository;
        this.jobQueueRepositoryMap = jobQueueRepositoryList.stream().collect(Collectors.toMap(JobQueueRepository::getType, Function.identity()));
    }

    @Transactional
    public void process() {
        jobRepository.getAllByProcessableStatuses()
            .stream()
            .filter(this::isNotUnknown)
            .forEach(inner -> {
                var type = EJobType.fromStr(inner.getType());
                var event = JobProcessingEvent.builder()
                    .jobId(inner.getId())
                    .payload(inner.getPayload())
                    .build();
                jobQueueRepositoryMap.get(type).enqueue(event);
                jobRepository.updateStatus(inner.getId(), EJobStatus.PROCESSING);
            });
    }

    private boolean isNotUnknown(Job job) {
        return !UNKNOWN.equals(EJobType.fromStr(job.getType()));
    }

}
