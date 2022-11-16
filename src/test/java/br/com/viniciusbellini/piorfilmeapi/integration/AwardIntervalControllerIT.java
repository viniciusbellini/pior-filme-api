package br.com.viniciusbellini.piorfilmeapi.integration;

import br.com.viniciusbellini.piorfilmeapi.models.AwardInterval;
import br.com.viniciusbellini.piorfilmeapi.models.AwardIntervalDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class AwardIntervalControllerIT {

    @LocalServerPort
    private int port;

    @Test
    public void givenListOfTitlesShouldReturnAProducerWithAMinimumAwardIntervalAndAnotherWithAMaximumInterval() {
        uploadFile("movielistFull");

        AwardIntervalDTO result = given()
                .when()
                .get("http://localhost:" + port + "/awardInterval")
                .then()
                .extract().as(AwardIntervalDTO.class);

        List<AwardInterval> min = result.getMin();
        assertThat(min.size()).isEqualTo(1);
        assertThat(min.get(0).getProducer()).isEqualTo("Joel Silver");
        assertThat(min.get(0).getInterval()).isEqualTo(1);
        assertThat(min.get(0).getPreviousWin()).isEqualTo(1990);
        assertThat(min.get(0).getFollowingWin()).isEqualTo(1991);

        List<AwardInterval> max = result.getMax();
        assertThat(max.size()).isEqualTo(1);
        assertThat(max.get(0).getProducer()).isEqualTo("Matthew Vaughn");
        assertThat(max.get(0).getInterval()).isEqualTo(13);
        assertThat(max.get(0).getPreviousWin()).isEqualTo(2002);
        assertThat(max.get(0).getFollowingWin()).isEqualTo(2015);
    }

    @Test
    public void givenListOfTitlesShouldReturnDrawAtMinimumAwardIntervalAndMaximumInterval() {
        uploadFile("movielistDraw");

        AwardIntervalDTO result = given()
                .when()
                .get("http://localhost:" + port + "/awardInterval")
                .then()
                .extract().as(AwardIntervalDTO.class);

        List<AwardInterval> min = result.getMin();
        assertThat(min.size()).isEqualTo(2);
        assertThat(min.get(0).getProducer()).isEqualTo("Producer 1");
        assertThat(min.get(0).getInterval()).isEqualTo(1);
        assertThat(min.get(0).getPreviousWin()).isEqualTo(2008);
        assertThat(min.get(0).getFollowingWin()).isEqualTo(2009);
        assertThat(min.get(1).getProducer()).isEqualTo("Producer 2");
        assertThat(min.get(1).getInterval()).isEqualTo(1);
        assertThat(min.get(1).getPreviousWin()).isEqualTo(2018);
        assertThat(min.get(1).getFollowingWin()).isEqualTo(2019);

        List<AwardInterval> max = result.getMax();
        assertThat(max.size()).isEqualTo(2);
        assertThat(max.get(0).getProducer()).isEqualTo("Producer 1");
        assertThat(max.get(0).getInterval()).isEqualTo(80);
        assertThat(max.get(0).getPreviousWin()).isEqualTo(1928);
        assertThat(max.get(0).getFollowingWin()).isEqualTo(2008);
        assertThat(max.get(1).getProducer()).isEqualTo("Producer 2");
        assertThat(max.get(1).getInterval()).isEqualTo(80);
        assertThat(max.get(1).getPreviousWin()).isEqualTo(2019);
        assertThat(max.get(1).getFollowingWin()).isEqualTo(2099);
    }

    @Test
    public void givenEmptyListOfTitlesShouldReturnStatusNotFound() {
        uploadFile("movielistEmpty");

        given()
                .when()
                .get("http://localhost:" + port + "/awardInterval")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void uploadFile(String fileName) {
        given()
                .multiPart("file", new File("src/test/resources/" + fileName + ".csv"))
                .when().post("http://localhost:" + port + "/title");
    }
}
