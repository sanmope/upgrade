package upgrade;


import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity
public class Reservation {
    @Id
    @GeneratedValue
    private Long id;
    private String userName;
    @OneToMany
    @JoinColumn(name="reservation_id",referencedColumnName = "id")
    private Set<Campsite> campsiteRange = new HashSet<Campsite>();


    public Reservation() {
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

}
