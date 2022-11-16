package br.com.viniciusbellini.piorfilmeapi.integration;

import br.com.viniciusbellini.piorfilmeapi.error.ApplicationExceptions;
import br.com.viniciusbellini.piorfilmeapi.error.TitleAlreadyExistsException;
import br.com.viniciusbellini.piorfilmeapi.models.Title;
import br.com.viniciusbellini.piorfilmeapi.services.TitleService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.springframework.test.annotation.DirtiesContext.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class TitleControllerIT {

    @Autowired
    private TitleService titleService;

    @Test
    public void createTitleHappyDay() {

        assertNoRegisteredTitles();
        Title title = new Title();
        title.setYear("2018");
        title.setName("Can't Stop the Music");
        title.setWinner("yes");
        titleService.save(title);
        Assertions.assertThat(titleService.findByName("Can't Stop the Music")).isNotNull();
    }

    @Test(expected = ConstraintViolationException.class)
    public void dontRegisterTitleWithEmptyName() {
        Title title = new Title();
        title.setYear("2018");
        title.setName("");
        title.setWinner("yes");
        titleService.save(title);
    }

    @Test(expected = ConstraintViolationException.class)
    public void dontRegisterTitleWithNullName() {
        Title title = new Title();
        title.setYear("2018");
        title.setWinner("yes");
        titleService.save(title);
    }

    @Test(expected = TitleAlreadyExistsException.class)
    public void givenTitleAlreadyRegisteredDontRegisterAgain() {
        Title title = new Title();
        title.setYear("2018");
        title.setName("Can't Stop the Music");
        title.setWinner("yes");
        titleService.save(title);

        Title title2 = new Title();
        title2.setYear("2018");
        title2.setName("Can't Stop the Music");
        title2.setWinner("yes");
        titleService.save(title2);

    }

    private void assertNoRegisteredTitles() {
        try {
            titleService.findAll();
        } catch (ApplicationExceptions e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("There are no registered titles!");
        }
    }
}
