package fr.uga.l3miage.spring.tp3.components;


import fr.uga.l3miage.spring.tp3.exceptions.technical.CandidateNotFoundException;
import fr.uga.l3miage.spring.tp3.models.EcosSessionEntity;
import fr.uga.l3miage.spring.tp3.models.EcosSessionProgrammationEntity;
import fr.uga.l3miage.spring.tp3.models.EcosSessionProgrammationStepEntity;
import fr.uga.l3miage.spring.tp3.repositories.CandidateRepository;
import fr.uga.l3miage.spring.tp3.repositories.EcosSessionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class SessionComponentTest {
    @Autowired
    private SessionComponent sessionComponent;
    @MockBean
    private EcosSessionRepository ecosSessionRepository;

    @Test
    void createSession() {
        //Given
        EcosSessionProgrammationStepEntity ecosSessionProgrammationStepEntity = EcosSessionProgrammationStepEntity.builder()
                .description("test description")
                .build();
        EcosSessionProgrammationEntity ecosSessionProgrammationEntity = EcosSessionProgrammationEntity.builder()
                .ecosSessionProgrammationStepEntities(Set.of(ecosSessionProgrammationStepEntity))
                .build();
        EcosSessionEntity ecosSessionEntity = EcosSessionEntity.builder()
                .ecosSessionProgrammationEntity(ecosSessionProgrammationEntity)
                .build();
        //When
        when(ecosSessionRepository.save(any())).thenReturn(ecosSessionEntity);

        //Then
        assertThat(sessionComponent.createSession(ecosSessionEntity)).isEqualTo(ecosSessionEntity);

    }
}
