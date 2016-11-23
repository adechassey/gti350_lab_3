package com.company.meetsports.Entities;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class User {
    @SerializedName("id_user")
    private Integer id_user;
    @SerializedName("name")
    private String name;
    @SerializedName("surname")
    private String surname;
    @SerializedName("gender")
    private String gender;
    @SerializedName("age")
    private Integer age;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("date")
    private Timestamp date;

    public User(Integer id_user, String name, String surname, String gender, Integer age, String email, String password,
                Timestamp date) {
        super();
        this.id_user = id_user;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.age = age;
        this.email = email;
        this.password = password;
        this.date = date;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}