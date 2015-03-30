package co.edu.uniandes.hrs.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("hrsRemoteService")
public interface HRSService extends RemoteService{
	String[] sendUser(String user);
	String[] getUserList();
	String[] getMovieList(String movie);
	String[] sendMovie(String movie);
	String[] getIndexes(String values);

	//Servicios de Deisy
	String[] getStatistics();
	List<String[]> getMoviesMostRatings();
	List<String[]> getMoviesUser(String user);
}
