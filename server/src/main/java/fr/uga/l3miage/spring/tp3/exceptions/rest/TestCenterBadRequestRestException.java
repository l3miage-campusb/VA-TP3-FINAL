package fr.uga.l3miage.spring.tp3.exceptions.rest;

import lombok.Getter;

@Getter
public class TestCenterBadRequestRestException extends RuntimeException {

    public TestCenterBadRequestRestException(String message) {
        super(message);
    }
}