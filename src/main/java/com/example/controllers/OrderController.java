package com.example.controllers;

import com.example.pojo.CreateOrder;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class OrderController extends BaseController{
    private final static String API_ORDERS = "/api/v1/orders";
    @Step("Создание заказа")
    public static Response executeOrder  (CreateOrder createOrder) {
        Response response =
                given()
                        .spec(getSpec())
                        .body(createOrder)
                        .when()
                        .post(API_ORDERS);
        return response;
    }
    @Step("Поиск заказа по айди")
    public static Response executeGetOrderByTrackId(int trackId) {
        Response response =  given()
                .spec(getSpec())
                .queryParam("t",trackId)
                .when()
                .get(API_ORDERS + "/track");
        return response;
    }
    @Step("Парсим айдишник из заказа")
    public static int parseOrderIdFromResponse(Response response) {
        int trackId = response.jsonPath().get("track");
        Response responseOrder = executeGetOrderByTrackId(trackId);
        return responseOrder.jsonPath().get("order.id");
    }
    @Step("Принять заказ")
    public static Response executeAcceptOrder(int courierId, int orderId) {
        Response response =
                given()
                        .spec(getSpec())
                        .queryParam("courierId", courierId)
                        .when()
                        .put(String.format(API_ORDERS + "/accept/%s",orderId));
        return response;
    }
    @Step("Получить список заказов по айди курьера")
    public static Response executeListOrder(int courierId) {
        Response response =
                given()
                        .spec(getSpec())
                        .queryParam("courierId", courierId)
                        .when()
                        .get(API_ORDERS);

        return response;
    }
    @Step("Получить список заказов хашмап")
    public static Response executeListOrder(Map<String,String> queryParams) {
        Response response =
                given()
                        .spec(getSpec())
                        .queryParams(queryParams)
                        .when()
                        .get(API_ORDERS);
        return response;
    }
    @Step("Удалить заказ по айди")
    public static Response executeDeleteOrder(int orderId) {
        Response response =
                given()
                        .spec(getSpec())
                        .when()
                        .put(String.format(API_ORDERS + "/finish/%s",orderId));
        return response;
    }
    @Step("Удалить непосредствнно заказ")
    public static Response executeDeleteOrder(CreateOrder createOrder) {
        return executeDeleteOrder(createOrder);
    }
}

