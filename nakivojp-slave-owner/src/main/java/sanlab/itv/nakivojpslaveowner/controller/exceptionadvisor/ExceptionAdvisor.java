package sanlab.itv.nakivojpslaveowner.controller.exceptionadvisor;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sanlab.itv.nakivojpslaveowner.dto.ErrorResponseDto;
import sanlab.itv.nakivojpslaveowner.exception.DuplicatedRequestException;
import sanlab.itv.nakivojpslaveowner.exception.NakivoJpRuntimeException;
import sanlab.itv.nakivojpslaveowner.exception.DataNotFoundException;
import sanlab.itv.nakivojpslaveowner.exception.ProcessingCreateRequestException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.TOO_EARLY;

@RestControllerAdvice
public class ExceptionAdvisor {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponseDto> internalServerError(Exception ex) {
        return new ResponseEntity<>(new ErrorResponseDto(ex.getMessage(), ex.getCause()), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({DataNotFoundException.class})
    public ResponseEntity<ErrorResponseDto> notFound(NakivoJpRuntimeException ex) {
        return new ResponseEntity<>(new ErrorResponseDto(ex.getMessage(), null), NOT_FOUND);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorResponseDto> badRequest(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(new ErrorResponseDto("Cannot parse creating request payload", ex.getCause()), BAD_REQUEST);
    }

    @ExceptionHandler({ProcessingCreateRequestException.class})
    public ResponseEntity<ErrorResponseDto> badRequest(NakivoJpRuntimeException ex) {
        return new ResponseEntity<>(new ErrorResponseDto(ex.getMessage(), null), BAD_REQUEST);
    }

    @ExceptionHandler({DuplicatedRequestException.class})
    public ResponseEntity<ErrorResponseDto> tooEarly(NakivoJpRuntimeException ex) {
        return new ResponseEntity<>(new ErrorResponseDto(ex.getMessage(), null), TOO_EARLY);
    }

}
