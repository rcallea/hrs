package co.edu.uniandes.hrs.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.edu.uniandes.hrs.shared.CFParameters;
import co.edu.uniandes.hrs.shared.ContentParameters;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("hrsRemoteService")
public interface HRSService extends RemoteService{
	String[] sendUser(CFParameters data);
	HashMap<String, String> getContentBusiness(ContentParameters data);
}
