package co.edu.uniandes.hrs.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.recommender101.data.DataModel;
import org.recommender101.data.Rating;
import org.recommender101.recommender.baseline.NearestNeighbors;

import co.edu.uniandes.hrs.shared.CFParameters;
import co.edu.uniandes.hrs.shared.CFResult;

public class CollaborativeFiltering {
	private float precision=0;
	private float recall=0;

	public CFResult initCF(CFParameters data) {
		CFResult ret=new CFResult();

		// Simple test method.
		System.out.println("Iniciando CF");
		try {
			DataModel dm = new DataModel();
			DefaultDataLoader loader = new DefaultDataLoader();
			loader.setMinNumberOfRatingsPerUser(data.getRatings());
			String datasetSize="80";
			if(data.getDatasetSize().startsWith("6")) {
				datasetSize="60";
			} else if(data.getDatasetSize().startsWith("7")) {
				datasetSize="70";
			}
			loader.setFilename("./data/g" + datasetSize + "t.txt");
			System.out.println("Cargando datamodel");
			loader.loadData(dm);
			NearestNeighbors rec = new NearestNeighbors();
			rec.setDataModel(dm);
			rec.setCosineSimilarity(data.getRecommenderType());
			rec.setItemBased(data.getMeasureType());
			rec.setNeighbors(data.getNeighbors());

			System.out.println("Iniciando recomendador CF");
			rec.init();
			
			ArrayList<Integer> recommendations=(ArrayList<Integer>)rec.recommendItems(loader.getUserId().get(data.getUser()));
			String[] retList=new String[recommendations.size()];
			String[] retListData=new String[recommendations.size()];
			
			System.out.println("Adicionando resultados a la lista CF");
			for(int i=0;i<recommendations.size();i++) {
				retList[i]="" + loader.getBusiness().get(recommendations.get(i));
				System.out.println(retList[i]);
			}
			int maxDataSize=50;
			String[] retListSearch;
			if(retList.length<=maxDataSize) {
				maxDataSize=retList.length;
			}
			retListData=new String[maxDataSize];
			retListSearch=new String[maxDataSize];
			
			for(int i=0;i<maxDataSize;i++) {
				retListSearch[i]=retList[i];
			}
			
			ArrayList<String[]> retListAllData=MongoDB.getBusinessInfo(retListSearch);
			for(int i=0;i<retListAllData.size();i++) {
				String[] businessData=retListAllData.get(i);
				retListData[i]=businessData[7] + ": " +businessData[1] + " (" + businessData[4] + " - " + businessData[5] + ")"; 
			}

			System.out.println("Calculando precision y recall a CF");
			this.precisionRecallCF(data, recommendations);
			ret=new CFResult(retList, retListData, this.precision, this.recall);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Fin de CF");

		return(ret);
	}
	
	public void precisionRecallCF(CFParameters data, List<Integer> recommendations) {
		this.precision=0;
		this.recall=0;
		System.out.println("Iniciando mediciones");
		try {
			DataModel dm = new DataModel();
			DefaultDataLoader loader = new DefaultDataLoader();
			loader.setMinNumberOfRatingsPerUser(data.getRatings());
			String datasetSize="80";
			if(data.getDatasetSize().startsWith("6")) {
				datasetSize="60";
			} else if(data.getDatasetSize().startsWith("7")) {
				datasetSize="70";
			}
			loader.setFilename("/usr/local/tomcat7/webapps/hrs/data/g" + datasetSize + "v.txt");
			System.out.println("Cargando datamodel");
			loader.loadVerifyData(dm, data.getUser());

			System.out.println("Iniciando comparación para mediciones");
			
			Set<Rating> ratings=dm.getRatings();
			int tamRatings=0;
			int found=0;
			for(Rating r:ratings) {
				tamRatings++;
				int currentBusiness=r.item;
				for(int i=0;i<recommendations.size();i++) {
					int currentRecommendation=recommendations.get(i);
					if(currentRecommendation==currentBusiness) {
						found++;
						i=recommendations.size();
					}
				}
			}
			
			this.precision = ((float)found)/((float)(found + recommendations.size()));
			this.recall = ((float)found)/((float)(found + tamRatings));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Fin de Mediciones");
	}	
	
	public String[] initCF_hybrid(CFParameters data) {
		
		String[] retList = new String[50];
		// Simple test method.
		System.out.println("Iniciando CF");
		try {
			DataModel dm = new DataModel();
			DefaultDataLoader loader = new DefaultDataLoader();
			loader.setMinNumberOfRatingsPerUser(data.getRatings());
			String datasetSize="80";
			if(data.getDatasetSize().startsWith("6")) {
				datasetSize="60";
			} else if(data.getDatasetSize().startsWith("7")) {
				datasetSize="70";
			}
			loader.setFilename("/usr/local/tomcat7/webapps/hrs/data/g" + datasetSize + "t.txt");
			System.out.println("Cargando datamodel");
			loader.loadData(dm);
			NearestNeighbors rec = new NearestNeighbors();
			rec.setDataModel(dm);
			rec.setCosineSimilarity(data.getRecommenderType());
			rec.setItemBased(data.getMeasureType());
			rec.setNeighbors(data.getNeighbors());

			System.out.println("Iniciando recomendador CF");
			rec.init();
			
			ArrayList<Integer> recommendations=(ArrayList<Integer>)rec.recommendItems(loader.getUserId().get(data.getUser()));
			retList = new String[recommendations.size()];
			
			System.out.println("Adicionando resultados a la lista CF");
			for(int i=0;i<recommendations.size();i++) {
				retList[i]="" + loader.getBusiness().get(recommendations.get(i));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Fin de CF");

		return retList;
	}
	
}
