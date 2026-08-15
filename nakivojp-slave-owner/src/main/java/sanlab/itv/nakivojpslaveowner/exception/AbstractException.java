package sanlab.itv.nakivojpslaveowner.exception;

public abstract class AbstractException extends RuntimeException {

    public AbstractException(String msg) {
        super(msg);
    }

    public abstract String message();

}
