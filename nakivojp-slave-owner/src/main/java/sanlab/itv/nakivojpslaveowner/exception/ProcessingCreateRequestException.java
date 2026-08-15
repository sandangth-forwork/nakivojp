package sanlab.itv.nakivojpslaveowner.exception;

public class ProcessingCreateRequestException extends NakivoJpRuntimeException {

    private static final String UNABLE_TO_HASH_CREATING_REQUEST = "Unable to hash a creating request.";

    public ProcessingCreateRequestException(String msg) {
        super(msg);
    }

    public ProcessingCreateRequestException(Throwable throwable, String msg) {
        super(throwable, msg);
    }

    public static ProcessingCreateRequestException hashingRequest() {
        return new ProcessingCreateRequestException(UNABLE_TO_HASH_CREATING_REQUEST);
    }

}
