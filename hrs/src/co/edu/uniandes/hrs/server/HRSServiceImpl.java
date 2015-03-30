package co.edu.uniandes.hrs.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import co.edu.uniandes.hrs.client.HRSService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class HRSServiceImpl extends RemoteServiceServlet implements HRSService {
	
	@Override
	public String[] sendUser(String user) {
		String[] ret={"Listo"};
		return(ret);
	}

	@Override
	public String[] getUserList() {
		String[] ret={};
		return(ret);
	}

	@Override
	public String[] getMovieList(String movie) {
		String[] ret={};
		return(ret);
	}
	
	@Override
	public String[] sendMovie(String movie) {
		String[] ret={};
		return(ret);
	}

	@Override
	public String[] getIndexes(String values) {
		String[] ret={};
		return(ret);
	}

	@Override
	public String[] getStatistics() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String[]> getMoviesMostRatings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String[]> getMoviesUser(String user) {
		// TODO Auto-generated method stub
		return null;
	}
}
