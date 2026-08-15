package sanlab.itv.nakivojpshared.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CollectionQueryRequest {

    private String status;
    private Integer page;
    private Integer size;

}
