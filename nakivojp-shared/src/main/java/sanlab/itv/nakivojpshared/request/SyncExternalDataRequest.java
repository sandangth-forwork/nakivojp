package sanlab.itv.nakivojpshared.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static sanlab.itv.nakivojpshared.constant.EJobType.SYNC_EXTERNAL_DATA;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SyncExternalDataRequest implements CreateJobRequest {

    private Payload payload;

    @Override
    public String getType() {
        return SYNC_EXTERNAL_DATA.name();
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
        private String apiEndpoint;
        private String authToken;
        private Boolean fail;
    }

}
