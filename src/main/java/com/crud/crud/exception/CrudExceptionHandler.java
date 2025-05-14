package com.crud.crud.exception;

import com.crud.crud.dto.ErrorResponse;
import io.micrometer.common.util.StringUtils;
import java.time.Instant;
import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CrudExceptionHandler {
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = BadRequestException.class)
  public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException exception) {
    return ResponseEntity.badRequest()
        .body(this.buildErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage()));
  }

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(value = AccessDeniedException.class)
  public ResponseEntity<ErrorResponse> handleAccessDeniedException(
      AccessDeniedException exception) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(
            this.buildErrorResponse(
                HttpStatus.UNAUTHORIZED,
                StringUtils.isBlank(exception.getMessage())
                    ? "Unauthorized"
                    : exception.getMessage()));
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(value = EntityNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleEntityNotFoundException(
      EntityNotFoundException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(this.buildErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage()));
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(value = InternalServerErrorException.class)
  public ResponseEntity<ErrorResponse> handleInternalServerErrorException(
      InternalServerErrorException exception) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(
            this.buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                StringUtils.isBlank(exception.getMessage())
                    ? "Internal Server Error"
                    : exception.getMessage()));
  }

  /**
   * Build error response.
   *
   * @param httpStatus {@link HttpStatus}
   * @param message {@link String}
   * @return {@link ErrorResponse}
   */
  private ErrorResponse buildErrorResponse(HttpStatus httpStatus, String message) {
    return ErrorResponse.builder()
        .error(httpStatus)
        .message(message)
        .status(httpStatus.value())
        .timestamp(Date.from(Instant.now()))
        .build();
  }
}
