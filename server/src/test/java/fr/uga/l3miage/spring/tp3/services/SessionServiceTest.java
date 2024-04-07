package fr.uga.l3miage.spring.tp3.services;

import fr.uga.l3miage.spring.tp3.components.ExamComponent;
import fr.uga.l3miage.spring.tp3.components.SessionComponent;
import fr.uga.l3miage.spring.tp3.mappers.SessionMapper;
import fr.uga.l3miage.spring.tp3.models.EcosSessionEntity;
import fr.uga.l3miage.spring.tp3.request.SessionCreationRequest;
import fr.uga.l3miage.spring.tp3.request.SessionProgrammationCreationRequest;
import fr.uga.l3miage.spring.tp3.request.SessionProgrammationStepCreationRequest;
import fr.uga.l3miage.spring.tp3.responses.SessionResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class SessionServiceTest {
    @Autowired
    private SessionService sessionService;
    @MockBean
    private SessionComponent sessionComponent;
    @MockBean
    private ExamComponent examComponent;
    @SpyBean
    private SessionMapper sessionMapper;

    @Test
    void createSession(){
        //given
        SessionProgrammationStepCreationRequest sessionProgrammationStepCreationRequest = SessionProgrammationStepCreationRequest
                .builder()
                .id(1L)
                .build();

        SessionProgrammationCreationRequest sessionProgrammationCreationRequest = SessionProgrammationCreationRequest
                .builder()
                .id(1L)
                .steps(Set.of(sessionProgrammationStepCreationRequest))
                .build();

        SessionCreationRequest sessionCreationRequest = SessionCreationRequest
                .builder()
                .name("test")
                .startDate(LocalDateTime.of(LocalDate.of(2024,5,5), LocalTime.of(15,40)))
                .endDate(LocalDateTime.of(LocalDate.of(2024,5,15), LocalTime.of(15,40)))
                .examsId(Set.of())
                .ecosSessionProgrammation(sessionProgrammationCreationRequest)
                .build();

        EcosSessionEntity ecosSessionEntity = sessionMapper.toEntity(sessionCreationRequest);

        when(sessionComponent.createSession(any())).thenReturn(ecosSessionEntity);
        //when(examComponent.getAllById(same(Set.of()))).thenReturn(Set.of());
        //when(examComponent.getAllById(same(Set.of(anyLong())))).thenReturn(Set.of());
        SessionResponse responseExpected = sessionMapper.toResponse(ecosSessionEntity);

        //when
        SessionResponse response = sessionService.createSession(sessionCreationRequest);

        assertThat(response).usingRecursiveComparison().isEqualTo(responseExpected);
        verify(sessionMapper,times(2)).toEntity(sessionCreationRequest);
        verify(sessionMapper, times(2)).toResponse(same(ecosSessionEntity));
        verify(sessionComponent,times(1)).createSession(any(EcosSessionEntity.class));


    }
}
