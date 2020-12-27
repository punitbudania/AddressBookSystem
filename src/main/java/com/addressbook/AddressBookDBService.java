package com.addressbook;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddressBookDBService
{
    private int connectionCounter = 0;

    private Connection getConnection() throws SQLException {
        connectionCounter++;
        String jdbcURL = "jdbc:mysql://localhost:3306/address_book_service?allowPublicKeyRetrieval=true&useSSL=false";
        String userName = "root";
        String password = "110016";
        Connection con;
        System.out.println("Connecting to database" + jdbcURL);
        //System.out.println("Processing thread: " + Thread.currentThread().getName() +
          //      " Connecting to database with id:" + connectionCounter);
        con = DriverManager.getConnection(jdbcURL, userName, password);
        //System.out.println("Processing thread: " + Thread.currentThread().getName() +
          //      " Id: " + connectionCounter + " Connection is successful!!" + con);
        System.out.println("Connection is successful!!!" + con);
        return  con;
    }

    public List<ContactBook> readData()
    {
        List<ContactBook> contactBook = null;
        String sql = "SELECT * FROM customer_details;";
        try(Connection connection = this.getConnection())
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            contactBook = this.getContactData(resultSet);
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
        return contactBook;
    }

    private List<ContactBook> getContactData(ResultSet resultSet)
    {
        List<ContactBook> contactBookList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String book = resultSet.getString("book_name");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                contactBookList.add(new ContactBook(id, book, firstName, lastName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactBookList;
    }

    public int updateData(String firstName, String book)
    {
        String sql = String.format("update customer_details set book_name = '%s' where first_name = '%s'", book, firstName);
        try (Connection connection = this.getConnection())
        {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    public List<ContactBook> getCustomerFromDateRange(LocalDate startDate, LocalDate endDate)
    {
        List<ContactBook> contactBookList = new ArrayList<>();
        String sql = String.format("SELECT * FROM customer_details WHERE date_added BETWEEN '%s' AND '%s';",
                                            Date.valueOf(startDate), Date.valueOf(endDate));
        try (Connection connection = this.getConnection())
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            contactBookList = this.getContactData(resultSet);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return contactBookList;
    }

    public int getCountByCity(String city)
    {
        int count = -1;
        String sql = String.format("select count(city) from customer_address where city = '%s'", city);
        try(Connection connection = this.getConnection())
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()) count = resultSet.getInt(1);
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
        return count;
    }

    public void addContactToDatabase(String book, String firstName, String lastName, LocalDate date, String address, String city, String state, long zip, long phoneNo, String email)
    {
        HashMap<String, Integer> bookDetails = new HashMap<>();
        bookDetails.put("CapG", 1);
        bookDetails.put("Bridgelabz", 2);
        Connection connection = null;
        try
        {
            connection = this.getConnection();
            connection.setAutoCommit(false);
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

        int contactID = -1;
        try (Statement statement = connection.createStatement())
        {
            String sql = String.format("INSERT INTO customer_details (book_name, first_name, last_name, date_added)" +
                    "VALUES ('%s', '%s', '%s', '%s')", book, firstName, lastName, Date.valueOf(date));
            int rowAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
            if(rowAffected == 1)
            {
                ResultSet resultSet = statement.getGeneratedKeys();
                if(resultSet.next()) contactID = resultSet.getInt(1);
            }
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try(Statement statement = connection.createStatement())
        {
            String sql = String.format("INSERT INTO customer_address (customer_id, address, city, state, zip, phoneNo, email)" +
                    "VALUES (%s, '%s', '%s', '%s', %s, %s, '%s')", contactID, address, city, state, zip, phoneNo, email);
            statement.executeUpdate(sql);
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try(Statement statement = connection.createStatement())
        {
            int bookID = -1;
            for (String i : bookDetails.keySet())
            {
                if(i == book)
                {
                    bookID = bookDetails.get(i);
                }
            }
            String sql = String.format("INSERT INTO customer_book (book_id, customer_id) VALUES (%s, %s)", bookID, contactID);
            statement.executeUpdate(sql);
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        finally
        {
            if(connection != null)
            {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    public void addContactsToDBWithThreads(List<ContactBook> contactBookList)
    {
        HashMap<Integer, Boolean> contactAdditionStatus = new HashMap<>();
        contactBookList.forEach(contact -> {
            Runnable task = () -> {
                contactAdditionStatus.put(contact.hashCode(), false);
                System.out.println("Contact being added: " + Thread.currentThread().getName());
                this.addContactToDatabase(contact.book, contact.fname, contact.lname, contact.dateAdded, contact.address,
                                            contact.city, contact.state, contact.zip, contact.mobile, contact.email);
                System.out.println("Contact added: " + Thread.currentThread().getName());
                contactAdditionStatus.put(contact.hashCode(), true);
            };
            Thread thread = new Thread(task, contact.fname);
            thread.start();
        });
        while (contactAdditionStatus.containsValue(false))
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
