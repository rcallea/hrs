package co.edu.uniandes.hrs.shared;

import java.io.Serializable;

public class CBParametersL implements Serializable {

	private int minTermFrequency=0;
	private int minDocFrequency=0;
	private int minWordLen=0;
	private int user=0;
	
	
	/**
	 * 
	 */
	public CBParametersL() {
		super();
	}


	/**
	 * @param minTermFrequency
	 * @param minDocFrequency
	 * @param minWordLen
	 * @param user
	 */
	public CBParametersL(int minTermFrequency, int minDocFrequency,
			int minWordLen, int user) {
		super();
		this.minTermFrequency = minTermFrequency;
		this.minDocFrequency = minDocFrequency;
		this.minWordLen = minWordLen;
		this.user = user;
	}


	/**
	 * @return the minTermFrequency
	 */
	public int getMinTermFrequency() {
		return minTermFrequency;
	}


	/**
	 * @param minTermFrequency the minTermFrequency to set
	 */
	public void setMinTermFrequency(int minTermFrequency) {
		this.minTermFrequency = minTermFrequency;
	}


	/**
	 * @return the minDocFrequency
	 */
	public int getMinDocFrequency() {
		return minDocFrequency;
	}


	/**
	 * @param minDocFrequency the minDocFrequency to set
	 */
	public void setMinDocFrequency(int minDocFrequency) {
		this.minDocFrequency = minDocFrequency;
	}


	/**
	 * @return the minWordLen
	 */
	public int getMinWordLen() {
		return minWordLen;
	}


	/**
	 * @param minWordLen the minWordLen to set
	 */
	public void setMinWordLen(int minWordLen) {
		this.minWordLen = minWordLen;
	}


	/**
	 * @return the user
	 */
	public int getUser() {
		return user;
	}


	/**
	 * @param user the user to set
	 */
	public void setUser(int user) {
		this.user = user;
	}
	
	
}
