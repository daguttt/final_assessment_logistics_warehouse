package com.riwi.final_assessment_logistics_warehouse.common.infrastructure.exception_handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class JwtExceptionHandlers {
  @ExceptionHandler(ExpiredJwtException.class)
  public ProblemDetail handleExpiredJwtException(ExpiredJwtException exception) {
    return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Token expired");
  }

  @ExceptionHandler(MalformedJwtException.class)
  public ProblemDetail handleMalformedJwtException(MalformedJwtException exception) {
    log.debug("Calling handleMalformedJwtException");
    return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Token not supported");
  }

  @ExceptionHandler(SignatureException.class)
  public ProblemDetail handleSignatureException(SignatureException exception) {
    return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Invalid token signature");
  }

}