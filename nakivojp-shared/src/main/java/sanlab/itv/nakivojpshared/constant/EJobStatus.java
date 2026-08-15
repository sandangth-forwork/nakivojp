package sanlab.itv.nakivojpshared.constant;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

public enum EJobStatus {

    PENDING, PROCESSING, COMPLETED, FAILED,
    ;

    public static EJobStatus fromStr(String status) {
        if (ObjectUtils.isEmpty(status)) return PENDING;
        try {
            return EJobStatus.valueOf(StringUtils.upperCase(status));
        } catch (IllegalArgumentException ignored) {
            return PENDING;
        }
    }

}
