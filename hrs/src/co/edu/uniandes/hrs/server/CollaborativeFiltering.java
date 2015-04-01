package co.edu.uniandes.hrs.server;

import java.util.ArrayList;
import java.util.List;

import org.recommender101.data.DataModel;
import org.recommender101.data.DefaultDataLoader;
import org.recommender101.recommender.baseline.NearestNeighbors;

import co.edu.uniandes.hrs.shared.CFParameters;
import co.edu.uniandes.hrs.shared.CFResult;

public class CollaborativeFiltering {

	private static final long serialVersionUID = -2112585968730455525L;

	public CFResult initCF(CFParameters data) {
		CFResult ret=new CFResult();

		// Simple test method.
		System.out.println("Iniciando CF");
		try {
			//TODO cambiar la manera de llenar el datamodel
			DataModel dm = new DataModel();
			DefaultDataLoader loader = new DefaultDataLoader();
			loader.setMinNumberOfRatingsPerUser("10");
			loader.setFilename("C:\\Users\\Ricardo\\Documents\\Maestría\\05SRecomendacion\\workspace\\recommender101\\data\\movielens\\ratings.txt");
			System.out.println("Cargando datamodel");
			loader.loadData(dm);
			NearestNeighbors rec = new NearestNeighbors();
			rec.setDataModel(dm);
			rec.setCosineSimilarity(data.getRecommenderType());
			rec.setItemBased(data.getMeasureType());
			rec.setNeighbors(data.getNeighbors());

			System.out.println("Iniciando recomendador CF");
			rec.init();
			
			ArrayList<Integer> recommendations=(ArrayList<Integer>)rec.recommendItems(data.getUser());
			String[] retList=new String[recommendations.size()];
			
			System.out.println("Adicionando resultados a la lista CF");
			for(int i=0;i<recommendations.size();i++) {
				retList[i]="" + recommendations.get(i);
			}

			System.out.println("Calculando precision y recall a CF");
			ret=new CFResult(retList, precisionCF(recommendations), recallCF(recommendations));
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Fin de CF");

		return(ret);
	}
	
	public float precisionCF(List<Integer> recommendations) {
		return(-1);
	}

	public float recallCF(List<Integer> recommendations) {
		return(-2);
	}
	
	
}
