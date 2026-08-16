package sanlab.itv.nakivojpshared.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static sanlab.itv.nakivojpshared.constant.EJobType.CLEANUP_TEMP_FILES;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CleanupTempFileRequest implements CreateJobRequest {

    private Payload payload;

    @Override
    public String getType() {
        return CLEANUP_TEMP_FILES.name();
    }

    @Override
    public CreateJobRequestPayload getPayload() {
        return payload;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Payload implements CreateJobRequestPayload {
        private String directory;
        private Boolean fail;
    }

}
