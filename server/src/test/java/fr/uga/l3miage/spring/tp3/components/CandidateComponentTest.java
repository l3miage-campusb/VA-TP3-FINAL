package fr.uga.l3miage.spring.tp3.components;

import fr.uga.l3miage.spring.tp3.repositories.CandidateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CandidateComponentTest {
    @Autowired
    private CandidateComponent candidateComponent;
    @MockBean
    private CandidateRepository candidateRepository;

    @Test
    void getAllEliminatedCandidate() {
        //Given
        when(songRepository.findById(anyString())).thenReturn(Optional.empty());
    }

    void getCandidatById() {

    }

}
