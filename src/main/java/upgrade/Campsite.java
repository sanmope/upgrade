package upgrade;


import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Campsite {

	@Id
	@GeneratedValue
	private  long id;
	private DateTime date;

	public Campsite(DateTime date) {
		this.date = date;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

} 
