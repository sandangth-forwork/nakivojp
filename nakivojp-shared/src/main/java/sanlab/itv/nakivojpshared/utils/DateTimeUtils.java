package sanlab.itv.nakivojpshared.utils;

import lombok.experimental.UtilityClass;

import java.time.Instant;

@UtilityClass
public class DateTimeUtils {

    public static Long instantToEpochMilli(Instant instant) {
        if (instant == null) return null;
        return instant.toEpochMilli();
    }

    public static Instant epochMilliToInstant(Long epochMilli) {
        if (epochMilli == null) return null;
        return Instant.ofEpochMilli(epochMilli);
    }

}
