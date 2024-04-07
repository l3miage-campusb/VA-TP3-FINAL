package fr.uga.l3miage.spring.tp3.exceptions.rest;

import lombok.Getter;

@Getter
public class TestCenterNotFoundRestException extends RuntimeException {
    private final Long testCenterId;

    public TestCenterNotFoundRestException(String message, Long testCenterId) {
        super(message);
        this.testCenterId = testCenterId;
    }
}