package com.addressbook;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

public class AddressBookCSV
{
    private static final String HOME = System.getProperty("user.home");
    private static String WORK_SPACE = "\\IdeaProjects\\AddressBook";

    public AddressBookCSV() {
    }

    public void writeToCSV(String addressBookName, LinkedList<ContactBook> contactList)
            throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException
    {
        try
        {
            Path workPath = Paths.get(HOME + WORK_SPACE + "\\OutputDirectory\\CSV");
            if(Files.notExists(workPath))
            {
                Files.createDirectory(workPath);
            }
            Path filePath = Paths.get(workPath + "\\" + addressBookName + "--contacts.csv");
            if(Files.notExists(filePath))
            {
                Files.createFile(filePath);
            }
            try (Writer writer = Files.newBufferedWriter(Paths.get(String.valueOf(filePath)));)
            {
                StatefulBeanToCsv<ContactBook> beanToCsv = new StatefulBeanToCsvBuilder<ContactBook>(writer)
                        .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
                beanToCsv.write(contactList);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public LinkedList<ContactBook> readCSV(File file) throws IOException {
        Path filePath = Paths.get(HOME + WORK_SPACE + "\\OutputDirectory\\CSV\\" + file);
        LinkedList<ContactBook> contactList = new LinkedList<ContactBook>();
        try(Reader reader = Files.newBufferedReader(Paths.get(String.valueOf(filePath)));
            CSVReader csvReader = new CSVReader(reader);)
        {
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext())!=null)
            {
                contactList.add(new ContactBook(nextRecord[1], nextRecord[2], nextRecord[3], nextRecord[4],
                        Long.parseLong(nextRecord[5]), Long.parseLong(nextRecord[6]), nextRecord[7]));
            }
        }
        return contactList;
    }
}
