package fr.uga.l3miage.spring.tp3.exceptions.handlers;


import fr.uga.l3miage.spring.tp3.exceptions.TestCenterBadRequestResponse;
import fr.uga.l3miage.spring.tp3.exceptions.rest.TestCenterBadRequestRestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class TestCenterBadRequestHandler {

    @ExceptionHandler(TestCenterBadRequestRestException.class)
    public ResponseEntity<TestCenterBadRequestResponse> handle(HttpServletRequest httpServletRequest, Exception exception){
        TestCenterBadRequestRestException testCenterBadRequestRestException = (TestCenterBadRequestRestException) exception;
        TestCenterBadRequestResponse response = TestCenterBadRequestResponse
                .builder()
                .errorMessage(testCenterBadRequestRestException.getMessage())
                .uri(httpServletRequest.getRequestURI())
                .build();
        return ResponseEntity.status(400).body(response);
    }
}