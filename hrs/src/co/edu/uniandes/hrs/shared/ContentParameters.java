package co.edu.uniandes.hrs.shared;

import java.io.Serializable;

public class ContentParameters implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String city="";
	private String category="";
	private String description="";
	private String day = "";
	private String hour = "";
	
	/**
	 * @param city
	 * @param category
	 * @param description
	 */
	public ContentParameters()
	{}
	
	public ContentParameters(String city, String category, String description, String day, String hour) {
		super();
		this.city = city;
		this.category = category;
		this.description = description;
		this.day = day;
		this.hour = hour;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}
}
