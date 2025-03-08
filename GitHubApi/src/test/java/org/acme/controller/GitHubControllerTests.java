package org.acme.controller;

import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
public class GitHubControllerTests {

    @Test
    public void testGetRepositories_HappyPath() {
        RestAssured.given()
                .when().get("/github/octocat/repos")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("$.size()", greaterThan(0))
                .body("[0].name", not(emptyOrNullString()))
                .body("[0].ownerLogin", equalTo("octocat"))
                .body("[0].branches", not(empty()));
    }

}
