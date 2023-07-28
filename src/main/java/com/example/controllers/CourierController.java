package com.example.controllers;

import com.example.pojo.CreateCourier;
import com.example.pojo.LoginCourier;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierController extends BaseController {
    private final static String API_COURIER = "/api/v1/courier";
    @Step("Создание курьера")
    public static Response executeCreate(CreateCourier createCourier) {
        Response response =
                given()
                        .spec(getSpec())
                        .body(createCourier)
                        .when()
                        .post(API_COURIER);
        System.out.println("Создали курьера" + response.getBody().asPrettyString());
        return response;
    }
    @Step("Удаление курьера по айди")
    private static Response executeDelete(int courierId) {
        Response response =
                given()
                        .spec(getSpec())
                        .when()
                        .delete(String.format(API_COURIER + "%s", courierId));
        return response;
    }
    @Step("Удаление курьера по логину")
    public static Response executeDelete(LoginCourier loginCourier) {
        return executeDelete(getCourierId(loginCourier));
    }
    @Step("ПОлучить айди курьера")
    public static int getCourierId(LoginCourier loginCourier) {
        Response response = executeLogin(loginCourier);
        int id = response.jsonPath().get("id");
        return id;
    }
    @Step("Залогиниться")
    public static Response executeLogin(LoginCourier loginCourier) {
        Response response =

                given()
                        .spec(getSpec())
                        .body(loginCourier)
                        .when()
                        .post(API_COURIER + "/login");
        return response;
    }
}

