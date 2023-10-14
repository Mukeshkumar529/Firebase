package com.example.firebaseconcepts;

public class Information_Data {

    private String Email;
    private String Name;

    public Information_Data() {

    }

    public Information_Data(String email, String name) {
        Email = email;
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
