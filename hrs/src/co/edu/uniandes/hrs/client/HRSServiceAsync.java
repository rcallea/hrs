package co.edu.uniandes.hrs.client;

import java.util.List;

import co.edu.uniandes.hrs.shared.CBParametersL;
import co.edu.uniandes.hrs.shared.CBResultL;
import co.edu.uniandes.hrs.shared.CFParameters;
import co.edu.uniandes.hrs.shared.CFResult;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface HRSServiceAsync {
	//Ojo, lo que va dentro del <> del asyncCallBack debe ser la clase de retorno del método del service
	void initCF(CFParameters data, AsyncCallback<CFResult> callback);
	void initCBL(CBParametersL data, AsyncCallback<CBResultL> callback);
}
