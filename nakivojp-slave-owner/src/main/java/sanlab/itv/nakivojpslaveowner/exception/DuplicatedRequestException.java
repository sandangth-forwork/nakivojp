package sanlab.itv.nakivojpslaveowner.exception;

public class DuplicatedRequestException extends NakivoJpRuntimeException {

    private static final String JOB_WITH_ID_EXISTS = "This job has already existed, id: %s";

    public DuplicatedRequestException(String msg) {
        super(msg);
    }

    public DuplicatedRequestException(Throwable throwable, String msg) {
        super(throwable, msg);
    }

    public static DuplicatedRequestException jobExists(String id) {
        return new DuplicatedRequestException(JOB_WITH_ID_EXISTS.formatted(id));
    }

}
