package com.addressbook;

import com.google.gson.Gson;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;

public class AddressBookJSON
{
    private static final String HOME = System.getProperty("user.home");
    private static String WORK_SPACE = "\\IdeaProjects\\AddressBook";

    public AddressBookJSON() {
    }

    public void writeToJson(String addressBookName, LinkedList<ContactBook> contactList)
    {
        try
        {
            Path workPath = Paths.get(HOME + WORK_SPACE + "\\OutputDirectory\\Json");
            if(Files.notExists(workPath))
            {
                Files.createDirectory(workPath);
            }
            Path filePath = Paths.get(workPath + "\\" + addressBookName + "--contacts.json");
            if(Files.notExists(filePath))
            {
                Files.createFile(filePath);
            }
            try (Writer writer = Files.newBufferedWriter(Paths.get(String.valueOf(filePath)));)
            {
                Gson gson = new Gson();
                String json = gson.toJson(contactList);
                writer.write(json);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public LinkedList<ContactBook> readJson(File file) throws IOException {
        Path filePath = Paths.get(HOME + WORK_SPACE + "\\OutputDirectory\\Json\\" + file);
        LinkedList<ContactBook> contactList = new LinkedList<ContactBook>();
        try(Reader reader = Files.newBufferedReader(Paths.get(String.valueOf(filePath)));)
        {
            Gson gson = new Gson();
            ContactBook[] contacts = gson.fromJson(reader, ContactBook[].class);
            contactList = new LinkedList(Arrays.asList(contacts));
        }
        return contactList;
    }
}
