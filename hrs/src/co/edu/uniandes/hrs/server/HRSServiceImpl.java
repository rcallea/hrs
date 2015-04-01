package co.edu.uniandes.hrs.server;

/*import ConnectionDB;
import JaccardCoefficient;
import ValueComparator;*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.recommender101.data.DataModel;
import org.recommender101.data.DefaultDataLoader;
import org.recommender101.recommender.baseline.NearestNeighbors;

import co.edu.uniandes.hrs.client.HRSService;
import co.edu.uniandes.hrs.shared.CFParameters;
import co.edu.uniandes.hrs.shared.ConnectionDB;
import co.edu.uniandes.hrs.shared.ContentParameters;
import co.edu.uniandes.hrs.shared.JaccardCoefficient;
import co.edu.uniandes.hrs.shared.ValueComparator;

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

	public HashMap<String, String> getContentBusiness(ContentParameters data)
	{
		JaccardCoefficient jc = new JaccardCoefficient();
		
		String[] arrReq = ConnectionDB.ProcesarRequerimiento(data.getDescription());
		
		Map<String,List<String[]>> arrCompare = ConnectionDB.getKeyWordsNegociosComparar(data.getCategory(), data.getCity());
		
		double weightName = 1;
		double weightCategories = 0.8;
		double weightAttributes = 0.6;
		double weightComments = 0.4;
		double similarityTotal = 0;
	
		Map<String, Double> arrSimilarity = new HashMap<String, Double>();
		
		for (String business : arrCompare.keySet()) {
			List<String[]> lst = arrCompare.get(business);
			
			double similarityName = jc.similarity(arrReq, lst.get(0));
			double similarityCategories = jc.similarity(arrReq, lst.get(1));
			double similarityAttributes = jc.similarity(arrReq, lst.get(2));
			double similarityComments = jc.similarity(arrReq, lst.get(3));
			
			similarityTotal = (similarityName * weightName) + (similarityCategories * weightCategories) + (similarityAttributes * weightAttributes) + (similarityComments * weightComments);
			
			arrSimilarity.put(business, similarityTotal);
		}
		
		ValueComparator bvc =  new ValueComparator(arrSimilarity);
		TreeMap<String, Double> sorted_map = new TreeMap<String, Double>(bvc);
		sorted_map.putAll(arrSimilarity);
		// saco las x primeras
		int i = 0;
		String[] resultado = new String[(sorted_map.keySet().size() > 10 ? 10 : sorted_map.keySet().size())];
		for (String idBusiness : sorted_map.keySet()) {
			resultado[i] = idBusiness;
			i++;
			if (i == 10) {
				break;
			}
		}
		
		return getNameBusiness(resultado); 
	}
	
	private HashMap<String, String> getNameBusiness(String[] arrBusinessId) {

		HashMap<String, String> infoBusiness = new HashMap<String, String>(); 
		String name = "";
		for (String businessId : arrBusinessId) {
			name = ConnectionDB.getNombreNegocio(businessId);
			infoBusiness.put(businessId, name);
			//System.out.println(name);
		}

		return infoBusiness;
	}

	public String[] getInformationBusiness(String businessId)
	{
		
		return ConnectionDB.getInformationBusiness(businessId);
			
	}
}
