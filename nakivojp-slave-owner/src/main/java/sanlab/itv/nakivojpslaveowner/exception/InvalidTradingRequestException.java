package sanlab.itv.nakivojpslaveowner.exception;

public class InvalidTradingRequestException extends AbstractException {

    private final String msg;

    public InvalidTradingRequestException(String msg) {
        super(msg);
        this.msg = msg;
    }

    @Override
    public String message() {
        return this.msg;
    }

}
