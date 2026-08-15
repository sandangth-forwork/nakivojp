package sanlab.itv.nakivojpslaveowner.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@JsonInclude(NON_EMPTY)
public record ErrorResponseDto(String message, Throwable details) {}
