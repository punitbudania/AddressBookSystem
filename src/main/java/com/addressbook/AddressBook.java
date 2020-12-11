package com.addressbook;

import java.util.*;
import java.util.stream.Collectors;


public class AddressBook
{
    public static void main(String[] args)
    {
        System.out.println("Welcome To Address Book");
        int a=0;

        boolean stop = false;
        while(!stop)
        {
            Scanner input = new Scanner(System.in);
            System.out.println("Select suitable Option:");
            System.out.println("Press 1 to Create New Contact");
            System.out.println("Press 2 to Delete the contact");
            System.out.println("Press 3 to Edit the contact");
            System.out.println("Press 4 to View the entries");
            System.out.println("Press 5 to Search persons by State");
            System.out.println("Press 6 to Search persons by City");
            System.out.println("Press 7 to count persons by State");
            System.out.println("Press 8 to count persons by City");
            System.out.println("Press 9 to sort entries by Name");
            System.out.println("Press 10 to sort entries by City");
            System.out.println("Press 11 to sort entries by State");
            System.out.println("Press 12 to sort entries by Zip");
            System.out.println("Press 13 to write address book to file");
            System.out.println("Press 14 to exit");

            int option = input.nextInt();

            switch (option)
            {

                case 1:
                    a++;
                    Scanner sc = new Scanner(System.in);
                    System.out.println("Enter Address Book name:");
                    String addressbook = sc.next();

                    System.out.println("Enter First Name:");
                    String firstname = sc.next();
                    if(a>1)
                    {
                        if(Adrsbook.containsKey(addressbook))
                        {
                            if (Adrsbook.get(addressbook).contains(firstname)) {
                                System.out.println("This entry already exist.");
                                break;
                            }
                            else
                            {
                                Fname.add(firstname);
                                Adrsbook.put(addressbook, firstname);
                                AddressBook.add(addressbook);
                                addcontact();
                            }
                        }
                        else
                        {
                            Fname.add(firstname);
                            Adrsbook.put(addressbook, firstname);
                            AddressBook.add(addressbook);
                            addcontact();
                        }
                    }
                    else
                    {
                        Fname.add(firstname);
                        Adrsbook.put(addressbook, firstname);
                        AddressBook.add(addressbook);
                        addcontact();
                    }
                    break;
                case 2:
                    delete();
                    break;
                case 3:
                    edit();
                    break;
                case 4:

                    display();
                    break;
                case 5:
                    sc = new Scanner(System.in);
                    System.out.println("Enter State:");
                    String selectState = sc.next();
                    List<String> ReqSPerson;
                    ReqSPerson = StatePerson.entrySet()
                            .stream()
                            .filter(e -> e.getKey().equals(selectState))
                            .map(Map.Entry::getValue)
                            .collect(Collectors.toList());
                    System.out.println(ReqSPerson);
                    break;
                case 6:
                    sc = new Scanner(System.in);
                    System.out.println("Enter City:");
                    String selectCity = sc.next();
                    List<String> ReqCPerson;
                    ReqCPerson = CityPerson.entrySet().stream()
                            .filter(e -> e.getValue().equals(selectCity))
                            .map(Map.Entry::getKey)
                            .collect(Collectors.toList());
                    System.out.println(ReqCPerson);
                    break;
                case 7:
                    sc = new Scanner(System.in);
                    System.out.println("Enter State:");
                    String countState = sc.next();
                    long totalState = StatePerson.entrySet()
                            .stream()
                            .filter(e -> e.getKey().equals(countState))
                            .count();
                    System.out.println(totalState);
                    break;
                case 8:
                    sc = new Scanner(System.in);
                    System.out.println("Enter City:");
                    String countCity = sc.next();
                    long totalCity = CityPerson.entrySet()
                            .stream()
                            .filter(e -> e.getKey().equals(countCity))
                            .count();
                    System.out.println(totalCity);
                    break;
                case 9:
                    List<String> sortedEntries;
                    sortedEntries = Adrsbook.entrySet()
                            .stream()
                            .sorted(Map.Entry.comparingByValue())
                            .map(Map.Entry::getValue)
                            .collect(Collectors.toList());
                    System.out.println(sortedEntries);
                    break;
                case 10:
                    CityPerson.entrySet()
                            .stream()
                            .sorted(Map.Entry.comparingByValue())
                            .forEach(System.out::println);
                    break;
                case 11:
                    HashMap<String, String> z;
                    z = StatePerson.entrySet()
                            .stream()
                            .sorted(Map.Entry.comparingByKey())
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                    (oldValue, newValue) -> oldValue, LinkedHashMap::new));
                    System.out.println(z);
                    break;
                case 12:
                    ZipPerson.entrySet()
                            .stream()
                            .sorted(Map.Entry.comparingByKey())
                            .forEach(System.out::println);
                    break;
                case 13:
                    System.out.println("Enter AddressBook name:");
                    sc = new Scanner(System.in);
                    String addressBookName = sc.next();
                    write(addressBookName);
                    break;
                case 14:
                    System.out.println("Thank You!!");
                    stop = true;
                    break;
                default:
                    System.out.println("invalid selection");
            }
        }
    }



    static int entry = 0;
    static HashMap<String, ContactBook> Contact= new HashMap<String, ContactBook>();
    static HashMap<String, String> Adrsbook= new HashMap<String, String>();
    static HashMap<String, String> CityPerson = new HashMap<String, String>();
    static HashMap<String, String> StatePerson = new HashMap<String, String>();
    static HashMap<Long, String> ZipPerson = new HashMap<Long, String>();
    //static LinkedList<ContactBook> contactDetails = new LinkedList<>();
    static ArrayList<String> AddressBook = new ArrayList<String>();
    static ArrayList<String> Fname = new ArrayList<String>();
    static ArrayList<String> Lname = new ArrayList<String>();
    static ArrayList<String> City = new ArrayList<String>();
    static ArrayList<String> State = new ArrayList<String>();
    static ArrayList<Long> Zip = new ArrayList<Long>();
    static ArrayList<Long> Mobile = new ArrayList<Long>();
    static ArrayList<String> Email = new ArrayList<String>();


    public static void addcontact()
    {
        entry++;
        Scanner input = new Scanner(System.in);

        System.out.println("Enter Last Name:");
        String lastname = input.next();
        Lname.add(lastname);

        System.out.println("Enter City:");
        String city = input.next();
        City.add(city);
        CityPerson.put(Fname.get(entry-1), city);

        System.out.println("Enter State:");
        String state = input.next();
        State.add(state);
        StatePerson.put(state, Fname.get(entry-1));

        System.out.println("Enter Zip:");
        Long zip = input.nextLong();
        Zip.add(zip);
        ZipPerson.put(zip, Fname.get(entry-1));

        System.out.println("Enter Phone Number:");
        long phonenumber = input.nextLong();
        Mobile.add(phonenumber);

        System.out.println("Enter Email ID:");
        String email = input.next();
        Email.add(email);

        ContactBook contactList = new ContactBook(Fname.get(entry-1), lastname, city, state, zip, phonenumber, email);
        Contact.put(AddressBook.get(entry-1), contactList);
    }

    public static void write(String addressBookName)
    {
        new AddressBookFileIO().writeAddressBookToFile(addressBookName, Contact.get(addressBookName));
    }

    public static void display()
    {
        System.out.println(CityPerson);
        System.out.println(StatePerson);
        for (int i=1; i<=entry; i++)
        {
            System.out.println("******************Entry No.: " + i);
            System.out.println("First Name: " + Fname.get(i-1));
            System.out.println("Last Name: " + Lname.get(i-1));
            System.out.println("City: " + City.get(i-1));
            System.out.println("State: " + State.get(i-1));
            System.out.println("Zip: " + Zip.get(i-1));
            System.out.println("Phone no.: " + Mobile.get(i-1));
            System.out.println("Email: " + Email.get(i-1));
        }
        System.out.println(Contact);
    }

    public static void delete()
    {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter Name: ");
        String over = input.next();
        int j = Fname.indexOf(over);
        Fname.remove(j);
        Lname.remove(j);
        City.remove(j);
        State.remove(j);
        Zip.remove(j);
        Mobile.remove(j);
        Email.remove(j);
        entry -= 1;
    }

    public static void edit()
    {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter Name: ");
        String over = input.next();
        int k = Fname.indexOf(over);
        System.out.println("Enter updated mobile no.: ");
        long elem = input.nextLong();
        Mobile.set(k, elem);
    }
}
