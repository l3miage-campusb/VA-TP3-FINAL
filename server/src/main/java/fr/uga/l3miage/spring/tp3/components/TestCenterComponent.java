package fr.uga.l3miage.spring.tp3.components;

import fr.uga.l3miage.spring.tp3.exceptions.technical.CandidateNotFoundException;
import fr.uga.l3miage.spring.tp3.exceptions.technical.TestCenterNotFoundException;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.CandidateEvaluationGridEntity;
import fr.uga.l3miage.spring.tp3.models.TestCenterEntity;
import fr.uga.l3miage.spring.tp3.repositories.CandidateEvaluationGridRepository;
import fr.uga.l3miage.spring.tp3.repositories.CandidateRepository;
import fr.uga.l3miage.spring.tp3.repositories.TestCenterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TestCenterComponent {
    private final TestCenterRepository testCenterRepository;

    public TestCenterEntity getTestCenterById(Long id) throws TestCenterNotFoundException {
        return testCenterRepository.findById(id).orElseThrow(()-> new TestCenterNotFoundException(String.format("Le TestCenter [%s] n'a pas été trouvé",id),id));
    }

    public TestCenterEntity updateTestCenterCandidates(Long id, TestCenterEntity testCenterEntity) throws TestCenterNotFoundException {
        TestCenterEntity testCenterEntity1 = testCenterRepository.findById(id).orElseThrow(()-> new TestCenterNotFoundException(String.format("Le TestCenter [%s] n'a pas été trouvé",id),id));
        testCenterEntity1.setCandidateEntities(testCenterEntity.getCandidateEntities());
        return testCenterRepository.save(testCenterEntity1);
    }
}
