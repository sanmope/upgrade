package com.upgrade.campsite;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Reservation {
    @Id
    @GeneratedValue
    @Column(name="reservation_id")
    private Long id;
    private String userName;
    private String userEmail;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="reservation_id",referencedColumnName = "reservation_id")
    private Set<Campsite> campsiteRange = new HashSet<Campsite>();


    public Reservation() {
    }

    public Reservation(String userName, String userEmail, Set<Campsite> campsiteRange) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.campsiteRange = campsiteRange;
    }

    public Reservation(String name, Set<Campsite> campsiteRange) {
        userName = name;
        this.campsiteRange = campsiteRange;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<Campsite> getCampsiteRange() {
        return campsiteRange;
    }

    public void setCampsiteRange(Set<Campsite> campsiteRange) {
        this.campsiteRange = campsiteRange;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.userEmail = email;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
