package co.edu.uniandes.hrs.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.edu.uniandes.hrs.shared.CBParametersL;
import co.edu.uniandes.hrs.shared.CBResultL;
import co.edu.uniandes.hrs.shared.CFParameters;
import co.edu.uniandes.hrs.shared.ContentParameters;
import co.edu.uniandes.hrs.shared.CFResult;



import co.edu.uniandes.hrs.shared.ContentResult;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("hrsRemoteService")
public interface HRSService extends RemoteService{

	//String[] sendUser(CFParameters data);
	List<ContentResult> getContentBusiness(ContentParameters data, String[] listCF);
	List<ContentResult> getHybridBusiness(CFParameters cfData, CBParametersL cbData, ContentParameters contentData);
	String[] getInformationBusiness(String businessId);

	CFResult initCF(CFParameters data);
	CBResultL initCBL(CBParametersL data);
	CBResultL initCBL2(CBParametersL data);
}
