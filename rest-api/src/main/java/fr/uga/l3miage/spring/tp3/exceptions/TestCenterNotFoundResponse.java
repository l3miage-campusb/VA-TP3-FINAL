package fr.uga.l3miage.spring.tp3.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestCenterNotFoundResponse {
    private Long testCenterId;
    private String errorMessage;
    private String uri;
}
