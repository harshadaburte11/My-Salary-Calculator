package com.example.mysalaryapp;
public class User {
    private String fullName;
    private String username;
    private String email;
    private String mobile;
    private String address;



    private String designation;
    private String selectedDate;
    private String salary;
    private String  haStr,taStr,daStr,pfStr;
private String password;
    public User() {
        // Default constructor required for Firebase
    }



    public User(String fullName, String username, String email, String password, String mobile, String address, String designation, String salary, String selectedDate, String ha, String ta, String da, String pf) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
        this.designation = designation;
        this.selectedDate=selectedDate;
        this.salary=salary;
        this.password=password;
        this.haStr=ha;
        this.daStr=da;
        this.taStr=ta;
        this.pfStr=pf;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAddress() {
        return address;
    }

    public String getDesignation() {
        return designation;
    }

    public String getSelectedDate() {
        return selectedDate;
    }

    public String getSalary() {
        return salary;
    }

    public String getPassword() {
        return password;
    }

    public String getHaStr() {
        return haStr;
    }

    public String getTaStr() {
        return taStr;
    }

    public String getDaStr() {
        return daStr;
    }

    public String getPfStr() {
        return pfStr;
    }
// Add getters and setters as needed
}
