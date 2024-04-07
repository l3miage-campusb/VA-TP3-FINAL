package fr.uga.l3miage.spring.tp3.controller;


import fr.uga.l3miage.spring.tp3.components.CandidateComponent;
import fr.uga.l3miage.spring.tp3.components.SessionComponent;
import fr.uga.l3miage.spring.tp3.exceptions.technical.CandidateNotFoundException;
import fr.uga.l3miage.spring.tp3.models.EcosSessionEntity;
import fr.uga.l3miage.spring.tp3.repositories.*;
import fr.uga.l3miage.spring.tp3.request.SessionCreationRequest;
import fr.uga.l3miage.spring.tp3.request.SessionProgrammationCreationRequest;
import fr.uga.l3miage.spring.tp3.request.SessionProgrammationStepCreationRequest;
import fr.uga.l3miage.spring.tp3.responses.SessionResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.*;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@AutoConfigureTestDatabase
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
public class SessionControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private EcosSessionRepository ecosSessionRepository;
    @Autowired
    private EcosSessionProgrammationRepository ecosSessionProgrammationRepository;
    @Autowired
    private EcosSessionProgrammationStepRepository ecosSessionProgrammationStepRepository;

    @SpyBean
    private SessionComponent sessionComponent;

    @AfterEach
    public void clear() {
        ecosSessionRepository.deleteAll();
    }

    @Test
    void createSession() {
        // Given
        final HttpHeaders headers = new HttpHeaders();

        final SessionProgrammationStepCreationRequest sessionProgrammationStepCreationRequest = SessionProgrammationStepCreationRequest.builder()
                .description("test description")
                .build();

        final SessionProgrammationCreationRequest sessionProgrammationCreationRequest = SessionProgrammationCreationRequest.builder()
                .steps(Set.of(sessionProgrammationStepCreationRequest))
                .build();

        final SessionCreationRequest request = SessionCreationRequest
                .builder()
                .ecosSessionProgrammation(sessionProgrammationCreationRequest)
                .build();
        // when
        ResponseEntity<SessionResponse> response = testRestTemplate.exchange("/api/sessions/create", HttpMethod.POST, new HttpEntity<>(request, headers), SessionResponse.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(ecosSessionRepository.count()).isEqualTo(1);
        assertThat(ecosSessionProgrammationRepository.count()).isEqualTo(1);
        assertThat(ecosSessionProgrammationStepRepository.count()).isEqualTo(1);
        verify(sessionComponent, times(1)).createSession(any(EcosSessionEntity.class));

    }
}
