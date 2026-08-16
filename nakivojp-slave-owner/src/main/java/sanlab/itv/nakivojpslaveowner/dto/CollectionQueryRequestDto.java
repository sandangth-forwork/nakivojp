package sanlab.itv.nakivojpslaveowner.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CollectionQueryRequestDto {

    private String status;
    private Integer page;
    private Integer size;

}
