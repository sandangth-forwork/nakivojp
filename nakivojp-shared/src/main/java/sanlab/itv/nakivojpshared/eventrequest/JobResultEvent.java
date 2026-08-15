package sanlab.itv.nakivojpshared.eventrequest;

import lombok.Builder;

import java.util.UUID;

@Builder
public record JobResultEvent(
    UUID jobId, String status, String errorMessage
) {}
