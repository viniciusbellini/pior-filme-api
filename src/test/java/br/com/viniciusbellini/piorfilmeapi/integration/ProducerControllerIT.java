package br.com.viniciusbellini.piorfilmeapi.integration;

import br.com.viniciusbellini.piorfilmeapi.error.ApplicationExceptions;
import br.com.viniciusbellini.piorfilmeapi.models.Producer;
import br.com.viniciusbellini.piorfilmeapi.services.ProducerService;
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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class ProducerControllerIT {

    @Autowired
    private ProducerService producerService;

    @Transactional
    @Test
    public void createProducerHappyDay() {
        assertNoRegisteredProducers();
        producerService.save(new Producer("Steven Spielberg"));
        Assertions.assertThat(producerService.findByName("Steven Spielberg")).isNotNull();
    }

    @Transactional
    @Test(expected = ConstraintViolationException.class)
    public void dontRegisterProducerWithEmptyName() {
        producerService.save(new Producer(""));
    }

    @Transactional
    @Test(expected = ConstraintViolationException.class)
    public void dontRegisterProducerWithNullName() {
        producerService.save(new Producer(null));
    }

    @Transactional
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
