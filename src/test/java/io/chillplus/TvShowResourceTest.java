package io.chillplus;

import io.chillplus.domain.TvShow;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

@QuarkusTest
class TvShowResourceTest {

    @BeforeEach
    void beforeEach() {
        given()
                .when()
                .delete("/api/tv");
    }

    @Test
    void getAllTvShows() {
        given()
                .when()
                .get("/api/tv").then()
                .body("$.size()", is(0));

        TvShow aaShow = new TvShow();
        aaShow.title = "AA";

        given()
                .body(aaShow)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .when()
                .post("/api/tv")
                .then()
                .statusCode(201)
                .contentType(MediaType.APPLICATION_JSON)
                .body("title", is(aaShow.title));

        TvShow bbShow = new TvShow();
        bbShow.title = "BB";

        given()
                .body(bbShow)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .when()
                .post("/api/tv")
                .then()
                .statusCode(201)
                .contentType(MediaType.APPLICATION_JSON)
                .body("title", is(bbShow.title));

        given()
                .when()
                .get("/api/tv")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("$.size()", is(2));
    }

    @Test
    void checkTvShowTitleIsNotBlank() {
        TvShow tvShow = new TvShow();
        given()
                .contentType(ContentType.JSON)
                .body(tvShow)
                .when()
                .post("/api/tv")
                .then()
                .statusCode(400);
    }

    @Test
    void createTvShow() {
        TvShow tvShow = new TvShow();
        tvShow.title = "my_tvShow";
        given()
                .contentType(ContentType.JSON)
                .body(tvShow)
                .when()
                .post("/api/tv")
                .then()
                .statusCode(201);

        tvShow.id = 1L;
        given()
                .contentType(ContentType.JSON)
                .body(tvShow)
                .when()
                .post("/api/tv")
                .then()
                .statusCode(400);
    }

    @Test
    void getOneTvShow() {
        TvShow tvShow = new TvShow();
        tvShow.title = "AA";

        given()
                .body(tvShow)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .when()
                .post("/api/tv")
                .then()
                .statusCode(201)
                .contentType(MediaType.APPLICATION_JSON)
                .body("title", is(tvShow.title));

        given()
                .when()
                .get("/api/tv/{id}", 0)
                .then()
                .statusCode(200);
    }

    @Test
    void getNonExistingTvShow() {
        given()
                .when()
                .get("/api/tv/{id}", -1L)
                .then()
                .statusCode(404);
    }

    @Test
    void deleteAllTvShows() {
        TvShow tvShow = new TvShow();
        tvShow.title = "title";

        given()
                .contentType(ContentType.JSON)
                .body(tvShow)
                .when()
                .post("/api/tv")
                .then()
                .statusCode(201);

        given()
                .when()
                .get("/api/tv")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("$.size()", is(1));

        given()
                .when()
                .delete("/api/tv")
                .then()
                .statusCode(200);

        given()
                .when()
                .get("/api/tv")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("$.size()", is(0));
    }

    @Test
    void deleteOneTvShow() {
        TvShow tvShow = new TvShow();
        tvShow.title = "title";

        given()
                .contentType(ContentType.JSON)
                .body(tvShow)
                .when()
                .post("/api/tv")
                .then()
                .statusCode(201);

        given()
                .when()
                .get("/api/tv")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("$.size()", is(1));

        given()
                .when()
                .delete("/api/tv/{id}", 0L)
                .then()
                .statusCode(200);

        given()
                .when()
                .get("/api/tv")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("$.size()", is(0));
    }
}
