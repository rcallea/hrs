package co.edu.uniandes.hrs.shared;

public interface SimilarityMeasure extends java.io.Serializable{
    
    public double similarity(String[] x, String[] y);

}