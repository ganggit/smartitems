package com.smarthome.spring.jpa.postgresql.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	@Column(name = "fullName")
    private String fullName;
	@Column(name = "address")
    private String address;
	@Column(name = "phone")
    private String phone;
	@Column(name = "username")
    private String username;
	@Column(name = "password")
    private String password;
	@Column(name = "acl")
    private String acl;
	@Column(name = "image")
    private File image;

	public User() {
		
	}
	
	public User(String fullName, String address, String phone, String username, String password, String acl, File image) {
		this.fullName = fullName;
		this.address = address;
		this.phone = phone;
		this.username = username;
		this.password = password;
		this.acl = acl;
		this.image = image;
	}
	
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String location) {
        this.address = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getACL() {
        return acl; // admin, root, other use
    }

    public void setACL(String category) {
        this.acl = category;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }
}
