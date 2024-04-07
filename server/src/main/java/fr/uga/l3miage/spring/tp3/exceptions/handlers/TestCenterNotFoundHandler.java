package fr.uga.l3miage.spring.tp3.exceptions.handlers;


import fr.uga.l3miage.spring.tp3.exceptions.TestCenterNotFoundResponse;
import fr.uga.l3miage.spring.tp3.exceptions.rest.TestCenterNotFoundRestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class TestCenterNotFoundHandler {

    @ExceptionHandler(TestCenterNotFoundRestException.class)
    public ResponseEntity<TestCenterNotFoundResponse> handle(HttpServletRequest httpServletRequest, Exception exception){
        TestCenterNotFoundRestException testCenterNotFoundRestException = (TestCenterNotFoundRestException) exception;
        TestCenterNotFoundResponse response = TestCenterNotFoundResponse
                .builder()
                .testCenterId(testCenterNotFoundRestException.getTestCenterId())
                .errorMessage(testCenterNotFoundRestException.getMessage())
                .uri(httpServletRequest.getRequestURI())
                .build();
        return ResponseEntity.status(404).body(response);
    }
}