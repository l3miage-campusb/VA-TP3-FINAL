package fr.uga.l3miage.spring.tp3.components;

import fr.uga.l3miage.spring.tp3.exceptions.technical.CandidateNotFoundException;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.CandidateEvaluationGridEntity;
import fr.uga.l3miage.spring.tp3.repositories.CandidateEvaluationGridRepository;
import fr.uga.l3miage.spring.tp3.repositories.CandidateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CandidateComponentTest {
    @Autowired
    private CandidateComponent candidateComponent;
    @MockBean
    private CandidateRepository candidateRepository;
    @MockBean
    private CandidateEvaluationGridRepository candidateEvaluationGridRepository;

    @Test
    void getAllEliminatedCandidateFound() {
        //Given
        CandidateEvaluationGridEntity candidateEvaluationGridEntity = CandidateEvaluationGridEntity
                .builder()
                .grade(2)
                .build();

        when(candidateEvaluationGridRepository.findAllByGradeIsLessThanEqual(anyDouble())).thenReturn(Set.of(candidateEvaluationGridEntity));

        // when - then
        assertDoesNotThrow(()->candidateComponent.getAllEliminatedCandidate());
    }


    @Test
    void getCandidatNotFound() {
        //Given
        when(candidateRepository.findById(anyLong())).thenReturn(Optional.empty());

        //then - when
        assertThrows(CandidateNotFoundException.class,()->candidateComponent.getCandidatById(1L));
    }

    @Test
    void getCandidatFound() {
        //Given
        CandidateEntity candidateEntity = CandidateEntity
                .builder()
                .firstname("test")
                .lastname("test")
                .email("test@test.com")
                .phoneNumber("12345678")
                .birthDate(LocalDate.of(1970,1,1))
                .hasExtraTime(false)
                .build();
        when(candidateRepository.findById(anyLong())).thenReturn(Optional.of(candidateEntity));

        // when - then
        assertDoesNotThrow(()->candidateComponent.getCandidatById(1L));
    }

}
