package com.addressbook;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class AddressBookCSV
{
    //private static final String HOME = System.getProperty("user.home");
    private static String WORK_SPACE = "C:\\Users\\PUNIT BUDANIA\\IdeaProjects\\AddressBook\\OutputDirectory\\CSV\\CapG--contacts.csv";

    public AddressBookCSV() {
    }

    public void writeToCSV(String addressBookName, LinkedList<ContactBook> contactList)
            throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException
    {
        try
        {
            ColumnPositionMappingStrategy <ContactBook> mappingStrategy = new ColumnPositionMappingStrategy<>();
            mappingStrategy.setType(ContactBook.class);
            String[] columns = new String[]
                    { "first","last","address","city","State", "zip", "mobile", "email" };
            mappingStrategy.setColumnMapping(columns);
            try(Writer writer = Files.newBufferedWriter(Paths.get(WORK_SPACE))) {
                StatefulBeanToCsv<ContactBook> beanToCsv = new StatefulBeanToCsvBuilder(writer).withMappingStrategy(mappingStrategy).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
                beanToCsv.write(contactList);
            }
            /*
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


            try (//Writer writer = Files.newBufferedWriter(Paths.get(String.valueOf(filePath)));
                 CSVWriter writer = new CSVWriter(filePath); )
            {

                System.out.println(filePath);
                System.out.println(contactList);
                StatefulBeanToCsv<ContactBook> beanToCsv = new StatefulBeanToCsvBuilder<ContactBook>(writer)
                        .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
                beanToCsv.write(contactList);


                writer.writeNext(contactList.toArray(String[]::new));
            }
            */
            /*
            File file = new File(WORK_SPACE);

            FileWriter outputfile = new FileWriter(file);

            CSVWriter writer = new CSVWriter(outputfile);

            //LinkedList<ContactBook> data = contactList;
            String[] a = new String[contactList.size()];
            a = contactList.toArray(a);
            String[] header = { "Name", "Class", "Marks" };
            writer.writeNext(header);

            // add data to csv
            String[] data1 = { "Aman", "10", "620" };
            writer.writeNext(data1);
            String[] data2 = { "Suraj", "10", "630" };
            writer.writeNext(data2);
            writer.writeNext(a);

            writer.close();

             */
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /*
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

     */
}
