package fr.uga.l3miage.spring.tp3.exceptions;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class TestCenterBadRequestResponse {
    private String uri;
    private String errorMessage;

}