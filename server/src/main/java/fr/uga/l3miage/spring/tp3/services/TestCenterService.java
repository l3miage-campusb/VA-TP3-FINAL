package fr.uga.l3miage.spring.tp3.services;

import fr.uga.l3miage.spring.tp3.components.CandidateComponent;
import fr.uga.l3miage.spring.tp3.components.TestCenterComponent;
import fr.uga.l3miage.spring.tp3.exceptions.rest.CandidateNotFoundRestException;
import fr.uga.l3miage.spring.tp3.exceptions.rest.TestCenterBadRequestRestException;
import fr.uga.l3miage.spring.tp3.exceptions.rest.TestCenterNotFoundRestException;
import fr.uga.l3miage.spring.tp3.exceptions.technical.CandidateNotFoundException;
import fr.uga.l3miage.spring.tp3.exceptions.technical.TestCenterBadRequestException;
import fr.uga.l3miage.spring.tp3.exceptions.technical.TestCenterNotFoundException;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.TestCenterEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TestCenterService {
    private final TestCenterComponent testCenterComponent;
    private final CandidateComponent candidateComponent;

    public Boolean addCandidates(Long testCenterId, Set<Long> request) {
        try {
            TestCenterEntity testCenterEntity = testCenterComponent.getTestCenterById(testCenterId);
            Set<CandidateEntity> candidateEntities = new HashSet<CandidateEntity>();
            for (Long candidatId : request){
                CandidateEntity candidateEntity = candidateComponent.getCandidatById(candidatId);
                if (LocalDate.now().getYear() - candidateEntity.getBirthDate().getYear() < 18 )
                {
                    throw new TestCenterBadRequestRestException("Le candidat a moins de 18 ans");
                }
                candidateEntities.add(candidateEntity);
            }
            testCenterEntity.setCandidateEntities(candidateEntities);
            testCenterComponent.updateTestCenterCandidates(testCenterId, testCenterEntity);
            return true;

        } catch (CandidateNotFoundException e) {
            throw new CandidateNotFoundRestException(e.getMessage(),e.getCandidateId());
        }
        catch (TestCenterNotFoundException e) {
            throw new TestCenterNotFoundRestException(e.getMessage(),e.getTestCenterId());
        }
    }
}
