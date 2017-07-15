package com.example.choi.cracker.data;

/**
 * Created by choi on 2017. 7. 15..
 */

public class User {
    int id;
    String name,email,auth_token;

    User(int id, String name, String email, String auth_token){
        this.name = name;
        this.id = id;
        this.email = email;
        this.auth_token = auth_token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
