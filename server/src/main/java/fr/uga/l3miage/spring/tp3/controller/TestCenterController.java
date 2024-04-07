package fr.uga.l3miage.spring.tp3.controller;
import fr.uga.l3miage.spring.tp3.endpoints.TestCenterEndpoints;
import fr.uga.l3miage.spring.tp3.services.TestCenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.Set;

@Controller
@RequiredArgsConstructor
public class TestCenterController implements TestCenterEndpoints {
    private final TestCenterService testCenterService;

    @Override
    public Boolean addCandidates(Long testCenterId, Set<Long> request) {
        return testCenterService.addCandidates(testCenterId,  request);
    }
}
