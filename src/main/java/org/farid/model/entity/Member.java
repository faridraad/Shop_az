package org.farid.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "member")
public class Member extends BaseEntity<Integer> {

    @Column(name = "username", length = 50, nullable = false, unique = true)
    String username;

    @Column(name = "password", length = 100)
    String password;

    @Column(name = "first_name", length = 50)
    String firstName;

    @Column(name = "last_name", length = 50)
    String lastName;

    @Column(name = "mobile_number", length = 15)
    String mobileNumber;

    @Column(name = "address", length = 1500)
    String address;

    @Column(name = "gender")
    Integer gender = 0;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    List<Vote> votes;


    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    List<Comment> comments;

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

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

