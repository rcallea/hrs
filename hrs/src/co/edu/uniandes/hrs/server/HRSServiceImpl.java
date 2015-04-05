package co.edu.uniandes.hrs.server;

/*import ConnectionDB;
import JaccardCoefficient;
import ValueComparator;*/

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;

import org.recommender101.data.DataModel;
import org.recommender101.data.DefaultDataLoader;
import org.recommender101.recommender.baseline.NearestNeighbors;

import co.edu.uniandes.hrs.client.HRSService;
import co.edu.uniandes.hrs.shared.CBParametersL;
import co.edu.uniandes.hrs.shared.CBResultL;
import co.edu.uniandes.hrs.shared.CFParameters;
import co.edu.uniandes.hrs.shared.ConnectionDB;
import co.edu.uniandes.hrs.shared.ContentParameters;
import co.edu.uniandes.hrs.shared.ContentResult;
import co.edu.uniandes.hrs.shared.JaccardCoefficient;
import co.edu.uniandes.hrs.shared.ValueComparator;
import co.edu.uniandes.hrs.shared.CFResult;




import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class HRSServiceImpl extends RemoteServiceServlet implements HRSService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2112585968730455525L;

	@Override
	public CFResult initCF(CFParameters data) {
		return(new CollaborativeFiltering().initCF(data)); 
	}

	@Override
	public CBResultL initCBL(CBParametersL data) {
		try {
			return(new ContentBasedL().initCB(data));
		} catch (IOException e) {}
		return(new CBResultL());
	}

	public List<ContentResult> getContentBusiness(ContentParameters data, String[] listCF)
	{
		List<ContentResult> arrResult = new ArrayList<ContentResult>();
		HashMap<String,ContentResult> arrResultAux = new HashMap<String, ContentResult>();
		
		JaccardCoefficient jc = new JaccardCoefficient();
		
		String[] arrReq = ConnectionDB.ProcesarRequerimiento(data.getDescription());
		
		Map<String,List<String[]>> arrCompare = ConnectionDB.getKeyWordsNegociosComparar(data.getCategory(), data.getCity(), listCF);
				
		double weightName = 1;
		double weightCategories = 1;
		double weightAttributes = 0.8;
		double weightComments = 0.5;
		double similarityTotal = 0;
	
		Map<String, Double> arrSimilarity = new HashMap<String, Double>();
		ContentResult contResult = new ContentResult();
		
		for (String business : arrCompare.keySet()) {
			contResult = new ContentResult();
			List<String[]> lst = arrCompare.get(business);
			
			double similarityName = jc.similarity(arrReq, lst.get(0));
			double similarityCategories = jc.similarity(arrReq, lst.get(1));
			double similarityAttributes = jc.similarity(arrReq, lst.get(2));
			double similarityComments = jc.similarity(arrReq, lst.get(3));
			
			similarityTotal = (similarityName * weightName) + (similarityCategories * weightCategories) + (similarityAttributes * weightAttributes) + (similarityComments * weightComments);
			
			arrSimilarity.put(business, similarityTotal);
			
			contResult.setIdBusiness(business);
			contResult.setKeyWordsName(lst.get(0));
			contResult.setKeyWordsCategories(lst.get(1));
			contResult.setKeyWordsAttributes(lst.get(2));
			contResult.setKeyWordsComments(lst.get(3));
			contResult.setKeyWordsRequest(arrReq);
			contResult.setSimilarityName(similarityName);
			contResult.setSimilarityCategories(similarityCategories);
			contResult.setSimilarityAttributes(similarityAttributes);
			contResult.setSimilarityComments(similarityComments);
			contResult.setSimilarityTotal(similarityTotal);
			arrResultAux.put(business, contResult);
		}
		
		ValueComparator bvc =  new ValueComparator(arrSimilarity);
		TreeMap<String, Double> sorted_map = new TreeMap<String, Double>(bvc);
		sorted_map.putAll(arrSimilarity);
		
		
		// saco las x primeras
		int i = 0;
		for (String idBusiness : sorted_map.keySet()) {
			ContentResult result = new ContentResult();
			result = arrResultAux.get(idBusiness);
			result.setName(ConnectionDB.getNombreNegocio(idBusiness));
			arrResult.add(result);
			i++;
			if (i == 20) {
				break;
			}
		}
		
		return arrResult; 
	}
	
	public String[] getInformationBusiness(String businessId)
	{
		
		return ConnectionDB.getInformationBusiness(businessId);
			
	}

	public List<ContentResult> getHybridBusiness(CFParameters cfData, ContentParameters contentData)
	{
		String[] listCF = new CollaborativeFiltering().initCF_hybrid(cfData);
		return getContentBusiness(contentData, listCF);		
	}

}
