package fr.uga.l3miage.spring.tp3.services;


import fr.uga.l3miage.spring.tp3.components.CandidateComponent;
import fr.uga.l3miage.spring.tp3.exceptions.technical.CandidateNotFoundException;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.CandidateEvaluationGridEntity;
import fr.uga.l3miage.spring.tp3.models.ExamEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CandidateServiceTest {
    @Autowired
    private CandidateService candidateService;
    @MockBean
    private CandidateComponent candidateComponent;

    @Test
    void getCandidateAverage() throws CandidateNotFoundException {


        ExamEntity examEntity = ExamEntity.builder()
                .weight(1)
                .build();
        CandidateEvaluationGridEntity candidateEvaluationGridEntity1 = CandidateEvaluationGridEntity.builder()
                .grade(10)
                .examEntity(examEntity)
                .build();
        CandidateEvaluationGridEntity candidateEvaluationGridEntity2 = CandidateEvaluationGridEntity.builder()
                .grade(15)
                .examEntity(examEntity)
                .build();

        CandidateEntity candidateEntity = CandidateEntity
        .builder()
                .candidateEvaluationGridEntities(Set.of(candidateEvaluationGridEntity1, candidateEvaluationGridEntity2))
                .build();

        when(candidateComponent.getCandidatById(anyLong())).thenReturn(candidateEntity);

        //when
        Double response = candidateService.getCandidateAverage(anyLong());

        //then
        assertThat(response).isEqualTo(12.5);
    }
}
