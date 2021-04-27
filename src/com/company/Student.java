package com.company;

public class Student {
    private String Name;
    private String SID;
    private String City;
    private Integer PostCode;
    private String Country;

    public Student(String StudentName, String StudentID, String city, Integer postCode, String country) {
        Name = StudentName;
        SID = StudentID;
        City = city;
        PostCode = postCode;
        Country = country;
    }

    public Student(String Sname, String ID) {
        Name = Sname;
        SID = ID;
        City = null;
        PostCode = null;
        Country = null;
    }

    public String getName() {
        return Name;
    }

    @Override
    public String toString() {
        return SID + " : " + Name;
    }
}
