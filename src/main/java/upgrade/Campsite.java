package upgrade;


import org.joda.time.DateTime;

public class Campsite {

	private  long id;
	private DateTime date;

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
