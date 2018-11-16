package upgrade;


import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Campsite {
	//Campsite class represents a reservation for one day.

	@Id
	@GeneratedValue
	private  long id;
	private Date date;
	private Reservation reservation;

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}


	public Campsite(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

} 
