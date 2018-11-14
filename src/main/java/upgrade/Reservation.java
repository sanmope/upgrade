package upgrade;


import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Reservation {
    @Id
    @GeneratedValue
    private Long id;
    private String userName;
    private DateTime checkin;
    private DateTime checkout;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public Reservation(String name, DateTime from, DateTime to) {
        checkin = from;
        checkout = to;
        userName = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getCheckin() {
        return checkin;
    }

    public void setCheckin(DateTime checkin) {
        this.checkin = checkin;
    }

    public DateTime getCheckout() {
        return checkout;
    }

    public void setCheckout(DateTime checkout) {
        this.checkout = checkout;
    }
}
