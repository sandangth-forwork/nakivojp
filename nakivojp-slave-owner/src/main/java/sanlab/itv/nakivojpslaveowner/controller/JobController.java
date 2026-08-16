package sanlab.itv.nakivojpslaveowner.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sanlab.itv.nakivojpslaveowner.dto.CollectionQueryRequestDto;
import sanlab.itv.nakivojpshared.request.CreateJobRequest;
import sanlab.itv.nakivojpslaveowner.dto.CollectionQueryResponseDto;
import sanlab.itv.nakivojpslaveowner.dto.JobDto;
import sanlab.itv.nakivojpslaveowner.service.CrudJobService;
import sanlab.itv.nakivojpslaveowner.service.JobProcessingService;

import java.util.UUID;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {

    private final CrudJobService crudJobService;
    private final JobProcessingService processingService;

    @GetMapping
    public ResponseEntity<CollectionQueryResponseDto<JobDto>> getAll(@ModelAttribute CollectionQueryRequestDto req) {
        var result = crudJobService.getAll(req);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDto> getDetails(@PathVariable UUID id) {
        var result = crudJobService.getDetails(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<JobDto> create(@RequestBody CreateJobRequest req) {
        var result = crudJobService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/process")
    public ResponseEntity<Void> process() {
        processingService.process();
        return ResponseEntity.accepted().build();
    }

}
