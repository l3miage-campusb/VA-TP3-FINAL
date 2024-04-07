package fr.uga.l3miage.spring.tp3.exceptions.technical;

import lombok.Getter;

@Getter
public class TestCenterBadRequestException extends Exception{
    public TestCenterBadRequestException(String message) {
        super(message);
    }
}