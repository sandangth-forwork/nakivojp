package sanlab.itv.nakivojpslaveowner.exception;

public class ServiceUnavailableException extends NakivoJpRuntimeException {

    public ServiceUnavailableException(String msg) {
        super(msg);
    }

    public ServiceUnavailableException(Throwable throwable, String msg) {
        super(throwable, msg);
    }

}
