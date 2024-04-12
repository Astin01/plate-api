package com.project.plateapi.commentTest;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentE2ETest {

    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp()
    {
        RestAssured.port = port;
    }

    @Test
    public void getCommentTest(){
        RestAssured
                .given()
                    .pathParam("discussion_id","1")
                .when()
                    .get("/api/comment/{discussion_id}")
                .then()
                    .log().all()
                .statusCode(200);

   }
}
