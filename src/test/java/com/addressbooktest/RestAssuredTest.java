package com.addressbooktest;

import com.addressbook.ContactBook;
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

    @Test
    public void givenMultipleContacts_OnPost_ShouldReturnAddedContact()
    {
        ContactBook[] arrayOfContacts = {
                new ContactBook(0, "Bridgelabz", "Karan"),
                new ContactBook(0, "Bridgelabz", "Usman")
        };
        Instant threadStart = Instant.now();
        addContactToJsonServerWithThreads(Arrays.asList(arrayOfContacts));
        Instant threadEnd = Instant.now();
        System.out.println("Duration with thread" + Duration.between(threadStart, threadEnd));
        Response response = RestAssured.get("/contacts");
        response.then().body("contactName", Matchers.hasItems("Usman"));
    }

    private void addContactToJsonServerWithThreads(List<ContactBook> asList)
    {
        HashMap<Integer, Boolean> contactAdditionStatus = new HashMap<>();
        asList.forEach(contactBook -> {
            Runnable task = () -> {
                contactAdditionStatus.put(contactBook.hashCode(), false);
                System.out.println("Contact being added: " + Thread.currentThread().getName());
                String contactDetails = "{\"bookName\": \"" + contactBook.book + "\", \"contactName\": \"" + contactBook.fname + "\"}";
                addContactToJSON(contactDetails);
                contactAdditionStatus.put(contactBook.hashCode(), true);
                System.out.println("Contact added: " + Thread.currentThread().getName());
            };
            Thread thread = new Thread(task, contactBook.fname);
            thread.start();
        });
        while (contactAdditionStatus.containsValue(false))
        {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void addContactToJSON(String contactData)
    {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(contactData)
                .when()
                .post("/contacts");
    }

    @Test
    public void givenContact_OnUpdate_ShouldReturnUpdatedContact()
    {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"bookName\": \"CapG\", \"contactName\": \"Mohit\"}")
                .when()
                .put("/contacts/1");
        response.then().body("contactName", Matchers.is("Mohit"));
    }

}
