package upgrade;


import javax.persistence.*;
import java.util.Date;

@Entity
public class Campsite {
	//Campsite class represents a reservation for one day.

	@Id
	@GeneratedValue
	@Column(name="campsite_id")
	private  long id;
	private Date date;

	private Long reservation_id;


	@Version
	private Long version;

    public Campsite() {
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

	public Long getReservation_id() {
		return reservation_id;
	}

	public void setReservation_id(Long reservation_id) {
		this.reservation_id = reservation_id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
}
