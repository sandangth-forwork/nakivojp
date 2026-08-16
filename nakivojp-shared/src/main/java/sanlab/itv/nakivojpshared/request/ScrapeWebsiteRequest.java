package sanlab.itv.nakivojpshared.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

import static sanlab.itv.nakivojpshared.constant.EJobType.SCRAPE_WEBSITE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScrapeWebsiteRequest implements CreateJobRequest {

    private Payload payload;

    @Override
    public String getType() {
        return SCRAPE_WEBSITE.name();
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
        private String url;
        private List<String> keywords;
        private Boolean fail;
    }

}
