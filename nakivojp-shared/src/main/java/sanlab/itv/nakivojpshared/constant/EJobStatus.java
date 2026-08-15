package sanlab.itv.nakivojpshared.constant;

import org.apache.commons.lang3.StringUtils;

public enum EJobStatus {

    PENDING, PROCESSING, COMPLETED, FAILED,
    ;

    public static EJobStatus fromStr(String status) {
        try {
            return EJobStatus.valueOf(StringUtils.upperCase(status));
        } catch (IllegalArgumentException e) {
            return PENDING;
        }
    }

}
