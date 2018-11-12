package upgrade;


public class Campsite {

	private final long id;
	private final String email;
	
	public Campsite (long id, String email) {
		this.id =id;
		this.email=email;
	}
	
	public long getId(){
		return id;
	}
	
	public String getEmail(){
		return email;
	}

} 
