package com.example.collegeflight.bean;
public class UserInfo {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String birthDate;
    private String passportCountry;
    private String passportNumber;
    private String passportExpireDate;

    private String phoneNumber;

    public UserInfo(String firstName, String lastName, String email, String birthDate, String passportCountry, String passportNumber, String passportExpireDate,String phoneNumber) {
        this.id=id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.passportCountry = passportCountry;
        this.passportNumber = passportNumber;
        this.passportExpireDate = passportExpireDate;
        this.phoneNumber=phoneNumber;
    }

    public UserInfo(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public UserInfo() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassportCountry() {
        return passportCountry;
    }

    public void setPassportCountry(String passportCountry) {
        this.passportCountry = passportCountry;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getPassportExpireDate() {
        return passportExpireDate;
    }

    public void setPassportExpireDate(String passportExpireDate) {
        this.passportExpireDate = passportExpireDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
