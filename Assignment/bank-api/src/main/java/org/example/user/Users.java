package org.example.user;

import jakarta.persistence.Entity;
import jakarta.persistence.*;


@Entity(name = "users")
public class Users {
    @Id
    private Long user_id;
    private String user_name;
    private String user_password;
    private String user_roles;
    private String user_status;
    private Long account_id;

    public Users(){}

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long id) {
        this.user_id = id;
    }
    public Long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Long id) {
        this.account_id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String uname) {
        this.user_name = uname;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String password) {
        this.user_password = password;
    }

    public String getUser_roles() {
        return user_roles;
    }

    public void setUser_roles(String roles) {
        this.user_roles = roles;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String status) {
        this.user_status = status;
    }



}