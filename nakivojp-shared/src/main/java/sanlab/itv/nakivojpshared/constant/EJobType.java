package sanlab.itv.nakivojpshared.constant;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

public enum EJobType {

    SEND_EMAIL,
    SEND_SMS,
    PROCESS_VIDEO,
    CLEANUP_TEMP_FILES,
    SYNC_EXTERNAL_DATA,
    SCRAPE_WEBSITE,
    UNKNOWN,
    ;

    public static EJobType fromStr(String type) {
        if (ObjectUtils.isEmpty(type)) return UNKNOWN;
        try {
            return EJobType.valueOf(StringUtils.upperCase(type));
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }

}
