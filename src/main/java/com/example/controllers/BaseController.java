package com.example.controllers;

import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BaseController {
        public static final String BASE_URI = "http://qa-scooter.praktikum-services.ru";
        @Step("Спека")
        protected static RequestSpecification getSpec() {
            return new RequestSpecBuilder()
                    .log(LogDetail.ALL)
                    .setContentType(ContentType.JSON)
                    .setBaseUri(BASE_URI)
                    .build();
    }
}
