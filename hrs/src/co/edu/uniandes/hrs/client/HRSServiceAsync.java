package co.edu.uniandes.hrs.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.edu.uniandes.hrs.shared.CFParameters;
import co.edu.uniandes.hrs.shared.ContentParameters;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface HRSServiceAsync {
	//Ojo, lo que va dentro del <> del asyncCallBack debe ser la clase de retorno del método del service
	void sendUser(CFParameters data, AsyncCallback<String[]> callback);
	void getContentBusiness(ContentParameters data, AsyncCallback<HashMap<String, String>> callback);
	void getInformationBusiness(String businessId, AsyncCallback<String[]> callback);
}
