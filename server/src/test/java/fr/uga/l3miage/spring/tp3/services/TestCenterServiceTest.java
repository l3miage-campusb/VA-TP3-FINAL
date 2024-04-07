package fr.uga.l3miage.spring.tp3.services;

import fr.uga.l3miage.spring.tp3.components.CandidateComponent;
import fr.uga.l3miage.spring.tp3.components.TestCenterComponent;
import fr.uga.l3miage.spring.tp3.exceptions.technical.CandidateNotFoundException;
import fr.uga.l3miage.spring.tp3.exceptions.technical.TestCenterNotFoundException;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.TestCenterEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class TestCenterServiceTest {
    @Autowired
    private TestCenterService testCenterService;

    @MockBean
    private TestCenterComponent testCenterComponent;

    @MockBean
    private CandidateComponent candidateComponent;


    @Test
    void addCandidates() throws TestCenterNotFoundException, CandidateNotFoundException {

        //given
        Set<Long> candidatesIds = Set.of(1L, 2L, 3L);

        TestCenterEntity testCenterEntity = TestCenterEntity.builder()
                .candidateEntities(Set.of())
                .build();
        CandidateEntity candidateEntity = CandidateEntity.builder().build();

        when(testCenterComponent.getTestCenterById(anyLong())).thenReturn(testCenterEntity);
        when(candidateComponent.getCandidatById(anyLong())).thenReturn(candidateEntity);

        //when
        boolean response = testCenterService.addCandidates(1L, candidatesIds);

        assertThat(response).isEqualTo(true);

    }
}
