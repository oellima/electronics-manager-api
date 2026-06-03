package com.electronics.utils;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Centralized helper for building and executing HTTP requests via RestAssured.
 */
public class RequestHelper {

    private static final Logger log = LoggerFactory.getLogger(RequestHelper.class);

    private RequestHelper() {}

    private static RequestSpecification baseSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(Constants.BASE_URL)
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .build();
    }

    private static RequestSpecification withToken(String token) {
        return RestAssured.given()
                .spec(baseSpec())
                .header("Authorization", "Bearer " + token);
    }

    // ── GET ────────────────────────────────────────────────────────────────

    public static Response get(String path) {
        log.info("GET {}{}", Constants.BASE_URL, path);
        return RestAssured.given()
                .spec(baseSpec())
                .when()
                .get(path)
                .then()
                .extract()
                .response();
    }

    public static Response getWithToken(String path, String token) {
        log.info("GET (authenticated) {}{}", Constants.BASE_URL, path);
        return withToken(token)
                .when()
                .get(path)
                .then()
                .extract()
                .response();
    }

    public static Response getById(String path, int id) {
        log.info("GET {}{}/{}", Constants.BASE_URL, path, id);
        return RestAssured.given()
                .spec(baseSpec())
                .pathParam("id", id)
                .when()
                .get(path)
                .then()
                .extract()
                .response();
    }

    // ── POST ───────────────────────────────────────────────────────────────

    public static Response post(String path, Map<String, Object> body) {
        log.info("POST {}{}", Constants.BASE_URL, path);
        return RestAssured.given()
                .spec(baseSpec())
                .body(body)
                .when()
                .post(path)
                .then()
                .extract()
                .response();
    }

    public static Response postWithToken(String path, Map<String, Object> body, String token) {
        log.info("POST (authenticated) {}{}", Constants.BASE_URL, path);
        return withToken(token)
                .body(body)
                .when()
                .post(path)
                .then()
                .extract()
                .response();
    }
}
