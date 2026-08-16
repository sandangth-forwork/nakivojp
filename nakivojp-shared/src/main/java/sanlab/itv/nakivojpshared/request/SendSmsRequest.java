package sanlab.itv.nakivojpshared.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static sanlab.itv.nakivojpshared.constant.EJobType.SEND_SMS;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendSmsRequest implements CreateJobRequest {

    private Payload payload;

    @Override
    public String getType() {
        return SEND_SMS.name();
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
        private String phoneNumber;
        private String message;
        private Boolean fail;
    }

}
