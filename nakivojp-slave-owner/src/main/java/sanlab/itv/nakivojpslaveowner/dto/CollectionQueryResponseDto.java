package sanlab.itv.nakivojpslaveowner.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CollectionQueryResponseDto<T> {

    private long total;
    private long page;
    private long totalPages;
    private List<T> data;

}
