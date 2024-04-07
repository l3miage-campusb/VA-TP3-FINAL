package fr.uga.l3miage.spring.tp3.components;



import fr.uga.l3miage.spring.tp3.enums.TestCenterCode;
import fr.uga.l3miage.spring.tp3.exceptions.technical.TestCenterNotFoundException;
import fr.uga.l3miage.spring.tp3.models.TestCenterEntity;
import fr.uga.l3miage.spring.tp3.repositories.TestCenterRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import fr.uga.l3miage.spring.tp3.enums.TestCenterCode.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class TestCenterComponentTest {
    @Autowired
    private TestCenterComponent testCenterComponent;
    @MockBean
    private TestCenterRepository testCenterRepository;





    @Test
    void getTestCenterNotFound() {
        //Given
        when(testCenterRepository.findById(anyLong())).thenReturn(Optional.empty());

        //then - when
        assertThrows(TestCenterNotFoundException.class,()->testCenterComponent.getTestCenterById(1L));
    }

    @Test
    void getTestCenterFound() {
        //Given
        TestCenterEntity testCenterEntity = TestCenterEntity
                .builder()
                .code(TestCenterCode.GRE)
                .university("uga")
                .city("saint martin d'heres")
                .build();
        when(testCenterRepository.findById(anyLong())).thenReturn(Optional.of(testCenterEntity));
        // when - then
        assertDoesNotThrow(()->testCenterComponent.getTestCenterById(1L));
    }

}