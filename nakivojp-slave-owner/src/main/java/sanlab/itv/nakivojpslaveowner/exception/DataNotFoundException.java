package sanlab.itv.nakivojpslaveowner.exception;

public class DataNotFoundException extends AbstractException {

    private final String msg;

    public DataNotFoundException(String msg) {
        super(msg);
        this.msg = msg;
    }

    @Override
    public String message() {
        return this.msg;
    }

}
