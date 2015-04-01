package co.edu.uniandes.hrs.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.recommender101.data.DataModel;
import org.recommender101.data.DefaultDataLoader;
import org.recommender101.recommender.baseline.NearestNeighbors;

import co.edu.uniandes.hrs.client.HRSService;
import co.edu.uniandes.hrs.shared.CBParametersL;
import co.edu.uniandes.hrs.shared.CBResultL;
import co.edu.uniandes.hrs.shared.CFParameters;
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

}
