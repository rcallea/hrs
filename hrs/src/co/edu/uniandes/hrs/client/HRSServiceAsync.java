package co.edu.uniandes.hrs.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.edu.uniandes.hrs.shared.CBParametersL;
import co.edu.uniandes.hrs.shared.CBResultL;
import co.edu.uniandes.hrs.shared.CFParameters;
// HEAD
import co.edu.uniandes.hrs.shared.ContentParameters;
//=======
import co.edu.uniandes.hrs.shared.CFResult;
//>>>>>>> branch 'master' of https://github.com/rcallea/hrs.git

import co.edu.uniandes.hrs.shared.ContentResult;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface HRSServiceAsync {
	//Ojo, lo que va dentro del <> del asyncCallBack debe ser la clase de retorno del método del service
	//void sendUser(CFParameters data, AsyncCallback<String[]> callback);
	void getContentBusiness(ContentParameters data, String[] listCF, AsyncCallback<List<ContentResult>> callback);
	void getHybridBusiness(CFParameters cfData, CBParametersL cbData, ContentParameters contentData, AsyncCallback<List<ContentResult>> callback);
	void getInformationBusiness(String businessId, AsyncCallback<String[]> callback);

	void initCF(CFParameters data, AsyncCallback<CFResult> callback);
	void initCBL(CBParametersL data, AsyncCallback<CBResultL> callback);

}
