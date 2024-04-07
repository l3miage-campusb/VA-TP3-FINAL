package fr.uga.l3miage.spring.tp3.components;

import fr.uga.l3miage.spring.tp3.enums.SessionStatus;
import fr.uga.l3miage.spring.tp3.exceptions.technical.CandidateNotFoundException;
import fr.uga.l3miage.spring.tp3.exceptions.technical.SessionConflictException;
import fr.uga.l3miage.spring.tp3.models.EcosSessionEntity;
import fr.uga.l3miage.spring.tp3.repositories.EcosSessionProgrammationRepository;
import fr.uga.l3miage.spring.tp3.repositories.EcosSessionProgrammationStepRepository;
import fr.uga.l3miage.spring.tp3.repositories.EcosSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionComponent {
    private final EcosSessionRepository ecosSessionRepository;
    private final EcosSessionProgrammationRepository ecosSessionProgrammationRepository;
    private final EcosSessionProgrammationStepRepository ecosSessionProgrammationStepRepository;


    public EcosSessionEntity createSession(EcosSessionEntity entity){
        ecosSessionProgrammationStepRepository.saveAll(entity.getEcosSessionProgrammationEntity().getEcosSessionProgrammationStepEntities());
        ecosSessionProgrammationRepository.save(entity.getEcosSessionProgrammationEntity());
        return ecosSessionRepository.save(entity);
    }

    public EcosSessionEntity endSession(long id) throws SessionConflictException {
        EcosSessionEntity ecosSessionEntity = ecosSessionRepository.findById(id).orElseThrow(()-> new SessionConflictException(String.format("L'état de la session [%s] n'a pas pu être changé",id), fr.uga.l3miage.spring.tp3.responses.enums.SessionStatus.FINISHED));
        ecosSessionEntity.setStatus(SessionStatus.EVAL_ENDED);
        return ecosSessionRepository.save(ecosSessionEntity);
    }

}
