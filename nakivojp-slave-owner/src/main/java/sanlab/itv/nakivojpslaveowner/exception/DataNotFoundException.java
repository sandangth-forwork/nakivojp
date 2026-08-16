package sanlab.itv.nakivojpslaveowner.exception;

import sanlab.itv.nakivojpshared.exception.NakivoJpRuntimeException;

public class DataNotFoundException extends NakivoJpRuntimeException {

    private static final String JOB_WITH_ID_NOT_FOUND = "Job with ID %s not found";


    public DataNotFoundException(String msg) {
        super(msg);
    }

    public DataNotFoundException(Throwable throwable, String msg) {
        super(throwable, msg);
    }

    public static DataNotFoundException jobWithId(String id) {
        return new DataNotFoundException(JOB_WITH_ID_NOT_FOUND.formatted(id));
    }

}
