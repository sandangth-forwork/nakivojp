package sanlab.itv.nakivojpslaveowner.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.util.internal.StringUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sanlab.itv.nakivojpshared.constant.EJobStatus;
import sanlab.itv.nakivojpshared.constant.EJobType;
import sanlab.itv.nakivojpslaveowner.dto.CollectionQueryRequestDto;
import sanlab.itv.nakivojpshared.request.CreateJobRequest;
import sanlab.itv.nakivojpslaveowner.dto.CollectionQueryResponseDto;
import sanlab.itv.nakivojpslaveowner.utils.CollectionQueryUtils;
import sanlab.itv.nakivojpshared.utils.DateTimeUtils;
import sanlab.itv.nakivojpslaveowner.dto.JobDto;
import sanlab.itv.nakivojpslaveowner.exception.DataNotFoundException;
import sanlab.itv.nakivojpslaveowner.exception.DuplicatedRequestException;
import sanlab.itv.nakivojpslaveowner.model.Job;
import sanlab.itv.nakivojpslaveowner.repository.rds.JobRepository;

import java.util.List;
import java.util.UUID;

@Service
public class CrudJobService {

    private final JobRepository jobRepository;
    private final HashingRequestService hashingRequestService;
    private final ObjectMapper objMapper;

    public CrudJobService(JobRepository jobRepository, HashingRequestService hashingRequestService, ObjectMapper standardObjectMapper) {
        this.jobRepository = jobRepository;
        this.hashingRequestService = hashingRequestService;
        this.objMapper = standardObjectMapper;
    }

    @Transactional(readOnly = true)
    public CollectionQueryResponseDto<JobDto> getAll(CollectionQueryRequestDto req) {
        PageRequest pageRequest = CollectionQueryUtils.toPageRequest(req);
        String status = StringUtils.isEmpty(req.getStatus()) ? StringUtil.EMPTY_STRING : EJobStatus.fromStr(req.getStatus()).name();
        Page<Job> pageResult = jobRepository.getAllByStatus(status, pageRequest);
        return CollectionQueryResponseDto.<JobDto>builder()
            .total(pageResult.getTotalElements())
            .totalPages(pageResult.getTotalPages())
            .page(pageResult.getNumber())
            .data(toDto(pageResult.getContent()))
            .build();
    }

    @Transactional(readOnly = true)
    public JobDto getDetails(UUID id) {
        var job = jobRepository.findFirstById(id)
            .orElseThrow(() -> DataNotFoundException.jobWithId(id.toString()));
        return toDto(job);
    }

    @Transactional
    public JobDto create(CreateJobRequest req) {
        String requestHash = hashingRequestService.hash(req);
        jobRepository.getWaitingJobByHash(requestHash)
            .ifPresent(inner -> {
                throw DuplicatedRequestException.jobExists(inner.toString());
            });
        var savedJob = jobRepository.save(Job.builder()
            .type(EJobType.fromStr(req.getType()).name())
            .status(EJobStatus.PENDING.name())
            .retryCount(0L)
            .requestHash(requestHash)
            .payload(objMapper.valueToTree(req.getPayload()))
            .build());
        return toDto(savedJob);
    }

    private static List<JobDto> toDto(List<Job> job) {
        return CollectionUtils.emptyIfNull(job).stream().map(CrudJobService::toDto).toList();
    }

    private static JobDto toDto(Job job) {
        return JobDto.builder()
            .id(job.getId())
            .type(job.getType())
            .status(job.getStatus())
            .errorMessage(job.getErrorMessage())
            .payload(job.getPayload())
            .createdAt(DateTimeUtils.instantToEpochMilli(job.getCreatedAt()))
            .updatedAt(DateTimeUtils.instantToEpochMilli(job.getUpdatedAt()))
            .build();
    }


}
