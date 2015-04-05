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
	
	/**
	 * @param city
	 * @param category
	 * @param description
	 */
	public ContentParameters()
	{}
	
	public ContentParameters(String city, String category, String description) {
		super();
		this.city = city;
		this.category = category;
		this.description = description;
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

}
