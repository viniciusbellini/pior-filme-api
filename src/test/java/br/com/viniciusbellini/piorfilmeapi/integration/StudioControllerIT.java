package br.com.viniciusbellini.piorfilmeapi.integration;

import br.com.viniciusbellini.piorfilmeapi.PiorFilmeApiApplication;
import br.com.viniciusbellini.piorfilmeapi.error.ApplicationExceptions;
import br.com.viniciusbellini.piorfilmeapi.models.Studio;
import br.com.viniciusbellini.piorfilmeapi.services.StudioService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PiorFilmeApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class StudioControllerIT {

    @Autowired
    private StudioService studioService;

    @Transactional
    @Test
    public void createStudioHappyDay() {
        assertNoRegisteredStudios();
        studioService.save(new Studio("Universal Studios"));
        Assertions.assertThat(studioService.findByName("Universal Studios")).isNotNull();
    }

    @Transactional
    @Test(expected = ConstraintViolationException.class)
    public void dontRegisterStudioWithEmptyName() {
        studioService.save(new Studio(""));
    }

    @Transactional
    @Test(expected = ConstraintViolationException.class)
    public void dontRegisterStudioWithNullName() {
        studioService.save(new Studio(null));
    }

    @Transactional
    @Test
    public void givenStudioAlreadyRegisteredDontRegisterAgain() {
        assertNoRegisteredStudios();

        studioService.save("Universal Studios");
        studioService.save("Universal Studios");

        List<Studio> studios = studioService.findAll();
        Assertions.assertThat(studios.size()).isEqualTo(1);
    }

    private void assertNoRegisteredStudios() {
        try {
            studioService.findAll();
        } catch (ApplicationExceptions e) {
           Assertions.assertThat(e.getMessage()).isEqualTo("There are no registered studios!");
        }
    }
}
