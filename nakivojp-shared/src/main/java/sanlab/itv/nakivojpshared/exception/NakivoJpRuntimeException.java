package sanlab.itv.nakivojpshared.exception;

import lombok.Getter;

@Getter
public abstract class NakivoJpRuntimeException extends RuntimeException {

    protected String msg;

    public NakivoJpRuntimeException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public NakivoJpRuntimeException(Throwable throwable, String msg) {
        super(msg, throwable);
        this.msg = msg;
    }

}
