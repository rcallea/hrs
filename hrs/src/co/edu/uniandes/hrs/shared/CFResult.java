package co.edu.uniandes.hrs.shared;

import java.io.Serializable;

public class CFResult implements Serializable {
	
	private String[] data;
	private float precision;
	private float recall;
	
	/**
	 * @param data
	 * @param precision
	 * @param recall
	 */
	public CFResult() {
		super();
		this.data = new String[]{};
		this.precision = -2;
		this.recall = -1;
	}

	/**
	 * @param data
	 * @param precision
	 * @param recall
	 */
	public CFResult(String[] data, float precision, float recall) {
		super();
		this.data = data;
		this.precision = precision;
		this.recall = recall;
	}

	/**
	 * @return the data
	 */
	public String[] getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(String[] data) {
		this.data = data;
	}

	/**
	 * @return the precision
	 */
	public float getPrecision() {
		return precision;
	}

	/**
	 * @param precision the precision to set
	 */
	public void setPrecision(float precision) {
		this.precision = precision;
	}

	/**
	 * @return the recall
	 */
	public float getRecall() {
		return recall;
	}

	/**
	 * @param recall the recall to set
	 */
	public void setRecall(float recall) {
		this.recall = recall;
	}

	
}
