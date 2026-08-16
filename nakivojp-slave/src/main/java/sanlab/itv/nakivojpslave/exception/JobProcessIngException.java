package sanlab.itv.nakivojpslave.exception;

import sanlab.itv.nakivojpshared.exception.NakivoJpRuntimeException;

public class JobProcessIngException extends NakivoJpRuntimeException {
    public JobProcessIngException(String msg) {
        super(msg);
    }

    public JobProcessIngException(Throwable throwable, String msg) {
        super(throwable, msg);
    }
}
