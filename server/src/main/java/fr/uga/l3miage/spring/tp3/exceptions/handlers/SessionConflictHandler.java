package fr.uga.l3miage.spring.tp3.exceptions.handlers;


import fr.uga.l3miage.spring.tp3.exceptions.SessionConflictResponse;
import fr.uga.l3miage.spring.tp3.exceptions.rest.SessionConflictRestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class SessionConflictHandler {

    @ExceptionHandler(SessionConflictRestException.class)
    public ResponseEntity<SessionConflictResponse> handle(HttpServletRequest httpServletRequest, Exception exception){
        SessionConflictRestException sessionConflictRestException = (SessionConflictRestException) exception;
        SessionConflictResponse response = SessionConflictResponse
                .builder()
                .sessionStatus(sessionConflictRestException.getSessionStatus())
                .errorMessage(sessionConflictRestException.getMessage())
                .uri(httpServletRequest.getRequestURI())
                .build();
        return ResponseEntity.status(409).body(response);
    }
}
