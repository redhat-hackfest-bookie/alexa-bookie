package com.redhat.hackfest;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class BackendResurceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/api/v1/backend")
          .then()
             .statusCode(200)
             .body(is("hello"));
    }

}