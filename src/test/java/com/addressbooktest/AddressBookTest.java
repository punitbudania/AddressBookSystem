package com.addressbooktest;

import com.addressbook.AddressBookDBService;
import com.addressbook.ContactBook;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
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
}

