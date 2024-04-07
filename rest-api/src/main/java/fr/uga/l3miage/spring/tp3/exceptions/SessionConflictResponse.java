package fr.uga.l3miage.spring.tp3.exceptions;

import fr.uga.l3miage.spring.tp3.responses.enums.SessionStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionConflictResponse {
    private String uri;
    private String errorMessage;
    private SessionStatus sessionStatus;
}
