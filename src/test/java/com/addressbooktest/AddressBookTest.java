package com.addressbooktest;

import com.addressbook.AddressBookDBService;
import com.addressbook.ContactBook;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class AddressBookTest
{
    AddressBookDBService addressBookDBService = new AddressBookDBService();

    @Test
    public void givenAddressBookDB_ShouldMatchDataCount()
    {
        List<ContactBook> contactBookData = addressBookDBService.readData();
        Assert.assertEquals(2, contactBookData.size());
    }

    @Test
    public void givenNewBookName_WhenUpdated_ShouldMatch()
    {
        int result = addressBookDBService.updateData("Arif", "Bridgelabz");
        Assert.assertEquals(1, result);
    }

    @Test
    public void givenDateRange_WhenRetrieved_ShouldMatchCount()
    {
        LocalDate startDate = LocalDate.of(2018, 01, 01);
        LocalDate endDate = LocalDate.now();
        List<ContactBook> contactBookList = addressBookDBService.getCustomerFromDateRange(startDate, endDate);
        Assert.assertEquals(2, contactBookList.size());
    }

    @Test
    public void givenCity_WhenRetrieved_ShouldMatchCount()
    {
        int result = addressBookDBService.getCountByCity("Jaipur");
        Assert.assertEquals(1, result);
    }

    @Test
    public void givenContact_WhenAdded_ShouldAddedToAllTables() throws SQLException
    {
        addressBookDBService.addContactToDatabase("CapG", "Harkirat", "Singh", LocalDate.now(), "Saheen Bagh", "New Delhi", "Delhi", 110021, 845749899, "hsingh@gmail.com");
        List<ContactBook> contactBookData = addressBookDBService.readData();
        Assert.assertEquals(3, contactBookData.size());
    }

    @Test
    public void given2Employees_WhenAddedToDB_ShouldMatchCount() throws SQLException
    {
        ContactBook[] arrayOfContacts = {
                new ContactBook(0, "Bridgelabz", "Chris", "Jordan", LocalDate.now(), "Bhagat Singh Road", "Hisar", "Haryana", 328921, 987530218, "cjordan@gmail.com"),
                new ContactBook(0, "CapG", "Prakash", "Jain", LocalDate.now(), "Matunga Road", "Patna", "Bihar", 279101, 984521894, "pjain@gmail.com")
        };
        Instant threadStart = Instant.now();
        addressBookDBService.addContactsToDBWithThreads(Arrays.asList(arrayOfContacts));
        Instant threadEnd = Instant.now();
        System.out.println("Duration with thread: " + Duration.between(threadStart, threadEnd));
        List<ContactBook> contactBookData = addressBookDBService.readData();
        Assert.assertEquals(5, contactBookData.size());
    }
}

