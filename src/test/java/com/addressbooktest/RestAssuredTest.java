package com.addressbooktest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RestAssuredTest
{
    @Before
    public void SetUp()
    {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3000;
    }

    @Test
    public void onCalling_ReturnContactList()
    {
        Response response = RestAssured.get("/contacts");
        System.out.println("AT FIRST: " + response.asString());
        response.then().body("id", Matchers.hasItems(1));
        response.then().body("contactName", Matchers.hasItems("Rohit"));
    }
}
