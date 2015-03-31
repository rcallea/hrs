package co.edu.uniandes.hrs.client;

import java.util.List;

import co.edu.uniandes.hrs.shared.CFParameters;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("hrsRemoteService")
public interface HRSService extends RemoteService{
	String[] sendUser(CFParameters data);
}
