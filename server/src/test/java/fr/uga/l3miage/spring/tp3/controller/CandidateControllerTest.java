package fr.uga.l3miage.spring.tp3.controller;

import fr.uga.l3miage.spring.tp3.components.CandidateComponent;
import fr.uga.l3miage.spring.tp3.exceptions.CandidatNotFoundResponse;
import fr.uga.l3miage.spring.tp3.exceptions.technical.CandidateNotFoundException;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.CandidateEvaluationGridEntity;
import fr.uga.l3miage.spring.tp3.models.ExamEntity;
import fr.uga.l3miage.spring.tp3.repositories.CandidateEvaluationGridRepository;
import fr.uga.l3miage.spring.tp3.repositories.CandidateRepository;
import fr.uga.l3miage.spring.tp3.repositories.ExamRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@AutoConfigureTestDatabase
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
public class CandidateControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private CandidateEvaluationGridRepository candidateEvaluationGridRepository;
    @Autowired
    private ExamRepository examRepository;

    @SpyBean
    private CandidateComponent candidateComponent;

    @AfterEach
    public void clear() {
        candidateRepository.deleteAll();
    }

    @Test
    void getCandidateAverage() {
        //given
        final HttpHeaders headers = new HttpHeaders();

        final Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("candidateId", 4L);

        ExamEntity examEntity = ExamEntity.builder()
                .weight(1)
                .build();
        examRepository.save(examEntity);
        CandidateEvaluationGridEntity candidateEvaluationGridEntity1 = CandidateEvaluationGridEntity.builder()
                .grade(10)
                .examEntity(examEntity)
                .build();

        CandidateEvaluationGridEntity candidateEvaluationGridEntity2 = CandidateEvaluationGridEntity.builder()
                .grade(15)
                .examEntity(examEntity)
                .build();
        candidateEvaluationGridRepository.save(candidateEvaluationGridEntity1);
        candidateEvaluationGridRepository.save(candidateEvaluationGridEntity2);
        examEntity.setCandidateEvaluationGridEntities(Set.of(candidateEvaluationGridEntity1, candidateEvaluationGridEntity2));
        examRepository.save(examEntity);
        CandidateEntity candidateEntity = CandidateEntity
                .builder()
                .firstname("bastien")
                .lastname("campus")
                .email("bastien@gmail.com")
                .phoneNumber("19161817")
                .birthDate(LocalDate.of(2003,11,1))
                .hasExtraTime(false)
                .candidateEvaluationGridEntities(Set.of(candidateEvaluationGridEntity1, candidateEvaluationGridEntity2))
                .build();
        candidateRepository.save(candidateEntity);

        candidateEvaluationGridEntity2.setCandidateEntity(candidateEntity);
        candidateEvaluationGridRepository.save(candidateEvaluationGridEntity2);
        // when
        ResponseEntity<Double> response = testRestTemplate.exchange("/api/candidates/{candidateId}/average", HttpMethod.GET, new HttpEntity<>(null, headers), Double.class, urlParams);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(15);
    }


    @Test
    void getCandidateAverageNotFound() throws CandidateNotFoundException {
        //given
        final HttpHeaders headers = new HttpHeaders();

        final Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("candidateId", 1L);

        CandidatNotFoundResponse CandidatNotFoundResponseExpected = CandidatNotFoundResponse.builder()
                .candidateId(1L)
                .errorMessage("Le candidat [1] n'a pas été trouvé")
                .uri("/api/candidates/1/average")
                .build();

        // when
        ResponseEntity<CandidatNotFoundResponse> response = testRestTemplate.exchange("/api/candidates/{candidateId}/average", HttpMethod.GET, new HttpEntity<>(null, headers), CandidatNotFoundResponse.class, urlParams);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).usingRecursiveComparison()
                .isEqualTo(CandidatNotFoundResponseExpected);
    }
}
