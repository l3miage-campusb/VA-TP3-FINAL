package fr.uga.l3miage.spring.tp3.controller;

import fr.uga.l3miage.spring.tp3.components.CandidateComponent;
import fr.uga.l3miage.spring.tp3.components.TestCenterComponent;
import fr.uga.l3miage.spring.tp3.exceptions.CandidatNotFoundResponse;
import fr.uga.l3miage.spring.tp3.exceptions.technical.CandidateNotFoundException;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.CandidateEvaluationGridEntity;
import fr.uga.l3miage.spring.tp3.models.ExamEntity;
import fr.uga.l3miage.spring.tp3.models.TestCenterEntity;
import fr.uga.l3miage.spring.tp3.repositories.CandidateEvaluationGridRepository;
import fr.uga.l3miage.spring.tp3.repositories.CandidateRepository;
import fr.uga.l3miage.spring.tp3.repositories.ExamRepository;
import fr.uga.l3miage.spring.tp3.repositories.TestCenterRepository;
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
public class TestCenterControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private TestCenterRepository testCenterRepository;
    @Autowired
    private CandidateRepository candidateRepository;

    @SpyBean
    private TestCenterComponent testCenterComponent;

    @AfterEach
    public void clear() {
        testCenterRepository.deleteAll();
    }

    @Test
    void addCandidates() {
        //given
        final HttpHeaders headers = new HttpHeaders();

        final Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("testCenterId", 1L);

        Set<Long> request = Set.of(2L, 3L);

        TestCenterEntity testCenterEntity = TestCenterEntity.builder()
                .id(1L)
                .build();
        testCenterRepository.save(testCenterEntity);

        CandidateEntity candidateEntity = CandidateEntity.builder()
                .id(2L)
                .email("bastien@gmail.com")
                .build();
        CandidateEntity candidateEntity2 = CandidateEntity.builder()
                .id(3L)
                .email("rochdy@gmail.com")
                .build();
        candidateRepository.save(candidateEntity);
        candidateRepository.save(candidateEntity2);

        // when
        ResponseEntity<Boolean> response = testRestTemplate.exchange("/api/testCenters/{testCenterId}/addCandidates", HttpMethod.PATCH, new HttpEntity<>(request, headers), Boolean.class, urlParams);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(true);
    }
}
