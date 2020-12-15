package com.addressbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

public class AddressBookFileIO
{
    private static final String HOME = System.getProperty("user.home");
    private static String WORK_SPACE = "\\IdeaProjects\\AddressBook";

    public AddressBookFileIO() {
    }

    public void writeAddressBookToFile(String addressBookName, LinkedList<ContactBook> contactList)
    {
        try
        {
            Path workPath = Paths.get(HOME + WORK_SPACE + "\\OutputDirectory\\FileIO");
            if(Files.notExists(workPath))
            {
                Files.createDirectories(workPath);
            }
            Path tempPath = Paths.get(workPath + "\\" + addressBookName + "--contacts.txt");
            if(Files.notExists(tempPath))
            {
                Files.createFile(tempPath);
            }
            StringBuffer contactBuffer = new StringBuffer();
            contactList.stream().forEach(contact -> {
                String contactData = contact.toString().concat("\n");
                contactBuffer.append(contactData);
            });
            Files.write(tempPath, contactBuffer.toString().getBytes());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public LinkedList<ContactBook> readContacts(File file)
    {
        Path filePath = Paths.get(HOME + WORK_SPACE + "\\OutputDirectory\\FileIO\\" + file);
        LinkedList<ContactBook> contactList = new LinkedList<ContactBook>();
        try
        {
            BufferedReader reader = Files.newBufferedReader(filePath);
            String currentLine;
            while ((currentLine = reader.readLine()) != null)
            {
                String[] contactDetails = currentLine.trim().split("[,\\s]{0,}[a-zA-Z]+[=]{1}");
                contactList.add(new ContactBook(contactDetails[1], contactDetails[2], contactDetails[3], contactDetails[4],
                                    Long.parseLong(contactDetails[5]), Long.parseLong(contactDetails[6]), contactDetails[7]));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contactList;
    }
}
