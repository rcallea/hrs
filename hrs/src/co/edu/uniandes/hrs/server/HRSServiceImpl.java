package co.edu.uniandes.hrs.server;

import java.util.ArrayList;

import org.recommender101.data.DataModel;
import org.recommender101.data.DefaultDataLoader;
import org.recommender101.recommender.baseline.NearestNeighbors;

import co.edu.uniandes.hrs.client.HRSService;
import co.edu.uniandes.hrs.shared.CFParameters;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class HRSServiceImpl extends RemoteServiceServlet implements HRSService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2112585968730455525L;

	@Override
	public String[] sendUser(CFParameters data) {
		String[] ret={"Listo"};

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
			
			System.out.println("Adicionando resultados a la lista CF");
			ArrayList<Integer> recommendations=(ArrayList<Integer>)rec.recommendItems(data.getUser());
			recommendations.add(0, -2); //Adicionar precision
			recommendations.add(0, -1); //Adicionar Recall
			ret=new String[recommendations.size()];

			for(int i=0;i<recommendations.size();i++) {
				ret[i]="" + recommendations.get(i);
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Fin de CF");

		return(ret);
	}

}
