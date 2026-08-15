package sanlab.itv.nakivojpshared.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import sanlab.itv.nakivojpshared.request.CollectionQueryRequest;

import java.util.Optional;

@UtilityClass
public class CollectionQueryUtils {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;

    public static PageRequest toPageRequest(CollectionQueryRequest req) {
        var reqOptional = Optional.ofNullable(req);
        return PageRequest.of(
            reqOptional.map(CollectionQueryRequest::getPage).orElse(DEFAULT_PAGE),
            reqOptional.map(CollectionQueryRequest::getSize).orElse(DEFAULT_SIZE)
        );
    }

}
