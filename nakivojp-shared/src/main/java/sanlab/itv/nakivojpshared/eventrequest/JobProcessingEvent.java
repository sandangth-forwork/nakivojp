package sanlab.itv.nakivojpshared.eventrequest;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;

import java.util.UUID;

@Builder
public record JobProcessingEvent(UUID jobId, JsonNode payload) {}
