package co.edu.uniandes.hrs.client;

import java.util.List;

import co.edu.uniandes.hrs.shared.CFParameters;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface HRSServiceAsync {
	//Ojo, lo que va dentro del <> del asyncCallBack debe ser la clase de retorno del método del service
	void sendUser(CFParameters data, AsyncCallback<String[]> callback);
}
