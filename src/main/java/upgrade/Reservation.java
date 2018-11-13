package upgrade;

import org.joda.time.DateTime;

public class Reservation {
    private Long id;
    private User user;
    private Campsite campsite;
    private DateTime checkin;
    private DateTime checkout;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Campsite getCampsite() {
        return campsite;
    }

    public void setCampsite(Campsite campsite) {
        this.campsite = campsite;
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
