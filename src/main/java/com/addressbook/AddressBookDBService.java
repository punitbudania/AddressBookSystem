package com.addressbook;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBService
{
    private int connectionCounter = 0;

    private Connection getConnection() throws SQLException {
        connectionCounter++;
        String jdbcURL = "jdbc:mysql://localhost:3306/address_book_service?useSSL=false";
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
}
