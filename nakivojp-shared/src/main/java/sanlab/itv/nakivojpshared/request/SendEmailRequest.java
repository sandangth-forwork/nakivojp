package sanlab.itv.nakivojpshared.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static sanlab.itv.nakivojpshared.constant.EJobType.SEND_EMAIL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendEmailRequest implements CreateJobRequest {

    private Payload payload;

    @Override
    public String getType() {
        return SEND_EMAIL.name();
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
        private Boolean fail;
    }

}
