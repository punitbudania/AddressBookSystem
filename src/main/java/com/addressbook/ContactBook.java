package com.addressbook;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

public class ContactBook extends LinkedList<ContactBook> {
    public String fname;
    public String lname;
    public String city;
    public String state;
    public String email;
    public Long zip;
    public Long mobile;
    public String book;
    public int id;
    public LocalDate dateAdded;
    public String address;

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fname, lname, city, state, email, zip, mobile, book, id, dateAdded, address);
    }

    public ContactBook(String fname, String lname, String city, String state, Long zip, Long mobile, String email)
    {
        this.fname = fname;
        this.lname = lname;
        this.city = city;
        this.state = state;
        this.email = email;
        this.zip = zip;
        this.mobile = mobile;
    }

    public ContactBook(int id, String book, String fname, String lname)
    {
        this.id = id;
        this.book = book;
        this.fname = fname;
        this.lname = lname;
    }

    public ContactBook(int id, String book, String fname, String lname, LocalDate dateAdded, String address, String city, String state, long zip, long mobile, String email)
    {
        this.id = id;
        this.book = book;
        this.fname = fname;
        this.lname = lname;
        this.dateAdded = dateAdded;
        this.city = city;
        this.state = state;
        this.email = email;
        this.zip = zip;
        this.mobile = mobile;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getZip() {
        return zip;
    }

    public void setZip(Long zip) {
        this.zip = zip;
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    /*
    @Override
    public String toString() {
        return "ContactBook{" +
                "fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", email='" + email + '\'' +
                ", zip=" + zip +
                ", mobile=" + mobile +
                '}';
    }

     */

    @Override
    public String toString()
    {
        return "{ '" + fname + "', '" + lname + "', '" + city + "', '" + state + "', '" + email + "', '" + zip + "', '" + mobile + "' }";
    }
}
