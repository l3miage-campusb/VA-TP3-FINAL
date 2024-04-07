package fr.uga.l3miage.spring.tp3.repositories;

import fr.uga.l3miage.spring.tp3.enums.TestCenterCode;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.CandidateEvaluationGridEntity;
import fr.uga.l3miage.spring.tp3.models.TestCenterEntity;
import fr.uga.l3miage.spring.tp3.repositories.CandidateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

import static fr.uga.l3miage.spring.tp3.enums.TestCenterCode.*;
import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
class CandidateRepositoryTest {
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private TestCenterRepository testCenterRepository;
    @Autowired
    private CandidateEvaluationGridRepository candidateEvaluationGridRepository;


    @Test
    void testFindAllByTestCenterEntityCode(){
        //given


        CandidateEntity candidateEntity = CandidateEntity
                .builder()
                .firstname("bastien")
                .lastname("campus")
                .email("bastien@gmail.com")
                .phoneNumber("19161817")
                .birthDate(LocalDate.of(2003,11,1))
                .hasExtraTime(false)
                .build();

        CandidateEntity candidateEntity2 = CandidateEntity
                .builder()
                .firstname("rochdy")
                .lastname("ait_el_kebir")
                .email("rochdy@gmail.com")
                .phoneNumber("12131415")
                .birthDate(LocalDate.of(2003, 2, 3))
                .hasExtraTime(true)
                .build();



        candidateRepository.save(candidateEntity);
        candidateRepository.save(candidateEntity2);

        TestCenterEntity testCenterGre = TestCenterEntity
                .builder()
                .code(GRE)
                .university("UGA")
                .city("Saint-Martin-D'heres")
                .candidateEntities(Set.of(candidateEntity))
                .build();
        TestCenterEntity testCenterDij = TestCenterEntity
                .builder()
                .code(DIJ)
                .university("Université de Dijon")
                .city("Dijon")
                .candidateEntities(Set.of(candidateEntity2))
                .build();
        testCenterRepository.save(testCenterDij);
        testCenterRepository.save(testCenterGre);

        candidateEntity.setTestCenterEntity(testCenterGre);
        candidateEntity2.setTestCenterEntity(testCenterDij);

        candidateRepository.save(candidateEntity);
        candidateRepository.save(candidateEntity2);


        // when
        Set<CandidateEntity> candidateEntitiesResponses1 = candidateRepository.findAllByTestCenterEntityCode(GRE);
        Set<CandidateEntity> candidateEntitiesResponses2 = candidateRepository.findAllByTestCenterEntityCode(PAR);
        //then
        assertThat(candidateEntitiesResponses1).hasSize(1);
        assertThat(candidateEntitiesResponses1.stream().findAny().get().getTestCenterEntity().getCode()).isEqualTo(GRE);

        assertThat(candidateEntitiesResponses2).hasSize(0);

    }


    @Test
    void testFindAllByCandidateEvaluationGridEntitiesGradeLessThan(){
        //given
        CandidateEvaluationGridEntity candidateEvaluationGrid1 = CandidateEvaluationGridEntity
                .builder()
                .sheetNumber((long)1)
                .grade(12.5)
                .submissionDate(LocalDateTime.of(LocalDate.of(2024,5,5), LocalTime.of(15,40)))
                .build();
        CandidateEvaluationGridEntity candidateEvaluationGrid2 = CandidateEvaluationGridEntity
                .builder()
                .sheetNumber((long)2)
                .grade(7)
                .submissionDate(LocalDateTime.of(LocalDate.of(2024,5,5), LocalTime.of(15,40)))

                .build();


        candidateEvaluationGridRepository.save(candidateEvaluationGrid1);
        candidateEvaluationGridRepository.save(candidateEvaluationGrid2);

        CandidateEntity candidateEntity = CandidateEntity
                .builder()
                .firstname("bastien")
                .lastname("campus")
                .email("bastien@gmail.com")
                .phoneNumber("12345678")
                .birthDate(LocalDate.of(2003,11,1))
                .hasExtraTime(false)
                .candidateEvaluationGridEntities(Set.of(candidateEvaluationGrid1))
                .build();

        CandidateEntity candidateEntity2 = CandidateEntity
                .builder()
                .firstname("rochdy")
                .lastname("ait_el_kebir")
                .email("rochdy@gmail.com")
                .phoneNumber("23456789")
                .birthDate(LocalDate.of(2003, 2, 3))
                .hasExtraTime(true)
                .candidateEvaluationGridEntities(Set.of(candidateEvaluationGrid2))
                .build();


        candidateRepository.save(candidateEntity);
        candidateRepository.save(candidateEntity2);

        candidateEvaluationGrid1.setCandidateEntity(candidateEntity);
        candidateEvaluationGrid2.setCandidateEntity(candidateEntity2);

        candidateEvaluationGridRepository.save(candidateEvaluationGrid1);
        candidateEvaluationGridRepository.save(candidateEvaluationGrid2);


        // when
        Set<CandidateEntity> candidateEntitiesResponses1 = candidateRepository.findAllByCandidateEvaluationGridEntitiesGradeLessThan(15);
        Set<CandidateEntity> candidateEntitiesResponses2 = candidateRepository.findAllByCandidateEvaluationGridEntitiesGradeLessThan(8);
        //then
        assertThat(candidateEntitiesResponses1).hasSize(2);   //faire aussi un test qui regarde si au moins une note est inférieur a 15 avec un find ?


        assertThat(candidateEntitiesResponses2).hasSize(1);


    }

    @Test
    void testFindAllByHasExtraTimeFalseAndBirthDateBefore(){
        //given
        CandidateEntity candidateEntity = CandidateEntity
                .builder()
                .firstname("bastien")
                .lastname("campus")
                .email("bastien@gmail.com")
                .phoneNumber("12345678")
                .birthDate(LocalDate.of(2003,11,1))
                .hasExtraTime(false)
                .build();


        CandidateEntity candidateEntity2 = CandidateEntity
                .builder()
                .firstname("rochdy")
                .lastname("ait_el_kebir")
                .email("rochdy@gmail.com")
                .phoneNumber("23456789")
                .birthDate(LocalDate.of(2003, 2, 3))
                .hasExtraTime(true)
                .build();
        CandidateEntity candidateEntity3 = CandidateEntity
                .builder()
                .firstname("domingo")
                .lastname("montesion")
                .email("guadalajara@cartel.com")
                .phoneNumber("42042012")
                .birthDate(LocalDate.of(2005,6,3))
                .hasExtraTime(false)
                .build();


        candidateRepository.save(candidateEntity);
        candidateRepository.save(candidateEntity2);
        candidateRepository.save(candidateEntity3);

        // when
        Set<CandidateEntity> candidateEntitiesResponses1 = candidateRepository.findAllByHasExtraTimeFalseAndBirthDateBefore(LocalDate.of(2006,1,1));
        Set<CandidateEntity> candidateEntitiesResponses2 = candidateRepository.findAllByHasExtraTimeFalseAndBirthDateBefore(LocalDate.of(2003,11,2));
        //then
        assertThat(candidateEntitiesResponses1).hasSize(2);   //faire aussi un test qui regarde si au moins une note est inférieur a 15 avec un find ?
        assertThat(candidateEntitiesResponses1.stream().findAny().get().getBirthDate()).isBefore(LocalDate.of(2006,1,1));
        assertThat(candidateEntitiesResponses2).hasSize(1);


    }
}





