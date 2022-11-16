package br.com.desafiotexoit.integration;

import br.com.desafiotexoit.services.ProducerService;
import br.com.desafiotexoit.error.ApplicationExceptions;
import br.com.desafiotexoit.models.Producer;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.springframework.test.annotation.DirtiesContext.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProducerControllerIT {

    @Autowired
    private ProducerService producerService;

    @Test
    public void createProducerHappyDay() {
        assertNoRegisteredProducers();
        producerService.save(new Producer("Steven Spielberg"));
        Assertions.assertThat(producerService.findByName("Steven Spielberg")).isNotNull();
    }

    @Test(expected = ConstraintViolationException.class)
    public void dontRegisterProducerWithEmptyName() {
        producerService.save(new Producer(""));
    }

    @Test(expected = ConstraintViolationException.class)
    public void dontRegisterProducerWithNullName() {
        producerService.save(new Producer(null));
    }

    @Test
    public void givenProducerAlreadyRegisteredDontRegisterAgain() {
        assertNoRegisteredProducers();

        producerService.save("Steven Spielberg");
        producerService.save("Steven Spielberg");

        List<Producer> producers = producerService.findAll();
        Assertions.assertThat(producers.size()).isEqualTo(1);
    }

    private void assertNoRegisteredProducers() {
        try {
            producerService.findAll();
        } catch (ApplicationExceptions e) {
           Assertions.assertThat(e.getMessage()).isEqualTo("There are no registered producers!");
        }
    }
}
