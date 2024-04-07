package fr.uga.l3miage.spring.tp3.exceptions.technical;

import fr.uga.l3miage.spring.tp3.responses.enums.SessionStatus;
import lombok.Getter;

@Getter
public class SessionConflictException extends Exception{
    private final SessionStatus sessionStatus;

    public SessionConflictException(String message, SessionStatus sessionStatus) {
        super(message);
        this.sessionStatus = sessionStatus;
    }
}
