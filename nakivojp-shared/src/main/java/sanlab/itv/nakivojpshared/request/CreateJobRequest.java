package sanlab.itv.nakivojpshared.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type",
    visible = true
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = SendEmailRequest.class, name = "SEND_EMAIL"),
    @JsonSubTypes.Type(value = SendSmsRequest.class, name = "SEND_SMS"),
    @JsonSubTypes.Type(value = ProcessVideoRequest.class, name = "PROCESS_VIDEO"),
    @JsonSubTypes.Type(value = CleanupTempFileRequest.class, name = "CLEANUP_TEMP_FILES"),
    @JsonSubTypes.Type(value = SyncExternalDataRequest.class, name = "SYNC_EXTERNAL_DATA"),
    @JsonSubTypes.Type(value = ScrapeWebsiteRequest.class, name = "SCRAPE_WEBSITE"),
})
public interface CreateJobRequest {

    String getType();
    CreateJobRequestPayload getPayload();

}
