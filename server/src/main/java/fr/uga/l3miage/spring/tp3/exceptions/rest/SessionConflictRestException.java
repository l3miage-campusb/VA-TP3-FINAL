package fr.uga.l3miage.spring.tp3.exceptions.rest;

import fr.uga.l3miage.spring.tp3.responses.enums.SessionStatus;
import lombok.Getter;

@Getter
public class SessionConflictRestException extends RuntimeException {
    private final SessionStatus sessionStatus;

    public SessionConflictRestException(String message, SessionStatus sessionStatus) {
        super(message);
        this.sessionStatus = sessionStatus;
    }
}
