package sanlab.itv.nakivojpshared.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static sanlab.itv.nakivojpshared.constant.EJobType.PROCESS_VIDEO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessVideoRequest implements CreateJobRequest {

    private Payload payload;

    @Override
    public String getType() {
        return PROCESS_VIDEO.name();
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
        private String recipient;
        private String subject;
        private String body;
    }

}
