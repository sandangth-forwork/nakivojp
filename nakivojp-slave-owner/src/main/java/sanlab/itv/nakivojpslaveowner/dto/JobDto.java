package sanlab.itv.nakivojpslaveowner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDto {

    private UUID id;
    private String type;
    private String status;
    private Long createdAt;
    private Long updatedAt;

}
