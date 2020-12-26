package com.addressbooktest;

import com.addressbook.AddressBookDBService;
import com.addressbook.ContactBook;
import org.junit.Assert;
import org.junit.Test;

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
}
