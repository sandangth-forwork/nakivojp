package sanlab.itv.nakivojpslaveowner.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import sanlab.itv.nakivojpslaveowner.dto.CollectionQueryRequestDto;

import java.util.Optional;

@UtilityClass
public class CollectionQueryUtils {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;

    public static PageRequest toPageRequest(CollectionQueryRequestDto req) {
        var reqOptional = Optional.ofNullable(req);
        return PageRequest.of(
            reqOptional.map(CollectionQueryRequestDto::getPage).orElse(DEFAULT_PAGE),
            reqOptional.map(CollectionQueryRequestDto::getSize).orElse(DEFAULT_SIZE)
        );
    }

}
